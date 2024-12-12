-- =========================================================================================
-- Fichier : pkg_lieu.pks
-- Description : Package specification pour la gestion des lieux.
-- Auteur : Jasser Gorsia / Nidhal Chelhi
-- =========================================================================================
CREATE OR REPLACE PACKAGE pkg_lieu AS
    -- Proc�dure pour ajouter un nouveau lieu
    PROCEDURE add_lieu (
        p_nomLieu IN VARCHAR2,
        p_adresse IN VARCHAR2,
        p_capacite IN NUMBER,
        p_status IN VARCHAR2 DEFAULT 'ACTIVE'
    );

    -- Proc�dure pour modifier le nom ou la capacit� d'un lieu
    PROCEDURE update_lieu (
        p_idLieu IN NUMBER,
        p_nomLieu IN VARCHAR2,
        p_capacite IN NUMBER
    );

    -- Proc�dure pour supprimer un lieu (physiquement ou logiquement)
    PROCEDURE delete_lieu (
        p_idLieu IN NUMBER
    );

    -- Fonction pour rechercher un lieu
    FUNCTION search_lieu (
        p_nomLieu IN VARCHAR2 DEFAULT NULL,
        p_capacite IN NUMBER DEFAULT NULL
    ) RETURN SYS_REFCURSOR;

END pkg_lieu;
/
