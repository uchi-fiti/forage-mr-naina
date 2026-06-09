package com.forage.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.forage.model.DemandeStatut;
import com.forage.service.DemandeService;
import com.forage.service.DemandeStatutService;
import com.forage.service.StatutService;
import com.forage.utils.Utils;

@Controller 
public class DemandeStatutController {
    @Autowired
    private StatutService statutService;
    @Autowired
    private Utils utils;
    @Autowired
    private DemandeService demandeService;
    @Autowired
    private DemandeStatutService demandeStatutService;
    @GetMapping("/demandeStatut/formAjout")
    public String formAjout(Model model) {
        model.addAttribute("statuts", statutService.findAll());
        utils.currentPageAndContent(model, "demandeStatutFormAjout", "demandeStatut/ajout");
        return "layout";
    }
    @PostMapping("/demandeStatut/creer")
    public String creerDemandeStatut(Model model,
        @RequestParam("refDemande") String refDemande,
        @RequestParam("dateChangementStatut") String dateChangementStatut,
        @RequestParam("idStatut") Integer idStatut
    ) {
        utils.currentPageAndContent(model, "demandeStatutFormAjout", "demandeStatut/ajout");
        if(refDemande == null 
            || dateChangementStatut.isEmpty() 
            || dateChangementStatut.isBlank() 
            || dateChangementStatut == null
            || idStatut == null
        ) {
            utils.addErrorMessage(model, "Error, please fill all the fields");
            return "layout";
        }
        DemandeStatut demandeStatut = new DemandeStatut();
        demandeStatut.setDemande(demandeService.findById(refDemande));
        demandeStatut.setDateChangementStatut(LocalDateTime.parse(dateChangementStatut));
        demandeStatut.setStatut(statutService.findById(idStatut));

        demandeStatutService.saveWithDureeTravailleeUpdate(demandeStatut);

        model.addAttribute("statuts", statutService.findAll());
        utils.currentPageAndContent(model, "demandeStatutFormAjout", "demandeStatut/ajout");
        utils.addSuccessMessage(model, "Demande statut ajouté avec succès");
        return "layout";
    }
    @GetMapping("/demandeStatut/formUpdate/{idDemandeStatut}")
    public String formUpdate(Model model, @PathVariable("idDemandeStatut") Integer idDemandeStatut) {
        model.addAttribute("demandeStatut", demandeStatutService.findById(idDemandeStatut));
        model.addAttribute("statuts", statutService.findAll());
        utils.currentPageAndContent(model, "demandeStatutFormUpdate", "demandeStatut/update");
        return "layout";
    }
    @PostMapping("/demandeStatut/update")
    public String updateDemandeStatut(Model model,
        @RequestParam("idDemandeStatut") Integer idDemandeStatut,
        @RequestParam("refDemande") String refDemande,
        @RequestParam("dateChangementStatut") String dateChangementStatut,
        @RequestParam("idStatut") Integer idStatut
    ) {
        DemandeStatut demandeStatut = demandeStatutService.findById(idDemandeStatut);
        if(refDemande != null && !refDemande.equals(demandeStatut.getDemande().getReference())) {
            demandeStatut.setDemande(demandeService.findById(refDemande));
        }
        if(!dateChangementStatut.isEmpty() && !LocalDateTime.parse(dateChangementStatut).equals(demandeStatut.getDateChangementStatut())) {
            demandeStatut.setDateChangementStatut(LocalDateTime.parse(dateChangementStatut));
        }
        if(idStatut != null && idStatut != demandeStatut.getStatut().getId()) {
            demandeStatut.setStatut(statutService.findById(idStatut));
        }

        demandeStatutService.saveWithDureeTravailleeUpdate(demandeStatut);

        utils.addSuccessMessage(model, "Demande statut mis à jour avec succès");
        utils.currentPageAndContent(model, "demandeStatutFormUpdate", "demandeStatut/update");
        model.addAttribute("statuts", statutService.findAll());
        model.addAttribute("demandeStatut", demandeStatutService.findById(idDemandeStatut));

        return "layout";
    }
    @GetMapping("/demandeStatut/liste")
    public String listeDemandeStatut(Model model) {
        model.addAttribute("demandeStatuts", demandeStatutService.findAll());
        utils.currentPageAndContent(model, "demandeStatutListe", "demandeStatut/liste");
        return "layout";
    }
    @GetMapping("/demandeStatut/delete/{idDemandeStatut}")
    public String deleteDemandeStatut(RedirectAttributes redirectAttributes, @PathVariable("idDemandeStatut") Integer idDemandeStatut) {
        demandeStatutService.deleteById(idDemandeStatut);
        utils.addSuccessMessage(redirectAttributes, "Demande statut supprimé avec succès");
        return "redirect:/demandeStatut/liste";
    }
    @GetMapping("/dsJson")
    @ResponseBody
    public String testTopDS() {
        DemandeStatut ds = demandeStatutService.findById(5);
        ds = demandeStatutService.findPreviousDemandeStatut(ds);
        return String.format("{\"idDemandeStatut\" : %d}", ds.getId());
    }
    @GetMapping("/dsJsonObj")
    @ResponseBody
    public Map<Object, Object> testTopDSObj() {
        Map <Object, Object> retour = new HashMap<>();

        DemandeStatut ds = demandeStatutService.findById(5);

        retour.put("dureeTravaillee", demandeStatutService.calculerDureeTravaillee(ds));

        ds = demandeStatutService.findPreviousDemandeStatut(ds);

        retour.put("idDemandeStatut", ds.getId());

        retour.put("statut", ds.getStatut().getLibelle());

        return retour;
    }
    @GetMapping("/followingDS")
    @ResponseBody
    public Map<Object, Object> testFollowing() {
        Map <Object, Object> retour = new HashMap<>();
        DemandeStatut ds = demandeStatutService.findById(12);

        retour.put("followingDS", demandeStatutService.findFollowingDemandeStatuts(ds));

        return retour;
    }
}
