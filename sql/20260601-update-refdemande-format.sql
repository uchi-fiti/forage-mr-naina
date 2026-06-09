-- Update existing Demande references to REFXX<old_reference> format

SET FOREIGN_KEY_CHECKS = 0;

UPDATE DemandeStatut ds
JOIN Demande d ON ds.refDemande = d.reference
SET ds.refDemande = CONCAT('REFXX', d.reference);

UPDATE Devis dv
JOIN Demande d ON dv.refDemande = d.reference
SET dv.refDemande = CONCAT('REFXX', d.reference);

UPDATE Demande
SET reference = CONCAT('REFXX', reference);

SET FOREIGN_KEY_CHECKS = 1;
