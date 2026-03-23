package com.example.forage.controller;

import com.example.forage.model.Demande;
import com.example.forage.service.DemandeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Controller REST pour la gestion des demandes de forage.
 * Expose les endpoints CRUD pour l'entité Demande.
 */
@RestController
@RequestMapping("/api/demandes")
public class DemandeController {
    
    @Autowired
    private DemandeService demandeService;
    
    /**
     * Créer une nouvelle demande
     * POST /api/demandes
     */
    @PostMapping
    public ResponseEntity<Demande> createDemande(@RequestBody Demande demande) {
        Demande savedDemande = demandeService.saveDemande(demande);
        return new ResponseEntity<>(savedDemande, HttpStatus.CREATED);
    }
    
    /**
     * Créer une demande en l'associant à un client existant
     * POST /api/demandes/client/{clientId}
     */
    @PostMapping("/client/{clientId}")
    public ResponseEntity<Demande> createDemandeWithClient(@RequestBody Demande demande, @PathVariable Long clientId) {
        Demande savedDemande = demandeService.saveDemandeWithClient(demande, clientId);
        if (savedDemande != null) {
            return new ResponseEntity<>(savedDemande, HttpStatus.CREATED);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    
    /**
     * Lister toutes les demandes
     * GET /api/demandes
     */
    @GetMapping
    public List<Demande> getAllDemandes() {
        return demandeService.getAllDemandes();
    }
    
    /**
     * Récupérer une demande par son ID
     * GET /api/demandes/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Demande> getDemandeById(@PathVariable Long id) {
        Optional<Demande> demande = demandeService.getDemandeById(id);
        if (demande.isPresent()) {
            return ResponseEntity.ok(demande.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Mettre à jour une demande existante
     * PUT /api/demandes/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<Demande> updateDemande(@PathVariable Long id, @RequestBody Demande demandeDetails) {
        Demande updatedDemande = demandeService.updateDemande(id, demandeDetails);
        if (updatedDemande != null) {
            return ResponseEntity.ok(updatedDemande);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Supprimer une demande
     * DELETE /api/demandes/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDemande(@PathVariable Long id) {
        boolean deleted = demandeService.deleteDemande(id);
        if (deleted) {
            return ResponseEntity.ok("Demande supprimée avec succès");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Demande non trouvée");
        }
    }
    
    /**
     * Rechercher les demandes par district
     * GET /api/demandes/district/{district}
     */
    @GetMapping("/district/{district}")
    public List<Demande> getDemandesByDistrict(@PathVariable String district) {
        return demandeService.getDemandesByDistrict(district);
    }
    
    /**
     * Rechercher les demandes par date
     * GET /api/demandes/date/{date}
     */
    @GetMapping("/date/{date}")
    public List<Demande> getDemandesByDate(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return demandeService.getDemandesByDate(date);
    }
    
    /**
     * Rechercher les demandes par client
     * GET /api/demandes/client/{clientId}
     */
    @GetMapping("/client/{clientId}")
    public List<Demande> getDemandesByClient(@PathVariable Long clientId) {
        return demandeService.getDemandesByClient(clientId);
    }
    
    /**
     * Rechercher les demandes dans une plage de dates
     * GET /api/demandes/period?start=yyyy-MM-dd&end=yyyy-MM-dd
     */
    @GetMapping("/period")
    public List<Demande> getDemandesBetweenDates(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        return demandeService.getDemandesBetweenDates(start, end);
    }
    
    /**
     * Compter les demandes par district
     * GET /api/demandes/district/{district}/count
     */
    @GetMapping("/district/{district}/count")
    public ResponseEntity<Long> countDemandesByDistrict(@PathVariable String district) {
        long count = demandeService.countDemandesByDistrict(district);
        return ResponseEntity.ok(count);
    }
}
