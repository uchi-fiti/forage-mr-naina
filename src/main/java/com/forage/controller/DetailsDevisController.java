package com.forage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.forage.model.DetailsDevis;
import com.forage.service.DetailsDevisService;

@Controller
public class DetailsDevisController {
    @Autowired
    private DetailsDevisService DetailsDevisService;

    @GetMapping("/details-demande/form")
    public String formDetailsDevis(Model model) {
        List<DetailsDevis> details = DetailsDevisService.findAll();
        model.addAttribute("DetailsDeviss", details);
        return "devis/index";
    }
}
