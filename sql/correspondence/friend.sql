
-- FRIEND's database

CREATE TABLE Status (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL
);

INSERT INTO Status (nom) VALUES
('DEMANDE CREE'),
('DEMANDE SUSPENDUE'),
('DEMANDE REPRISE'),
('DEMANDE ACCEPTE'),
('DEMANDE REFUSEE'),
('DEMANDE EN COURS'),
('DEMANDE ANNULE'),
('DEMANDE FINI'),

('DEVIS ETUDE CREE'),
('DEVIS ETUDE REFUSE'),
('DEVIS ETUDE ACCEPTE'),

('DEVIS FORAGE CREE'),
('DEVIS FORAGE REFUSE'),
('DEVIS FORAGE ACCEPTE');



CREATE TABLE Client(
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    telephone VARCHAR(20) NOT NULL
);

CREATE TABLE Region(
    id INT AUTO_INCREMENT PRIMARY KEY,
    libelle VARCHAR(23)
);

INSERT INTO Region (libelle) VALUES 
('Analamanga'),
('Vakinankaratra');

CREATE TABLE District(
    id INT AUTO_INCREMENT PRIMARY KEY,
    libelle VARCHAR(100),
    id_region INT,
    FOREIGN KEY (id_region) REFERENCES Region(id)
);

INSERT INTO District (libelle, id_region) VALUES 
('Antananarivo Atsimondrano', 1),
('Antsirabe I', 2);

CREATE TABLE Commune(
    id INT AUTO_INCREMENT PRIMARY KEY,
    libelle VARCHAR(100),
    id_district INT,
    FOREIGN KEY (id_district) REFERENCES District(id)
);

INSERT INTO Commune (libelle, id_district) VALUES 
('Ambohidratrimo', 1),
('Ankadikely Ilafy', 1),
('Antsirabe Ville', 2),
('Betafo', 2);

CREATE TABLE Parametre (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_status1 INT NOT NULL,
    id_status2 INT NOT NULL,
    duree FLOAT NOT NULL,
    alerte VARCHAR(25) NOT NULL
);

INSERT INTO Parametre (id_status1, id_status2, duree, alerte) VALUES
(1, 3 , 600, 'ROUGE'),    
(2 , 7 , 1000 , 'JAUNE');
-- (1, 3, 6, 'VERTE');


CREATE TABLE Demande (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_statut INT NOT NULL,
    id_client INT NOT NULL,
    reference VARCHAR(100) NOT NULL UNIQUE,
    lieu VARCHAR(100) NOT NULL,
    id_commune INT,
    date_demande TIMESTAMP NOT NULL,
    FOREIGN KEY (id_statut) REFERENCES Status(id),
    FOREIGN KEY (id_client) REFERENCES Client(id),
    FOREIGN KEY (id_commune) REFERENCES Commune(id)
);
CREATE TABLE Demande_Status (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_demande INT NOT NULL,
    id_status INT NOT NULL,
    observation varchar(500),
    Duree_Travail FLOAT,
    date_historique DATETIME NOT NULL,
    FOREIGN KEY (id_demande) REFERENCES Demande(id),
    FOREIGN KEY (id_status) REFERENCES Status(id)
);

create table devis (
    id int primary key auto_increment,
    idDemande int not null,
    ref varchar(50),
    date_devis DATETIME NOT NULL,
    id_type INT,
    description varchar(255),

    constraint fk_devis_demande
        foreign key (idDemande)
        references Demande(id)
        on delete cascade
        on update cascade
);

-- create table materiel (
--     id int primary key auto_increment,
--     label varchar(100) not null,
--     cout decimal(10,2) not null
-- );

create table devisDetail (
    id int primary key auto_increment,
    idDevis int not null,
    -- idMateriel int not null,
    quantite int not null,
    -- unite varchar(50) not null,
    pu decimal(10,2) not null,
    description varchar(255),

    --  constraint fk_devisDetail_materiel
    --     foreign key (idMateriel)
    --     references materiel(id)
    --     on delete cascade
    --     on update cascade,

    constraint fk_devisDetail_devis
        foreign key (idDevis)
        references devis(id)
        on delete cascade
        on update cascade
);

Create table TypeDevis (
    id int primary key auto_increment,
    label varchar(100) not null
);

INSERT INTO TypeDevis (label) VALUES
('Forage'),
('Etude');

-- Insertion de clients
-- INSERT INTO Client (nom, telephone) VALUES
-- ('Jean Rakoto', '0321234567'),
-- ('Marie Rasoa', '0349876543'),
-- ('Paul Andrian', '0381122334');

-- Insertion de demandes (référence unique, statut 'NOUVELLE' = id 1)
-- INSERT INTO Demande (id_statut, id_client, reference, lieu, id_commune, date_demande) VALUES
-- (1, 1, 'DEM-20230512131650-7909', 'Lot II 123 Antananarivo', 1, NOW()),
-- (1, 2, 'DEM-20230512131650-7910', 'PK 12 Route d’Ambohidratrimo', 1, NOW()),
-- (1, 3, 'DEM-20230512131650-7911', 'Centre ville Antsirabe', 3, NOW());

-- Insertion dans Demande_Status (historique des changements de statut)
-- INSERT INTO Demande_Status (id_demande, id_status, observation, Duree_Travail, date_historique) VALUES
-- (1, 1, NULL, 0, "2026-06-01 16:00:00"),
-- (2, 2, NULL, 0, "2026-06-01 08:00:00"),
-- (3, 1, NULL, 0, "2026-05-29 16:00:00"),
-- (1, 2, NULL ,60 , "2026-06-02 09:00:00"),
-- (1, 3, NULL ,60 , "2026-06-02 10:00:00"),
-- (1, 4, NULL ,60 , "2026-06-02 11:00:00"),
-- (2, 3, NULL ,60 , "2026-06-01 09:00:00"),
-- (2, 4, NULL ,60 , "2026-06-01 10:00:00"),
-- (2, 5, NULL ,60 , "2026-06-01 11:00:00");