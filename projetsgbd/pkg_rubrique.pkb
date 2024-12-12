-- =========================================================================================
-- Fichier : pkg_rubrique.pkb
-- Description : Package body pour la gestion des rubriques.
-- Auteur : Jasser Gorsia / Nidhal Chelhi
-- =========================================================================================
CREATE OR REPLACE PACKAGE BODY pkg_rubrique AS

    -- Proc�dure pour ajouter une rubrique
    PROCEDURE add_rubrique (
        p_idSpec IN NUMBER,
        p_idArt IN NUMBER,
        p_h_debutR IN NUMBER,
        p_dureeRub IN NUMBER,
        p_type IN VARCHAR2
    ) IS
        v_spectacle_start NUMBER;
        v_spectacle_duration NUMBER;
        v_spectacle_end NUMBER;
        v_speciality VARCHAR2(10);
        v_artist_conflict_count NUMBER;
    BEGIN
        -- V�rifier la sp�cialit� de l'artiste
        SELECT specialite INTO v_speciality
        FROM Artiste
        WHERE idArt = p_idArt;

        IF LOWER(v_speciality) != LOWER(p_type) THEN
            RAISE_APPLICATION_ERROR(-20001, 'Le type de la rubrique ne correspond pas � la sp�cialit� de l artiste.');
        END IF;

        -- R�cup�rer les d�tails du spectacle
        SELECT h_debut, dureeS INTO v_spectacle_start, v_spectacle_duration
        FROM Spectacle
        WHERE idSpec = p_idSpec;

        -- Calculer l'heure de fin du spectacle
        v_spectacle_end := v_spectacle_start + v_spectacle_duration;

        -- V�rifier si la rubrique commence avant le d�but du spectacle
        IF p_h_debutR < v_spectacle_start THEN
            RAISE_APPLICATION_ERROR(-20002, 'La rubrique ne peut pas commencer avant le d�but du spectacle.');
        END IF;

        -- V�rifier si la rubrique d�passe la dur�e du spectacle
        IF (p_h_debutR + p_dureeRub) > v_spectacle_end THEN
            RAISE_APPLICATION_ERROR(-20003, 'La rubrique d�passe la dur�e du spectacle.');
        END IF;

        -- V�rifier la disponibilit� de l'artiste
        SELECT COUNT(*) INTO v_artist_conflict_count
        FROM Rubrique
        WHERE idArt = p_idArt
          AND idSpec = p_idSpec
          AND (p_h_debutR BETWEEN h_debutR AND (h_debutR + dureeRub)
               OR (p_h_debutR + p_dureeRub) BETWEEN h_debutR AND (h_debutR + dureeRub));

        IF v_artist_conflict_count > 0 THEN
            RAISE_APPLICATION_ERROR(-20004, 'L artiste est indisponible � cette heure.');
        END IF;

        -- Ajouter la rubrique
        INSERT INTO Rubrique (idSpec, idArt, h_debutR, dureeRub, type)
        VALUES (p_idSpec, p_idArt, p_h_debutR, p_dureeRub, p_type);

        DBMS_OUTPUT.PUT_LINE('Rubrique ajout�e avec succ�s.');
    END add_rubrique;

    -- Proc�dure pour modifier une rubrique
    PROCEDURE update_rubrique (
        p_idRub IN NUMBER,
        p_idArt IN NUMBER DEFAULT NULL,
        p_h_debutR IN NUMBER DEFAULT NULL,
        p_dureeRub IN NUMBER DEFAULT NULL,
        p_type IN VARCHAR2 DEFAULT NULL
    ) IS
        v_speciality VARCHAR2(10);
        v_idSpec NUMBER;
        v_spectacle_start NUMBER;
        v_spectacle_duration NUMBER;
        v_spectacle_end NUMBER;
        v_artist_conflict_count NUMBER;
        v_current_h_debutR NUMBER;
        v_current_dureeRub NUMBER;
        v_updated_h_debutR NUMBER; -- Local variable for updated start time
        v_updated_dureeRub NUMBER; -- Local variable for updated duration
    BEGIN
        -- V�rifier si la rubrique existe
        BEGIN
            SELECT idSpec, h_debutR, dureeRub
            INTO v_idSpec, v_current_h_debutR, v_current_dureeRub
            FROM Rubrique
            WHERE idRub = p_idRub;
        EXCEPTION
            WHEN NO_DATA_FOUND THEN
                RAISE_APPLICATION_ERROR(-20010, 'La rubrique sp�cifi�e n existe pas.');
        END;

        -- V�rifier la sp�cialit� de l'artiste si le type est modifi�
        IF p_type IS NOT NULL THEN
            SELECT specialite INTO v_speciality
            FROM Artiste
            WHERE idArt = NVL(p_idArt, (SELECT idArt FROM Rubrique WHERE idRub = p_idRub));

            IF LOWER(v_speciality) != LOWER(p_type) THEN
                RAISE_APPLICATION_ERROR(-20001, 'Le type de la rubrique ne correspond pas � la sp�cialit� de l artiste.');
            END IF;
        END IF;

        -- R�cup�rer les d�tails du spectacle li� � la rubrique
        SELECT h_debut, dureeS INTO v_spectacle_start, v_spectacle_duration
        FROM Spectacle
        WHERE idSpec = v_idSpec;

        -- Calculer l'heure de fin du spectacle
        v_spectacle_end := v_spectacle_start + v_spectacle_duration;

        -- V�rifier les contraintes temporelles si h_debutR ou dureeRub sont modifi�s
        IF p_h_debutR IS NOT NULL THEN
            v_updated_h_debutR := p_h_debutR; -- Use provided value
        ELSE
            v_updated_h_debutR := v_current_h_debutR; -- Use current value
        END IF;

        IF p_dureeRub IS NOT NULL THEN
            v_updated_dureeRub := p_dureeRub; -- Use provided value
        ELSE
            v_updated_dureeRub := v_current_dureeRub; -- Use current value
        END IF;

        -- V�rifier si le d�but est avant le d�but du spectacle
        IF v_updated_h_debutR < v_spectacle_start THEN
            RAISE_APPLICATION_ERROR(-20002, 'La rubrique ne peut pas commencer avant le d�but du spectacle.');
        END IF;

        -- V�rifier si la rubrique d�passe la dur�e du spectacle
        IF (v_updated_h_debutR + v_updated_dureeRub) > v_spectacle_end THEN
            RAISE_APPLICATION_ERROR(-20003, 'La rubrique d�passe la dur�e du spectacle.');
        END IF;

        -- V�rifier les conflits d'horaires avec d'autres rubriques pour l'artiste
        IF p_idArt IS NOT NULL OR p_h_debutR IS NOT NULL OR p_dureeRub IS NOT NULL THEN
            SELECT COUNT(*) INTO v_artist_conflict_count
            FROM Rubrique
            WHERE idArt = NVL(p_idArt, (SELECT idArt FROM Rubrique WHERE idRub = p_idRub))
              AND idSpec = v_idSpec
              AND idRub != p_idRub
              AND (v_updated_h_debutR BETWEEN h_debutR AND (h_debutR + dureeRub)
                   OR (v_updated_h_debutR + v_updated_dureeRub) BETWEEN h_debutR AND (h_debutR + dureeRub));

            IF v_artist_conflict_count > 0 THEN
                RAISE_APPLICATION_ERROR(-20004, 'L artiste est d�j� pris � cette heure.');
            END IF;
        END IF;

        -- Modifier la rubrique
        UPDATE Rubrique
        SET idArt = NVL(p_idArt, idArt),
            h_debutR = v_updated_h_debutR,
            dureeRub = v_updated_dureeRub,
            type = NVL(p_type, type)
        WHERE idRub = p_idRub;

        DBMS_OUTPUT.PUT_LINE('Rubrique mise � jour avec succ�s.');
    END update_rubrique;

    -- Proc�dure pour supprimer une rubrique
    PROCEDURE delete_rubrique (
        p_idRub IN NUMBER
    ) IS
    BEGIN
        DELETE FROM Rubrique
        WHERE idRub = p_idRub;

        DBMS_OUTPUT.PUT_LINE('Rubrique supprim�e avec succ�s.');
    END delete_rubrique;

    -- Fonction pour chercher une rubrique
    FUNCTION search_rubrique (
        p_idSpec IN NUMBER DEFAULT NULL,
        p_nomArt IN VARCHAR2 DEFAULT NULL
    ) RETURN SYS_REFCURSOR IS
        v_cursor SYS_REFCURSOR;
    BEGIN
        OPEN v_cursor FOR
        SELECT r.idRub, r.idSpec, r.idArt, r.h_debutR, r.dureeRub, r.type
        FROM Rubrique r
        JOIN Artiste a ON r.idArt = a.idArt
        WHERE (p_idSpec IS NULL OR r.idSpec = p_idSpec)
          AND (p_nomArt IS NULL OR LOWER(a.NomArt) LIKE '%' || LOWER(p_nomArt) || '%');
        RETURN v_cursor;
    END search_rubrique;

END pkg_rubrique;
/
