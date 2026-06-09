package com.forage.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.forage.model.Alerte;
import com.forage.model.Demande;
import com.forage.service.AlerteService;
import com.forage.service.DemandeService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class AlerteController {
    @Autowired
    private DemandeService demandeService;

    @Autowired
    private AlerteService alerteService;

    @GetMapping("/demandes")
    public List<Demande> getAllDemandes() {
        return demandeService.findAll();
    }

    @GetMapping("/all-alertes")
    public List<Map<String, Object>> getAllAlertes() {
        List<Demande> demandes = demandeService.findAll();
        List<Map<String, Object>> result = new ArrayList<>();
        
        for (Demande d : demandes) {
            List<Alerte> alertes = alerteService.getAllAlertes(d);
            if (!alertes.isEmpty()) {
                Map<String, Object> map = new HashMap<>();
                map.put("reference", d.getReference());
                map.put("client", d.getClient().getNom());
                map.put("commune", d.getCommune().getNom());
                map.put("dateDemande", d.getDateDemande().toString());
                map.put("alertes", alertes);
                result.add(map);
            }
        }
        return result;
    }

    @PostMapping("/all-by-refdemande")
    public List<Alerte> findAlertes(
        @RequestParam("refDemande") String refDemande) {
        Demande demande = demandeService.findById(refDemande);

        System.out.println("This function has been called. Ref demande is : " + refDemande);

        return alerteService.getAllAlertes(demande);
    }
    @GetMapping("/all-by-refdemande/{refDemande}")
    public List<Alerte> findAlertesPath(
        @PathVariable("refDemande") String refDemande) {
        Demande demande = demandeService.findById(refDemande);
        System.out.println("Demande found : " + demande.getReference());


        return alerteService.getAllAlertes(demande);
    }
}
