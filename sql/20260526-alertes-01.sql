CREATE TABLE AlerteParametre (
    id int primary key auto_increment,
    idStatut1 int,
    idStatut2 int,
    duree bigint,
    alerte varchar(100),
    Foreign Key (idStatut1) REFERENCES statut(id),
    Foreign Key (idStatut2) REFERENCES statut(id)
);
INSERT INTO AlerteParametre (idstatut1, idstatut2, duree, alerte) VALUES 
(1, 3, 420, "Jaune"),
(1, 3, 550, "Orange"),
(1, 3, 750, "Rouge"),

(3, 6, 50, "Bleu"),
(3, 6, 75, "Violet"),
(3, 6, 150, "Rouge"),

(1, 6, 800, "Jaune"),
(1, 6, 900, "Rouge");