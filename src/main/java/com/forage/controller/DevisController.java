package com.forage.controller;

import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.forage.model.Demande;
import com.forage.model.DemandeStatut;
import com.forage.model.DetailsDevis;
import com.forage.model.Devis;
import com.forage.model.Statut;
import com.forage.model.StatutSigle;
import com.forage.model.Type;
import com.forage.service.DemandeService;
import com.forage.service.DemandeStatutService;
import com.forage.service.DevisService;
import com.forage.service.StatutService;
import com.forage.service.TypeService;
import com.forage.utils.Utils;

@Controller
public class DevisController {
    @Autowired
    private DevisService devisService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private Utils utils;

    @Autowired
    private DemandeService demandeService;

    @Autowired
    private StatutService statutService;

    @Autowired
    private DemandeStatutService demandeStatutService;

    @GetMapping("/devis/form")
    public String formDevis(Model model) {
        model.addAttribute("types", typeService.findAll());
        model.addAttribute("currentPage", "formDevis");
        model.addAttribute("content", "/WEB-INF/views/devis/index.jsp");
        return "layout";
    }

    @PostMapping("/devis/saveDevis")
    public String creerDevis(Model model,
                             @RequestParam("description") String description,
                             @RequestParam("refDemande") String refDemande,
                             @RequestParam("idType") @NonNull Integer idType,
                             @RequestParam("libelle") List <String> libelles,
                             @RequestParam("quantite") List <Integer> quantites,
                             @RequestParam("unite") List<String> unites,
                             @RequestParam("prix_unitaire") List <BigDecimal> pus,
                             @RequestParam(value = "dateDevis", required = false) String dateDevisStr
                            ) {
        if(refDemande == null || description == null) {
            model.addAttribute("error", "Veuillez remplir tous les champs");
            model.addAttribute("currentPage", "formDevis");
            model.addAttribute("content", "/WEB-INF/views/devis/index.jsp");
            return "layout";
        }
        try {
            LocalDateTime dateDevis = null;
            if (dateDevisStr != null && !dateDevisStr.isBlank()) {
                dateDevis = LocalDateTime.parse(dateDevisStr);
            }

            // Validation type forage
            Type type = typeService.findById(idType);
            if (type != null && "Forage".equalsIgnoreCase(type.getLibelle())) {
                Demande demande = demandeService.findById(refDemande);
                DemandeStatut currentStatut = demandeStatutService.getCurrentStatut(demande);
                if (currentStatut == null || !StatutSigle.DEVIS_ETUDE_ACCEPTE.equals(currentStatut.getStatut().getSigle())) {
                    throw new Exception("Erreur : Un devis de type 'Forage' ne peut être créé que si la demande est au statut 'Devis étude accepté'.");
                }
            }

            List <DetailsDevis> details = new ArrayList<>();
            for(int detail = 0; detail < quantites.size(); detail++) {
                String libelle = libelles.get(detail);
                Integer quantite = quantites.get(detail);
                String unite = unites.get(detail);
                BigDecimal pu = pus.get(detail);
                DetailsDevis temp = new DetailsDevis();
                temp.setLibelle(libelle);
                temp.setQuantite(quantite);
                temp.setUnite(unite);
                temp.setPrixUnitaire(pu);
                details.add(temp);
            }
            devisService.saveFullDevis(refDemande, description, idType, details, dateDevis);
            model.addAttribute("success", "Le devis a bien été créé");
            return "redirect:/devis/liste";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("types", typeService.findAll());
            model.addAttribute("currentPage", "formDevis");
            model.addAttribute("content", "/WEB-INF/views/devis/index.jsp");
            return "layout";
        }
        // model.addAttribute("currentPage", "formDevis");
        // model.addAttribute("content", "/WEB-INF/views/devis/index.jsp");
        // return "layout";
    
    }
        
    @GetMapping("/devis/liste")
    public String showDevis(Model model) {
        List <Devis> devis = devisService.findAll();
        model.addAttribute("devis", devis);
        model.addAttribute("currentPage", "listeDevis");
        model.addAttribute("content", "/WEB-INF/views/devis/liste.jsp");
        return "layout";
    }
}
