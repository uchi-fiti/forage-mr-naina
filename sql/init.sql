create database forageDB;
use forageDB;
CREATE TABLE Region (
    id int primary key auto_increment,
    libelle varchar(255) not null
);
CREATE TABLE District (
    id int primary key auto_increment,
    libelle varchar(255) not null,
    idRegion int not null,
    foreign key (idRegion) references Region(id)
);
CREATE TABLE Commune (
    id int primary key auto_increment,
    libelle varchar(255) not null,
    idDistrict int not null,
    foreign key (idDistrict) references District(id)
);
create table Demande (
    reference int primary key auto_increment,
    idClient int not null,
    idCommune int not null,
    dateDemande date not null,
    foreign key (idClient) references Client(id),
    foreign key (idCommune) references Commune(id)
);
CREATE TABLE Client (
    id int primary key auto_increment,
    nom varchar(255) not null,
    adresse varchar(255) not null,
    contact varchar(255) not null
);
create table Statut (
    id int primary key auto_increment,
    libelle varchar(255) not null
);
create table DemandeStatut (
    id int primary key auto_increment,
    refDemande int not null,
    idStatut int not null,
    dateChangementStatut date not null,
    foreign key (refDemande) references Demande(reference),
    foreign key (idStatut) references Statut(id)
);
create table Devis (
    reference int primary key auto_increment,
    description varchar(255) not null,
    montant_total decimal(10,2)
);
create table DetailsDevis (
    id int primary key auto_increment,
    refDevis int not null,
    libelle varchar(255) not null,
    quantite int not null,
    unite varchar(50) not null,
    prix_unitaire decimal(10,2) not null,
    foreign key (refDevis) references Devis(reference)
);