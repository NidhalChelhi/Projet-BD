-- =========================================================================================
-- Fichier : create_sequences.sql
-- Description : Ce script cr�e les s�quences n�cessaires pour g�n�rer des IDs uniques.
-- Auteur : Jasser Gorsia / Nidhal Chelhi
-- =========================================================================================

-- S�quence pour la table Lieu
CREATE SEQUENCE seq_lieu_id START WITH 1 INCREMENT BY 1;

-- S�quence pour la table Artiste
CREATE SEQUENCE seq_artiste_id START WITH 1 INCREMENT BY 1;

-- S�quence pour la table Spectacle
CREATE SEQUENCE seq_spectacle_id START WITH 1 INCREMENT BY 1;

-- S�quence pour la table Rubrique
CREATE SEQUENCE seq_rubrique_id START WITH 1 INCREMENT BY 1;

-- S�quence pour la table Billet
CREATE SEQUENCE seq_billet_id START WITH 1 INCREMENT BY 1;

-- Fin du fichier
