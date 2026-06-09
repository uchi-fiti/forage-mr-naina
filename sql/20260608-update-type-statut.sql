-- Script de recréation de la table Type et mise à jour de la table Statut
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS Type;
CREATE TABLE Type (
    id int primary key auto_increment,
    libelle varchar(255) not null
);

INSERT INTO Type (libelle) VALUES
('Etude'),
('Forage');

-- Réinitialisation des statuts
DROP TABLE IF EXISTS Statut;
CREATE TABLE Statut (
    id int primary key auto_increment,
    libelle varchar(255) not null,
    sigle varchar(255) not null
);

INSERT INTO Statut (id, libelle, sigle) VALUES
(1, 'Demande créée', 'DEMANDE_CREEE'),
(2, 'Devis étude terminé', 'DEVIS_ETUDE_TERMINE'),
(3, 'Devis étude accepté', 'DEVIS_ETUDE_ACCEPTE'),
(4, 'Devis forage terminé', 'DEVIS_FORAGE_TERMINE'),
(5, 'Devis forage accepté', 'DEVIS_FORAGE_ACCEPTE');

SET FOREIGN_KEY_CHECKS = 1;
