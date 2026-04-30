package com.forage.controller;

import com.forage.model.Demande;
import com.forage.model.Statut;
import com.forage.model.DemandeStatut;
import com.forage.service.DemandeServ;
import com.forage.service.DemandeStatutServ;
import com.forage.service.StatutServ;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/demandes-statuts")
public class DemandeStatutController {

    @Autowired
    private DemandeServ demandeService;
    
    @Autowired
    private DemandeStatutServ demandeStatutServ;

    @Autowired
    private StatutServ statutServ;

    
    @GetMapping
    public String listDemandesStatut(Model model) {
        List<Demande> demandes = demandeService.findAll();
        List<Statut> statuts = statutServ.findAll();

        model.addAttribute("demandes", demandes);
        model.addAttribute("statuts", statuts);

        model.addAttribute("activePage", "statut");
    
        return "demandeStatut";
    }
    
    @GetMapping("/{demandeId}/historique")
    public String getHistoriqueStatutByDemandeId(@PathVariable String demandeId, Model model) {
        Long idLong = null;
        if(demandeId != null) {
            idLong = Long.parseLong(demandeId);
        }
        Demande demande = demandeService.findById(idLong);

        if (demande != null) {
            List<DemandeStatut> demandesStatuts = demande.getHistoriqueStatuts();
            model.addAttribute("demandesStatuts", demandesStatuts);
            model.addAttribute("demande", demande);
        } else {
            model.addAttribute("message", "Aucun histotique de statut trouvé pour cette demande");
        }
        
        return "demandeStatut";
    }

    @GetMapping("/{demandeId}/ajouter-statut")
    public String showAjouterStatutForm(@PathVariable String demandeId, Model model) {
        Long idLong = null;
        if(demandeId != null) {
            idLong = Long.parseLong(demandeId);
        }
        
        Demande demande = demandeService.findById(idLong);
        List<Statut> statutsDisponibles = statutServ.findAll(); // MBOLA APINA ANLE STATUTS DISPO POUR LE DEMANDE
        
        model.addAttribute("demande", demande);
        model.addAttribute("statutsDisponibles", statutsDisponibles);
        
        return "ajouterStatutForm";
    }
    
    @PostMapping("/ajouter-statut")
    public String ajouterStatut(
            @RequestParam String demandeId,
            @RequestParam String statutId,
            @RequestParam(required = false) LocalDateTime dateHeure,
            @RequestParam(required = false) String observation) {
                
        Long idLongD = null;
        Long idLongS = null;
        if(demandeId != null) {
            idLongD = Long.parseLong(demandeId);
        }
        if(statutId != null) {
            idLongS = Long.parseLong(statutId);
        }
        try {
            Demande demande = demandeService.findById(idLongD);
            Statut statut = statutServ.findById(idLongS).orElse(null);
            
            if (demande != null && statut != null) {

                demande.ajouterStatut(statut, dateHeure != null ? dateHeure : LocalDateTime.now(), observation);
                
                demandeService.save(demande);
            } else {
                // Demande ou Statut non trouvé
            }
        } catch (Exception e) {
            // Erreur lors de l'ajout
        }
        
        return "redirect:/demandes-statuts";
    }
    
    
    @GetMapping("/{statutId}/supprimer")
    public String supprimerStatut(@PathVariable Long statutId) {
        try {
            DemandeStatut demandeStatut = demandeStatutServ.findById(statutId).orElse(null);
            
            if (demandeStatut != null) {
                Long demandeId = demandeStatut.getDemande().getId();
                demandeStatutServ.deleteByStatutId(statutId);
                
                return "redirect:/demandes-statuts/" + demandeId + "/historique";
            }
        } catch (Exception e) {
            // Erreur lors de la suppression
        }
        
        return "redirect:/demandes-statuts";
    }
    
    @GetMapping("/{demandeId}/supprimer-tous-statuts")
    public String supprimerTousStatuts(@PathVariable Long demandeId) {
        try {
            Demande demande = demandeService.findById(demandeId);
            
            if (demande != null && demande.getDemandeStatuts() != null) {
                demande.getDemandeStatuts().clear();
                demandeService.save(demande);
            }
        } catch (Exception e) {
            // Erreur lors de la suppression
        }
        
        return "redirect:/demandes-statuts/" + demandeId + "/historique";
    }

    
    
}