package com.forage.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.forage.model.Demande;
import com.forage.model.DemandeStatut;
import com.forage.model.DetailsDevis;
import com.forage.model.Devis;
import com.forage.model.Type;
import com.forage.repository.DetailsDevisRepository;
import com.forage.repository.DevisRepository;
import com.forage.repository.TypeRepository;
import com.forage.utils.Utils;

@Service
public class DevisService {
    @Autowired
    private DevisRepository devisRepository;
    @Autowired
    private DetailsDevisRepository detailsDevisRepository;
    @Autowired
    private DemandeService demandeService;
    @Autowired
    private TypeRepository typeRepository;
    @Autowired
    private Utils utils;
    @Autowired
    private StatutService statutService;
    @Autowired
    private DemandeStatutService demandeStatutService;
    public Devis save(String description) {
        Devis devis = new Devis();
        devis.setDescription(description);
        return devisRepository.save(devis);
    }
    
    @Transactional
    public Devis save(@NonNull String refDemande, String description, @NonNull Integer idType, LocalDateTime date) {
        Type type = typeRepository.findById(idType).orElseThrow();
        Demande demande = demandeService.findById(refDemande);

        LocalDateTime dateToUse = (date != null) ? date : LocalDateTime.now();

        Devis devis = new Devis();
        devis.setDemande(demande);
        devis.setDescription(description);
        devis.setType(type);
        devis.setDateDevis(dateToUse);

        Integer idStatut = utils.getIdStatutFromType(type);
        DemandeStatut ds = new DemandeStatut();
        ds.setDemande(demande);
        ds.setStatut(statutService.findById(idStatut));
        ds.setDateChangementStatut(dateToUse);
        demandeStatutService.saveWithDureeTravailleeUpdate(ds);
        return devisRepository.save(devis);
    }
    public Devis save(@NonNull String refDemande, String description) {
        Devis devis = new Devis();
        devis.setDemande(demandeService.findById(refDemande));
        devis.setDescription(description);
        devis.setDateDevis(LocalDateTime.now());
        return devisRepository.save(devis);
    }
    public List<Devis> findAll() {
        return devisRepository.findAll();
    }
    @Transactional
    public void saveDevis(List <DetailsDevis> details) { 
        for(DetailsDevis detailsDevis : details) {
            detailsDevisRepository.save(Objects.requireNonNull(detailsDevis));
        }
    }

    @Transactional
    public void saveFullDevis(String refDemande, String description, Integer idType, List<DetailsDevis> details, LocalDateTime date) {
        Devis devis = save(refDemande, description, idType, date);
        for(DetailsDevis detail : details) {
            detail.setDevis(devis);
            detailsDevisRepository.save(Objects.requireNonNull(detail));
        }
    }

}
