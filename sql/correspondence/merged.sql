-- ============================================================
-- SCRIPT DE MIGRATION : données de l'ami → schéma de mine.sql
-- ============================================================
-- Correspondances principales :
--   Status (ami)         → Statut (moi)
--   TypeDevis (ami)      → Type (moi)
--   Region/District/Commune → identiques (noms de colonnes adaptés)
--   Parametre (ami)      → AlerteParametre (moi)
--   Demande + Demande_Status (ami) → Demande + DemandeStatut (moi)
--   devis + devisDetail (ami) → Devis + DetailsDevis (moi)
-- ============================================================


-- ------------------------------------------------------------
-- 1. STATUT — ne pas modifier les existants, ajouter les manquants
--    Format respecté : libelle en français + sigle en SNAKE_CASE majuscule
--    Statuts existants (à ne pas toucher) :
--      id=1  Demande créée            DEMANDE_CREEE
--      id=2  Devis étude terminé      DEVIS_ETUDE_TERMINE
--      id=3  Devis étude accepté      DEVIS_ETUDE_ACCEPTE
--      id=4  Devis forage terminé     DEVIS_FORAGE_TERMINE
--      id=5  Devis forage accepté     DEVIS_FORAGE_ACCEPTE
-- ------------------------------------------------------------
set foreign_key_checks = 0;
delete from statut;

alter table statut AUTO_INCREMENT = 1;
INSERT INTO Statut (libelle, sigle) VALUES
('Demande suspendue',    'DEMANDE_SUSPENDUE'),    -- correspondra à id=6
('Demande reprise',      'DEMANDE_REPRISE'),       -- id=7
('Demande acceptée',     'DEMANDE_ACCEPTEE'),      -- id=8
('Demande refusée',      'DEMANDE_REFUSEE'),       -- id=9
('Demande en cours',     'DEMANDE_EN_COURS'),      -- id=10
('Demande annulée',      'DEMANDE_ANNULEE'),       -- id=11
('Demande finie',        'DEMANDE_FINIE'),         -- id=12
('Devis étude créé',     'DEVIS_ETUDE_CREE'),      -- id=13
('Devis étude refusé',   'DEVIS_ETUDE_REFUSE'),    -- id=14
('Devis forage créé',    'DEVIS_FORAGE_CREE'),     -- id=15
('Devis forage refusé',  'DEVIS_FORAGE_REFUSE');   -- id=16
set foreign_key_checks = 0;

-- Table de correspondance ami.Status.id → mon Statut.id (commentaire de référence)
-- ami  1  DEMANDE CREE          → moi  1  DEMANDE_CREEE
-- ami  2  DEMANDE SUSPENDUE     → moi  6  DEMANDE_SUSPENDUE
-- ami  3  DEMANDE REPRISE       → moi  7  DEMANDE_REPRISE
-- ami  4  DEMANDE ACCEPTE       → moi  8  DEMANDE_ACCEPTEE
-- ami  5  DEMANDE REFUSEE       → moi  9  DEMANDE_REFUSEE
-- ami  6  DEMANDE EN COURS      → moi 10  DEMANDE_EN_COURS
-- ami  7  DEMANDE ANNULE        → moi 11  DEMANDE_ANNULEE
-- ami  8  DEMANDE FINI          → moi 12  DEMANDE_FINIE
-- ami  9  DEVIS ETUDE CREE      → moi 13  DEVIS_ETUDE_CREE
-- ami 10  DEVIS ETUDE REFUSE    → moi 14  DEVIS_ETUDE_REFUSE
-- ami 11  DEVIS ETUDE ACCEPTE   → moi  3  DEVIS_ETUDE_ACCEPTE
-- ami 12  DEVIS FORAGE CREE     → moi 15  DEVIS_FORAGE_CREE
-- ami 13  DEVIS FORAGE REFUSE   → moi 16  DEVIS_FORAGE_REFUSE
-- ami 14  DEVIS FORAGE ACCEPTE  → moi  5  DEVIS_FORAGE_ACCEPTE


-- ------------------------------------------------------------
-- 2. TYPE — l'ami a TypeDevis ('Forage', 'Etude')
--    Toi tu as déjà ('Etude', 'Forage') avec id=1 et id=2
--    → rien à insérer, les données sont déjà compatibles
-- ------------------------------------------------------------
-- (table Type déjà remplie dans mine.sql)


-- ------------------------------------------------------------
-- 3. REGION — données supplémentaires de l'ami
--    L'ami a : Analamanga (id=1), Vakinankaratra (id=2)
--    Toi tu as déjà ces deux régions + d'autres
--    → rien à insérer (déjà présentes dans mine.sql)
-- ------------------------------------------------------------


-- ------------------------------------------------------------
-- 4. DISTRICT — données supplémentaires de l'ami
--    L'ami a : 'Antananarivo Atsimondrano' (id_region=1)
--              'Antsirabe I'               (id_region=2)
--    Toi tu as déjà 'Antananarivo Atsimondrano' (idRegion=2 chez toi)
--    et 'Antsirabe I' (idRegion=2 chez toi) → déjà présents
--    → rien à insérer
-- ------------------------------------------------------------


-- ------------------------------------------------------------
-- 5. COMMUNE — données supplémentaires de l'ami
--    L'ami a : Ambohidratrimo, Ankadikely Ilafy (district Atsimon.),
--              Antsirabe Ville, Betafo (district Antsirabe I)
--    Chez toi, idDistrict pour Antananarivo Atsimondrano = 2
--              idDistrict pour Antsirabe I = 3
--    → Ambohidratrimo et Ankadikely Ilafy sont nouvelles
--      Antsirabe Ville et Betafo sont nouvelles (tu n'as que CU Antsirabe)
-- ------------------------------------------------------------

INSERT INTO Commune (libelle, idDistrict) VALUES
('Ambohidratrimo',   2),   -- District Antananarivo Atsimondrano
('Ankadikely Ilafy', 2),   -- District Antananarivo Atsimondrano
('Antsirabe Ville',  3),   -- District Antsirabe I
('Betafo',           3);   -- District Antsirabe I


-- ------------------------------------------------------------
-- 6. CLIENT — données de l'ami (commentées dans son script)
--    Son schéma n'a pas d'adresse, seulement nom + telephone
--    → on mappe telephone vers contact, adresse laissée vide
-- ------------------------------------------------------------

INSERT INTO Client (nom, adresse, contact) VALUES
('Jean Rakoto', '', '0321234567'),
('Marie Rasoa',  '', '0349876543'),
('Paul Andrian', '', '0381122334');


-- ------------------------------------------------------------
-- 7. DEMANDE — données de l'ami (commentées dans son script)
--    Son schéma : PK = id INT, le mien : PK = reference VARCHAR
--    id_commune chez lui → idCommune chez moi
--    Correspondance communes (ordre d'insertion dans mine.sql + ci-dessus) :
--      ami commune 1 (Ambohidratrimo)   → moi id=6
--      ami commune 2 (Ankadikely Ilafy) → moi id=7
--      ami commune 3 (Antsirabe Ville)  → moi id=8
--      ami commune 4 (Betafo)           → moi id=9
--    Correspondance clients (insérés juste au-dessus, après les 6 existants) :
--      ami client 1 (Jean Rakoto)  → moi id=7
--      ami client 2 (Marie Rasoa)  → moi id=8
--      ami client 3 (Paul Andrian) → moi id=9
--    Note : id_statut de Demande ignoré (statut géré via DemandeStatut)
-- ------------------------------------------------------------

INSERT INTO Demande (reference, idClient, idCommune, dateDemande) VALUES
('DEM-20230512131650-7909', 7, 6, '2023-05-12 13:16:50'),
('DEM-20230512131650-7910', 8, 6, '2023-05-12 13:16:50'),
('DEM-20230512131650-7911', 9, 8, '2023-05-12 13:16:50');


-- ------------------------------------------------------------
-- 8. DEMANDESTATUT — historique des statuts (données ami commentées)
--    Mapping statuts ami → moi (voir table de correspondance §1)
--    ami status 1 → moi 1, ami status 2 → moi 6, ami status 3 → moi 7
--    ami status 4 → moi 8, ami status 5 → moi 9
-- ------------------------------------------------------------

-- INSERT INTO DemandeStatut (refDemande, idStatut, dateChangementStatut, dureeTravaillee) VALUES
-- -- Demande 7909
-- ('DEM-20230512131650-7909',  1, '2026-06-01 16:00:00',  0),   -- DEMANDE_CREEE
-- ('DEM-20230512131650-7909',  6, '2026-06-02 09:00:00', 60),   -- DEMANDE_SUSPENDUE
-- ('DEM-20230512131650-7909',  7, '2026-06-02 10:00:00', 60),   -- DEMANDE_REPRISE
-- ('DEM-20230512131650-7909',  8, '2026-06-02 11:00:00', 60),   -- DEMANDE_ACCEPTEE
-- -- Demande 7910
-- ('DEM-20230512131650-7910',  6, '2026-06-01 08:00:00',  0),   -- DEMANDE_SUSPENDUE
-- ('DEM-20230512131650-7910',  7, '2026-06-01 09:00:00', 60),   -- DEMANDE_REPRISE
-- ('DEM-20230512131650-7910',  8, '2026-06-01 10:00:00', 60),   -- DEMANDE_ACCEPTEE
-- ('DEM-20230512131650-7910',  9, '2026-06-01 11:00:00', 60),   -- DEMANDE_REFUSEE
-- -- Demande 7911
-- ('DEM-20230512131650-7911',  1, '2026-05-29 16:00:00',  0);   -- DEMANDE_CREEE


-- ------------------------------------------------------------
-- 9. ALERTEPARAMETRE — données de l'ami (Parametre)
--    Mapping statuts : ami status 1 → moi 1, ami status 2 → moi 6, ami status 7 → moi 11
--    Parametre (1, 3,  600, 'ROUGE') : statut1=DEMANDE_CREEE(1),    statut2=DEMANDE_REPRISE(7)
--    Parametre (2, 7, 1000, 'JAUNE') : statut1=DEMANDE_SUSPENDUE(6), statut2=DEMANDE_ANNULEE(11)
--    Note : alerte mise en minuscules pour respecter le format de mine.sql ('Jaune', 'Rouge')
-- ------------------------------------------------------------

INSERT INTO AlerteParametre (idStatut1, idStatut2, duree, alerte) VALUES
(1,  7,  600, 'Rouge'),
(6, 11, 1000, 'Jaune');


-- ------------------------------------------------------------
-- 10. DEVIS et DETAILSDEVIS
--     Aucune donnée non commentée chez l'ami → rien à insérer
--     (les INSERT de devis/devisDetail sont tous commentés)
-- ------------------------------------------------------------

-- ============================================================
-- FIN DU SCRIPT DE MIGRATION
-- ============================================================