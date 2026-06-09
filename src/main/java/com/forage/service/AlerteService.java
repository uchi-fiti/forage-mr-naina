package com.forage.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.forage.model.Alerte;
import com.forage.model.Demande;
import com.forage.model.DemandeStatut;
import com.forage.repository.AlerteRepository;

@Service
public class AlerteService {
    @Autowired
    private AlerteRepository alerteRepository;

    @Autowired
    DemandeStatutService demandeStatutService;


    public List <Alerte> getAlerte(DemandeStatut [] intervalle) {
        if(intervalle.length != 2) {
            return null;
        }
        List <Alerte> retour = new ArrayList<>();
        List <Alerte> alertes = alerteRepository.findAll();
        long dureeReelle = demandeStatutService.calculerDureeTravaille(intervalle[0], intervalle[1]);
        
        for (Alerte alerte : alertes) {
            if(intervalle[0].getStatut().getId() == alerte.getStatut1().getId() 
            && intervalle[1].getStatut().getId() == alerte.getStatut2().getId()) {
                
                boolean match = false;
                if (alerte.getDureeFin() == null) {
                    if (dureeReelle >= alerte.getDureeDebut()) match = true;
                } else {
                    if (dureeReelle >= alerte.getDureeDebut() && dureeReelle < alerte.getDureeFin()) match = true;
                }

                if (match) {
                    retour.add(alerte);
                }
            }
        }
        return retour;
    }
    public List <Alerte> getAllAlertes(Demande demande) {
        List <DemandeStatut[]> intervalles = demandeStatutService.getIntervallesDemandeStatutByDemande(demande);
        List <Alerte> retour = new ArrayList<>();   
        for (DemandeStatut [] intervalle : intervalles) {
            retour.addAll(getAlerte(intervalle));
        }
        return retour;
    }
}
