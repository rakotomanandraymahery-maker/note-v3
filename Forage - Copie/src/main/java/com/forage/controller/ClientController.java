package com.forage.controller;

import com.forage.model.Client;
import com.forage.model.Demande;
import com.forage.service.ClientServ;
import com.forage.service.DemandeServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    private ClientServ clientService;
    
    @Autowired
    private DemandeServ demandeService;

    @GetMapping
    public String listClients(Model model) {
        List<Client> clients = clientService.findAll();
        model.addAttribute("clients", clients);
        model.addAttribute("selectedClient", null);
        model.addAttribute("clientDemandes", null);

        model.addAttribute("activePage", "client");
        return "client";
    }
    
    @GetMapping("/{id}")
    public String viewClientDetails(@PathVariable String id, Model model) {
        Long idLong = null;
        if(id != null) {
            idLong = Long.parseLong(id);
        }
        List<Client> clients = clientService.findAll();
        Client selectedClient = clientService.findById(idLong).orElse(null);
        
        model.addAttribute("clients", clients);
        model.addAttribute("selectedClient", selectedClient);
        
        if (selectedClient != null) {
            List<Demande> clientDemandes = selectedClient.getDemandes();
            model.addAttribute("clientDemandes", clientDemandes);
        }
        
        return "client";
    }

    @PostMapping("/save")
    public String saveClient(@ModelAttribute Client client, RedirectAttributes redirectAttributes) {
        try {
            if (client.getId() != null) {
                clientService.update(client.getId(), client);
                redirectAttributes.addFlashAttribute("message", "Client modifié avec succès");
            } else {
                clientService.save(client);
                redirectAttributes.addFlashAttribute("message", "Client ajouté avec succès");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de l'enregistrement");
        }
        
        return "redirect:/clients";
    }

    @GetMapping("/delete/{id}")
    public String deleteClient(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            clientService.delete(id);
            redirectAttributes.addFlashAttribute("message", "Client supprimé avec succès");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de la suppression");
        }
        return "redirect:/clients";
    }
    
}