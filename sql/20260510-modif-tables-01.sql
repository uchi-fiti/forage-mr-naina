alter table devis drop column montant_total;
alter table devis add column refDemande int not null;
alter table devis add foreign key (refDemande) references Demande(reference);
create table Type (
    id int primary key auto_increment,
    libelle varchar(255) not null
);
alter table devis add column idType int not null;
alter table devis add foreign key (idType) references Type (id);
alter table statut add column sigle varchar(50) not null;
-- sigle <-> enum