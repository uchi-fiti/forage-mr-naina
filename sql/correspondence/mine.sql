-- MY DATABASE

-- 1. Tables de référence (Valeurs fixes)
CREATE TABLE Region (
    id INT PRIMARY KEY AUTO_INCREMENT,
    libelle VARCHAR(255) NOT NULL
);

CREATE TABLE District (
    id INT PRIMARY KEY AUTO_INCREMENT,
    libelle VARCHAR(255) NOT NULL,
    idRegion INT NOT NULL,
    FOREIGN KEY (idRegion) REFERENCES Region(id)
);

CREATE TABLE Commune (
    id INT PRIMARY KEY AUTO_INCREMENT,
    libelle VARCHAR(255) NOT NULL,
    idDistrict INT NOT NULL,
    FOREIGN KEY (idDistrict) REFERENCES District(id)
);

CREATE TABLE Statut (
    id INT PRIMARY KEY AUTO_INCREMENT,
    libelle VARCHAR(255) NOT NULL,
    sigle VARCHAR(255) NOT NULL
);

CREATE TABLE Type (
    id INT PRIMARY KEY AUTO_INCREMENT,
    libelle VARCHAR(255) NOT NULL
);

-- 2. Tables de données
CREATE TABLE Client (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nom VARCHAR(255) NOT NULL,
    adresse VARCHAR(255) NOT NULL,
    contact VARCHAR(255) NOT NULL
);

CREATE TABLE Demande (
    reference VARCHAR(50) PRIMARY KEY,
    idClient INT NOT NULL,
    idCommune INT NOT NULL,
    dateDemande DATETIME NOT NULL,
    FOREIGN KEY (idClient) REFERENCES Client(id),
    FOREIGN KEY (idCommune) REFERENCES Commune(id)
);

CREATE TABLE DemandeStatut (
    id INT PRIMARY KEY AUTO_INCREMENT,
    refDemande VARCHAR(50) NOT NULL,
    idStatut INT NOT NULL,
    dateChangementStatut DATETIME NOT NULL,
    dureeTravaillee FLOAT DEFAULT 0,
    FOREIGN KEY (refDemande) REFERENCES Demande(reference),
    FOREIGN KEY (idStatut) REFERENCES Statut(id)
);

CREATE TABLE Devis (
    reference INT PRIMARY KEY AUTO_INCREMENT,
    refDemande VARCHAR(50) NOT NULL,
    idType INT NOT NULL,
    description VARCHAR(255) NOT NULL,
    dateDevis DATETIME NOT NULL,
    FOREIGN KEY (refDemande) REFERENCES Demande(reference),
    FOREIGN KEY (idType) REFERENCES Type(id)
);

CREATE TABLE DetailsDevis (
    id INT PRIMARY KEY AUTO_INCREMENT,
    refDevis INT NOT NULL,
    libelle VARCHAR(255) NOT NULL,
    quantite INT NOT NULL,
    unite VARCHAR(50) NOT NULL,
    prix_unitaire DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (refDevis) REFERENCES Devis(reference)
);

CREATE TABLE AlerteParametre (
    id INT PRIMARY KEY AUTO_INCREMENT,
    idStatut1 INT NOT NULL,
    idStatut2 INT NOT NULL,
    duree BIGINT NOT NULL,
    alerte VARCHAR(100) NOT NULL,
    FOREIGN KEY (idStatut1) REFERENCES Statut(id),
    FOREIGN KEY (idStatut2) REFERENCES Statut(id)
);

-- 3. Insertion des données
-- Clients
INSERT INTO Client (nom, adresse, contact) VALUES 
('Fitiavana', 'ITU Andoharanofotsy', 'fitiuchi@gmail.com'),
('Jean Dupont', 'Lot I J 45 Ambohimanarina', 'jean.dupont@gmail.com'),
('Marie Rakoto', 'Villa 4, Ivandry', 'marie.rakoto@orange.mg'),
('Association Eau pour Tous', 'Siège Ambatobe', 'contact@eaupourtous.mg'),
('Entreprise BTP Sarl', 'Z.I. Forello Tanjombato', 'commercial@btpsarl.mg'),
('Fanilo Raoelison', 'Logement 123 67Ha', 'fanilo.rao@telma.mg');

-- Types
INSERT INTO Type (libelle) VALUES ('Etude'), ('Forage');

-- Statuts
INSERT INTO Statut (id, libelle, sigle) VALUES
(1, 'Demande créée', 'DEMANDE_CREEE'),
(2, 'Devis étude terminé', 'DEVIS_ETUDE_TERMINE'),
(3, 'Devis étude accepté', 'DEVIS_ETUDE_ACCEPTE'),
(4, 'Devis forage terminé', 'DEVIS_FORAGE_TERMINE'),
(5, 'Devis forage accepté', 'DEVIS_FORAGE_ACCEPTE');

-- Zones géographiques
INSERT INTO Region (libelle) VALUES ('Analamanga'), ('Vakinankaratra'), ('Atsinanana'), ('Diana');

INSERT INTO District (libelle, idRegion) VALUES 
('Antananarivo Renivohitra', 1), 
('Antananarivo Atsimondrano', 1),
('Antsirabe I', 2),
('Toamasina I', 3),
('Antsiranana I', 4);

INSERT INTO Commune (libelle, idDistrict) VALUES 
('CU Antananarivo', 1), 
('Andoharanofotsy', 2),
('CU Antsirabe', 3),
('CU Toamasina', 4),
('CU Antsiranana', 5);

-- Paramètres d'alertes
INSERT INTO AlerteParametre (idStatut1, idStatut2, duree, alerte) VALUES 
(1, 2, 420, 'Jaune'),
(1, 2, 800, 'Rouge'),
(3, 4, 1500, 'Orange'),
(3, 4, 2500, 'Rouge');
