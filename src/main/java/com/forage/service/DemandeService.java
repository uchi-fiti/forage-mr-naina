package com.forage.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.forage.model.Client;
import com.forage.model.Commune;
import com.forage.model.Demande;
import com.forage.model.DemandeStatut;
import com.forage.repository.ClientRepository;
import com.forage.repository.CommuneRepository;
import com.forage.repository.DemandeRepository;
@Service
public class DemandeService {
    @Autowired
    private DemandeRepository demandeRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private CommuneRepository communeRepository;

    @Autowired
    private DemandeStatutService demandeStatutService;

    public Demande save(@NonNull Demande demande) {
        return demandeRepository.save(demande);
    }
    public List <Demande> findAll() {
        return demandeRepository.findAll();
    }
    public Demande findById(@NonNull String refDemande) {
        return demandeRepository.findById(refDemande).orElseThrow();
    }
    @Transactional
    public DemandeStatut save(@NonNull Integer clientId, @NonNull Integer communeId, LocalDateTime date) {
        
        Client client = clientRepository.findById(clientId).orElseThrow();
        Commune commune = communeRepository.findById(communeId).orElseThrow();

        LocalDateTime dateToUse = (date != null) ? date : LocalDateTime.now();

        // Generation de la reference automatique: REF-YYYYMMDD-ID
        String datePart = dateToUse.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        long nextId = demandeRepository.count() + 1;
        String refDemande = String.format("REF-%s-%d", datePart, nextId);

        Demande demande = new Demande();
        demande.setReference(refDemande);
        demande.setClient(client);
        demande.setCommune(commune);
        demande.setDateDemande(dateToUse);

        demande = demandeRepository.save(demande);
        DemandeStatut demandeStatut = demandeStatutService.save(demande, dateToUse);
        if (demandeStatut != null) {
            return demandeStatut;
        }
        return null;
    }

} 
