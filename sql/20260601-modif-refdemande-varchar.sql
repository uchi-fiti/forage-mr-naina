-- Change Demande.reference to varchar and update foreign keys
SET FOREIGN_KEY_CHECKS = 0;

-- Normalize any existing weird references to REF### format
UPDATE Demande
SET reference = CONCAT('REF', LPAD(REGEXP_REPLACE(reference, '[^0-9]', ''), 3, '0'));

UPDATE DemandeStatut
SET refDemande = CONCAT('REF', LPAD(REGEXP_REPLACE(refDemande, '[^0-9]', ''), 3, '0'));

UPDATE Devis
SET refDemande = CONCAT('REF', LPAD(REGEXP_REPLACE(refDemande, '[^0-9]', ''), 3, '0'));

ALTER TABLE DemandeStatut DROP FOREIGN KEY DemandeStatut_ibfk_1;
ALTER TABLE Devis DROP FOREIGN KEY Devis_ibfk_1;

ALTER TABLE Demande MODIFY reference VARCHAR(50) NOT NULL;
ALTER TABLE DemandeStatut MODIFY refDemande VARCHAR(50) NOT NULL;
ALTER TABLE Devis MODIFY refDemande VARCHAR(50);

ALTER TABLE DemandeStatut ADD FOREIGN KEY (refDemande) REFERENCES Demande(reference);
ALTER TABLE Devis ADD FOREIGN KEY (refDemande) REFERENCES Demande(reference);

UPDATE DemandeStatut ds
JOIN Demande d ON ds.refDemande = d.reference
SET ds.refDemande = CONCAT('REF', LPAD(REGEXP_REPLACE(d.reference, '[^0-9]', ''), 3, '0'));

UPDATE Devis dv
JOIN Demande d ON dv.refDemande = d.reference
SET dv.refDemande = CONCAT('REF', LPAD(REGEXP_REPLACE(d.reference, '[^0-9]', ''), 3, '0'));

UPDATE Demande
SET reference = CONCAT('REF', LPAD(REGEXP_REPLACE(reference, '[^0-9]', ''), 3, '0'));

SET FOREIGN_KEY_CHECKS = 1;