package com.example.forage.controller;

import com.example.forage.model.Client;
import com.example.forage.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller REST pour la gestion des clients.
 * Expose les endpoints CRUD pour l'entité Client.
 */
@RestController
@RequestMapping("/api/clients")
public class ClientController {
    
    @Autowired
    private ClientService clientService;
    
    /**
     * Créer un nouveau client
     * POST /api/clients
     */
    @PostMapping
    public ResponseEntity<Client> createClient(@RequestBody Client client) {
        Client savedClient = clientService.saveClient(client);
        return new ResponseEntity<>(savedClient, HttpStatus.CREATED);
    }
    
    /**
     * Lister tous les clients
     * GET /api/clients
     */
    @GetMapping
    public List<Client> getAllClients() {
        return clientService.getAllClients();
    }
    
    /**
     * Récupérer un client par son ID
     * GET /api/clients/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable Long id) {
        Optional<Client> client = clientService.getClientById(id);
        if (client.isPresent()) {
            return ResponseEntity.ok(client.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Mettre à jour un client existant
     * PUT /api/clients/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable Long id, @RequestBody Client clientDetails) {
        Client updatedClient = clientService.updateClient(id, clientDetails);
        if (updatedClient != null) {
            return ResponseEntity.ok(updatedClient);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Supprimer un client
     * DELETE /api/clients/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteClient(@PathVariable Long id) {
        boolean deleted = clientService.deleteClient(id);
        if (deleted) {
            return ResponseEntity.ok("Client supprimé avec succès");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Client non trouvé");
        }
    }
    
    /**
     * Compter le nombre total de clients
     * GET /api/clients/count
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countClients() {
        long count = clientService.countClients();
        return ResponseEntity.ok(count);
    }
    
    /**
     * Vérifier si un client existe
     * GET /api/clients/{id}/exists
     */
    @GetMapping("/{id}/exists")
    public ResponseEntity<Boolean> clientExists(@PathVariable Long id) {
        boolean exists = clientService.clientExists(id);
        return ResponseEntity.ok(exists);
    }
}
