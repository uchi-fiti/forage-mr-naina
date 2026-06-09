delete from statut;
alter table statut AUTO_INCREMENT = 1;
INSERT INTO Statut (libelle, sigle) VALUES
('Demande en cours', 'DEMANDE_EN_COURS'),
('Devis étude en cours', 'DEVIS_ETUDE_EN_COURS'),
('Devis étude refusé', 'DEVIS_ETUDE_REFUSE'),
('Devis étude accepté', 'DEVIS_ETUDE_ACCEPTE'),
('Devis forage en cours', 'DEVIS_FORAGE_EN_COURS'),
('Devis forage refusé', 'DEVIS_FORAGE_REFUSE'),
('Devis étude terminé', 'DEVIS_ETUDE_TERMINE'),
('Devis forage terminé', 'DEVIS_FORAGE_TERMINE');
delete from type;
alter table type AUTO_INCREMENT = 1;
INSERT INTO Type (libelle) VALUES
('Etude'),
('Forage');