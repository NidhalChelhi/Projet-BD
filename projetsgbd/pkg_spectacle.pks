-- =========================================================================================
-- Fichier : pkg_spectacle.pks
-- Description : Package specification pour la gestion des spectacles.
-- Auteur : Jasser Gorsia / Nidhal Chelhi
-- =========================================================================================
CREATE OR REPLACE PACKAGE pkg_spectacle AS
    -- Proc�dure pour ajouter un spectacle
    PROCEDURE add_spectacle (
        p_titre IN VARCHAR2,
        p_dateS IN DATE,
        p_h_debut IN NUMBER,
        p_dureeS IN NUMBER,
        p_nbrSpectateur IN NUMBER,
        p_idLieu IN NUMBER
    );

    -- Proc�dure pour annuler un spectacle
    PROCEDURE cancel_spectacle (
        p_idSpec IN NUMBER
    );

    -- Proc�dure pour modifier un spectacle
    PROCEDURE update_spectacle (
        p_idSpec IN NUMBER,
        p_titre IN VARCHAR2 DEFAULT NULL,
        p_dateS IN DATE DEFAULT NULL,
        p_h_debut IN NUMBER DEFAULT NULL,
        p_dureeS IN NUMBER DEFAULT NULL,
        p_nbrSpectateur IN NUMBER DEFAULT NULL,
        p_idLieu IN NUMBER DEFAULT NULL
    );

    -- Fonction pour chercher un spectacle par titre ou ID
    FUNCTION search_spectacle (
        p_titre IN VARCHAR2 DEFAULT NULL,
        p_idSpec IN NUMBER DEFAULT NULL
    ) RETURN SYS_REFCURSOR;

END pkg_spectacle;
/
