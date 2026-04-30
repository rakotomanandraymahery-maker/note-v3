package com.forage.controller;

import com.forage.model.Client;
import com.forage.model.Demande;
import com.forage.model.Statut;
import com.forage.model.DemandeStatut;
import com.forage.service.ClientServ;
import com.forage.service.DemandeServ;
import com.forage.service.DemandeStatutServ;
import com.forage.service.StatutServ;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/demandes")
public class DemandeController {

    @Autowired
    private DemandeServ demandeService;
    
    @Autowired
    private ClientServ clientService;

    @Autowired
    private DemandeStatutServ demandeStatutServ;

    @Autowired
    private StatutServ statutServ;

    @GetMapping
    public String listDemandes(Model model) {
        List<Demande> demandes = demandeService.findAll();
        List<Client> clients = clientService.findAll();
        
        model.addAttribute("demandes", demandes);
        model.addAttribute("clients", clients);
        model.addAttribute("selectedDemande", null);
        
        model.addAttribute("activePage", "demande");

        return "demande";
    }
    
    @GetMapping("/{id}")
    public String editDemande(@PathVariable String id, Model model) {
        Long idLong = null;
        if(id != null) {
            idLong = Long.parseLong(id);
        }

        List<Demande> demandes = demandeService.findAll();
        List<Client> clients = clientService.findAll();
        Demande selectedDemande = demandeService.findById(idLong);
        
        model.addAttribute("demandes", demandes);
        model.addAttribute("clients", clients);
        model.addAttribute("selectedDemande", selectedDemande);
        
        return "demande";
    }

    @GetMapping("/{id}/historique")
    public String demandeHistorique(@PathVariable String id, Model model) {
        Long idLong = null;
        if(id != null) {
            idLong = Long.parseLong(id);
        }

        Demande demande = demandeService.findById(idLong);
        List<DemandeStatut> demandeStatuts = demande.getHistoriqueStatuts();

        List<Demande> demandes = demandeService.findAll();
    
        model.addAttribute("demandeStatuts", demandeStatuts);
        model.addAttribute("selectedDemande", demande);

        model.addAttribute("demandes", demandes);
        
        return "demande";
    }

    @PostMapping("/save")
    public String saveDemande(@ModelAttribute Demande demande,
                            @RequestParam Long clientId,
                            RedirectAttributes redirectAttributes) {
        try {
            Client client = clientService.findById(clientId).orElse(null);
            demande.setClient(client);
            
            if (demande.getId() != null) {
                demandeService.update(demande.getId(), demande);
                redirectAttributes.addFlashAttribute("message", "Demande modifiée avec succès");
            } else {
                demande.setDate(LocalDateTime.now());
                demandeService.save(demande);
                
                Statut statut = statutServ.findByNom("demande cree");
                if (statut != null) {
                    DemandeStatut demandeStatut = new DemandeStatut(demande, statut, LocalDateTime.now());
                    demandeStatutServ.save(demandeStatut);
                }
                redirectAttributes.addFlashAttribute("message", "Demande ajoutée avec succès");
            }
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Erreur: " + e.getMessage());
        }
        
        return "redirect:/demandes";
    }
    
    @GetMapping("/delete/{id}")
    public String deleteDemande(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            demandeService.delete(id);
            redirectAttributes.addFlashAttribute("message", "Demande supprimée avec succès");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de la suppression");
        }
        
        return "redirect:/demandes";
    }
    
    
}