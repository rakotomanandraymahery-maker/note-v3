package com.forage.controller;

import com.forage.model.DevisType;
import com.forage.model.Statut;
import com.forage.controller.dto.*;
import com.forage.model.Demande;
import com.forage.model.DemandeStatut;
import com.forage.model.Devis;
import com.forage.model.DevisDetails;
import com.forage.service.DemandeServ;
import com.forage.service.DemandeStatutServ;
import com.forage.service.DevisDetailsServ;
import com.forage.service.DevisServ;
import com.forage.service.DevisTypeServ;
import com.forage.service.StatutServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/devis")
@CrossOrigin(origins = "*")
public class DevisController {

    @Autowired
    private DemandeServ demandeService;
    
    @Autowired
    private DevisTypeServ devisTypeService;

    @Autowired
    private DevisServ devisService;

    @Autowired
    private DevisDetailsServ devisDetailsService;

    @Autowired
    private StatutServ statutServ;

    @Autowired
    private DemandeStatutServ demandeStatutServ;


    @GetMapping
    public String devis(Model model) {
        List<DevisType> devisTypes = devisTypeService.findAll();
        List<Devis> devis = devisService.getAllDevis();

        model.addAttribute("devisTypes", devisTypes);
        model.addAttribute("devis", devis);
        model.addAttribute("activePage", "devis");

        return "devis";
    }

    @GetMapping("/{id}")
    public String details(Model model, @PathVariable Long id) {
        List<Devis> deviss = devisService.getAllDevis();
        Devis devis = devisService.getDevisById(id).orElse(null);
        
        model.addAttribute("devis", deviss);
        
        if (devis != null && devis.getDevisDetails() != null) {
            model.addAttribute("devisDetails", devis.getDevisDetails());
        }
        
        return "devis";
    }

    @GetMapping("/recherche-demande")
    @ResponseBody
    public ResponseEntity<DemandeDTO> rechercherDemande(@RequestParam Long id) {
        Demande demande = demandeService.findById(id);
        if (demande != null) {
            DemandeDTO dto = new DemandeDTO();
            dto.setId(demande.getId());
            dto.setDate(demande.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yy HH:mm")));
            dto.setLieu(demande.getLieu());
            dto.setDistrict(demande.getDistrict());
            
            if (demande.getClient() != null) {
                dto.setClientNom(demande.getClient().getNom());
            }
            
            if (demande.getStatutActuel() != null) {
                dto.setStatutActuel(demande.getStatutActuel().getNom());
            }
            
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    // @GetMapping("/types-devis")
    // @ResponseBody
    // public ResponseEntity<List<DevisType>> getTypesDevis() {
    //     List<DevisType> devisTypes = devisTypeService.findAll();
    //     return ResponseEntity.ok(devisTypes);
    // }

    @PostMapping("/creer-devis")
    @ResponseBody
    public ResponseEntity<?> creerDevis(@RequestBody DevisRequest devisRequest) {
        try {
            
            Devis devis = new Devis();
            devis.setDateHeure(LocalDateTime.now());
            
            Demande demande = demandeService.findById(devisRequest.getDemandeId());
            devis.setDemande(demande);
            
            DevisType devisType = devisTypeService.findById(devisRequest.getDevisTypeId());
            devis.setDevisType(devisType);
            
            Devis savedDevis = devisService.createDevis(devis);
            
            for (DevisRequest.DetailDTO detail : devisRequest.getDetails()) {
                DevisDetails devisDetails = new DevisDetails();
                devisDetails.setPu(detail.getPu());
                devisDetails.setDevis(savedDevis);
                devisDetails.setLibelle(detail.getLibelle());
                devisDetails.setQte(detail.getQte());
                devisDetailsService.createDevisDetails(devisDetails);
            }
            String devisTypeNom = devisType.getNom().toLowerCase();
            Statut statut = statutServ.findByNom("devis " + devisTypeNom + " cree");
            if (statut != null) {
                DemandeStatut demandeStatut = new DemandeStatut(demande, statut, LocalDateTime.now());
                demandeStatutServ.save(demandeStatut);
            }

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteDevis(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            devisService.delete(id);
            redirectAttributes.addFlashAttribute("message", "Devis supprimé avec succès");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de la suppression");
        }
        
        return "redirect:/devis";
    }

    @GetMapping("/delete/details/{id}")
    public String deleteDevisDetail(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            devisDetailsService.delete(id);
            redirectAttributes.addFlashAttribute("message", "Détail supprimé avec succès");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de la suppression");
        }
        
        return "redirect:/devis";
    }

    @GetMapping("/accepter/{id}")
    public String acceptDevis(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            Devis devis = devisService.getDevisById(id).orElse(null);
            if (devis != null) {
                String devisTypeNom = devis.getDevisType().getNom().toLowerCase();
                Statut statut = statutServ.findByNom("devis " + devisTypeNom + " acceptee");
                if (statut != null) {
                    DemandeStatut demandeStatut = new DemandeStatut(devis.getDemande(), statut, LocalDateTime.now());
                    demandeStatutServ.save(demandeStatut);
                }
                redirectAttributes.addFlashAttribute("message", "Devis accepté avec succès");
            } else {
                redirectAttributes.addFlashAttribute("error", "Devis non trouvé");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de l'acceptation: " + e.getMessage());
        }
        
        return "redirect:/devis";
    }

    @GetMapping("/refuser/{id}")
    public String refuseDevis(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            Devis devis = devisService.getDevisById(id).orElse(null);
            if (devis != null) {
                String devisTypeNom = devis.getDevisType().getNom().toLowerCase();
                Statut statut = statutServ.findByNom("devis " + devisTypeNom + " refusee");
                if (statut != null) {
                    DemandeStatut demandeStatut = new DemandeStatut(devis.getDemande(), statut, LocalDateTime.now());
                    demandeStatutServ.save(demandeStatut);
                }
                redirectAttributes.addFlashAttribute("message", "Devis refusé avec succès");
            } else {
                redirectAttributes.addFlashAttribute("error", "Devis non trouvé");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors du refus: " + e.getMessage());
        }
        
        return "redirect:/devis";
    }


    @GetMapping("/{id}/ajouter-details")
    public String pageAjoutDetails(@PathVariable Long id, Model model) {
        Devis devis = devisService.getDevisById(id).orElse(null);
        
        model.addAttribute("devis", devis);
        return "devis-details"; // nouveau html
    }

    @PostMapping("/{id}/ajouter-details")
    @ResponseBody
    public ResponseEntity<?> ajouterDetails(
            @PathVariable Long id,
            @RequestBody List<DevisRequest.DetailDTO> details) {
            // @RequestBody List<DevisRequest.DetailDTO> details) {

        try {
            Devis devis = devisService.getDevisById(id).orElse(null);

            if (devis == null) {
                return ResponseEntity.badRequest().body("Devis introuvable");
            }

            for (DevisRequest.DetailDTO detail : details) {
                DevisDetails devisDetails = new DevisDetails();
                devisDetails.setPu(detail.getPu());
                devisDetails.setDevis(devis);
                devisDetails.setLibelle(detail.getLibelle());
                devisDetails.setQte(detail.getQte());

                devisDetailsService.createDevisDetails(devisDetails);
            }

            return ResponseEntity.ok().build();

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    // @GetMapping("/chiffreAffaire")
    // public String chiffreAffaire( Model model) {
    //     double chiffreAffaire = devisService.chiffreAffaire();
        
    //     model.addAttribute("chiffreAffaire", chiffreAffaire);
    //     return "chiffreAffaire"; // nouveau html
    // }

    @GetMapping("/chiffreAffaire")
    public String chiffreAffaire( Model model) {
        BigDecimal chiffreAffaire = devisService.chiffreAffaire1();
        
        model.addAttribute("chiffreAffaire", chiffreAffaire);

        model.addAttribute("activePage", "chiffreAffaire");

        return "chiffreAffaire"; // nouveau html
    }

    


}