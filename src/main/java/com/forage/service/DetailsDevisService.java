package com.forage.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.forage.model.DetailsDevis;
import com.forage.model.Devis;
import com.forage.repository.DetailsDevisRepository;

@Service
public class DetailsDevisService {
    @Autowired
    private DetailsDevisRepository DetailsDevisRepository;

    public List<DetailsDevis> findAll() {
        return DetailsDevisRepository.findAll();
    }

    public DetailsDevis save(Devis devis, String libelle, Integer quantite, String unite, BigDecimal prixUnitaire) {
        DetailsDevis DetailsDevis = new DetailsDevis();
        DetailsDevis.setDevis(devis);
        DetailsDevis.setLibelle(libelle);
        DetailsDevis.setQuantite(quantite);
        DetailsDevis.setUnite(unite);
        DetailsDevis.setPrixUnitaire(prixUnitaire);
        return DetailsDevisRepository.save(DetailsDevis);
    }
}
