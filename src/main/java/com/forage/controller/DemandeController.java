package com.forage.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.forage.model.StatutSigle;
import com.forage.model.Alerte;
import com.forage.model.Client;
import com.forage.model.Commune;
import com.forage.model.Demande;
import com.forage.model.DemandeStatut;
import com.forage.service.AlerteService;
import com.forage.service.ClientService;
import com.forage.service.CommuneService;
import com.forage.service.DemandeService;
import com.forage.service.DemandeStatutService;

@Controller
public class DemandeController {
    @Autowired
    private ClientService clientService;

    @Autowired
    private CommuneService communeService;

    @Autowired
    private DemandeService demandeService;

    @Autowired
    private DemandeStatutService demandeStatutService;

    @Autowired
    private AlerteService alerteService;

    @GetMapping("/demande/form")
    public String formDemande(Model model) {
        List <Client> clients = clientService.findAll();
        List <Commune> communes = communeService.findAll();
        model.addAttribute("communes", communes);
        model.addAttribute("clients", clients);
        model.addAttribute("currentPage", "formDemande");
        model.addAttribute("content", "/WEB-INF/views/demande/index.jsp");
        return "layout";
    }

    @PostMapping("/demande/creer")
    public String insererDemande(Model model,
                                @RequestParam("clientId") Integer clientId,
                                @RequestParam("communeId") Integer communeId,
                                @RequestParam(value = "dateDemande", required = false) String dateDemandeStr) {
        System.out.println("Client id: " + clientId + " Commune Id: " + communeId);
        
        LocalDateTime dateDemande = null;
        if (dateDemandeStr != null && !dateDemandeStr.isBlank()) {
            dateDemande = LocalDateTime.parse(dateDemandeStr);
        }

        if(clientId != null && communeId != null) {
            DemandeStatut ds = demandeService.save(clientId, communeId, dateDemande);
            if (ds != null) {
                return "redirect:/demande/liste-alertes";
            }
        } else {
            model.addAttribute("error", "Can't insert, client or commune is null");
        }
        model.addAttribute("currentPage", "formDemande");
        model.addAttribute("content", "/WEB-INF/views/demande/index.jsp");
        return "layout";
    }
    @GetMapping("/demande/liste")
    public String showListes(Model model) {
        List <Demande> demandes = demandeService.findAll();
        model.addAttribute("demandes", demandes);
        model.addAttribute("currentPage", "listeDemande");
        model.addAttribute("content", "/WEB-INF/views/demande/liste.jsp");
        return "layout";
    }

    @GetMapping("/demande/liste-alertes")
    public String showListesAlertes(Model model) {
        List<Demande> demandes = demandeService.findAll();
        Map<String, List<Alerte>> alertesMap = new HashMap<>();
        Map<String, Double> dureeTotaleMap = new HashMap<>();
        
        for (Demande demande : demandes) {
            alertesMap.put(demande.getReference(), alerteService.getAllAlertes(demande));
            
            DemandeStatut current = demandeStatutService.getCurrentStatut(demande);
            if (current != null && current.getStatut().getSigle() == StatutSigle.FORAGE_TERMINE) {
                long minutes = demandeStatutService.calculerDureeTotaleTravaillee(demande);
                dureeTotaleMap.put(demande.getReference(), minutes / 60.0);
            }
        }
        
        model.addAttribute("demandes", demandes);
        model.addAttribute("alertesMap", alertesMap);
        model.addAttribute("dureeTotaleMap", dureeTotaleMap);
        model.addAttribute("currentPage", "listeDemandeAlertes");
        model.addAttribute("content", "/WEB-INF/views/demande/listeAlertes.jsp");
        return "layout";
    }
    @ResponseBody
    @PostMapping("/demande/details/objet")
    public Demande showDetails(@RequestParam("refDemande") String refDemande) {
        if(refDemande == null) {
            return null;
        }
        return demandeService.findById(refDemande);
    }
    @ResponseBody
    @PostMapping("/demande/details/json")
    public String showDetailsJSONString(@RequestParam("refDemande") String refDemande) {
        if(refDemande == null) {
            return "{\"erreur\": \"Aucun reference fournie, veuillez entrez une reference valide\"}";
        }
        try {
            Demande demande = demandeService.findById(refDemande);
            if (demande != null) {
                //client, date, llieu
                return String.format("{\"client\": \"%s\", \"commune\": \"%s\", \"date\": \"%s\"}", 
                    demande.getClient().getNom(), 
                    demande.getCommune().getNom(),
                    demande.getDateDemande().toString()
                );
            }
        } catch (NoSuchElementException e) {
            return "{\"erreur\": \"Demande pas trouvé, Veuillez entrez une reference valide\"}";
        }
        return "{\"erreur\": \"Demande pas trouvé, Veuillez entrez une reference valide\"}";
    }
}
