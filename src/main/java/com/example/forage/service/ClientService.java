package com.example.forage.service;

import com.example.forage.model.Client;
import com.example.forage.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service pour la gestion des clients.
 * Contient la logique métier pour les opérations CRUD sur les clients.
 */
@Service
public class ClientService {
    
    @Autowired
    private ClientRepository clientRepository;
    
    /**
     * Créer ou mettre à jour un client
     * @param client le client à sauvegarder
     * @return le client sauvegardé avec son ID
     */
    public Client saveClient(Client client) {
        return clientRepository.save(client);
    }
    
    /**
     * Récupérer tous les clients
     * @return la liste de tous les clients
     */
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }
    
    /**
     * Récupérer un client par son ID
     * @param id l'ID du client
     * @return le client trouvé ou Optional.empty() si non trouvé
     */
    public Optional<Client> getClientById(Long id) {
        return clientRepository.findById(id);
    }
    
    /**
     * Supprimer un client par son ID
     * @param id l'ID du client à supprimer
     * @return true si le client existait et a été supprimé, false sinon
     */
    public boolean deleteClient(Long id) {
        if (clientRepository.existsById(id)) {
            clientRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    /**
     * Mettre à jour un client existant
     * @param id l'ID du client à mettre à jour
     * @param clientDetails les nouvelles informations du client
     * @return le client mis à jour ou null si le client n'existe pas
     */
    public Client updateClient(Long id, Client clientDetails) {
        Optional<Client> optionalClient = clientRepository.findById(id);
        if (optionalClient.isPresent()) {
            Client client = optionalClient.get();
            client.setNom(clientDetails.getNom());
            client.setContact(clientDetails.getContact());
            client.setDevis(clientDetails.getDevis());
            return clientRepository.save(client);
        }
        return null;
    }
    
    /**
     * Vérifier si un client existe
     * @param id l'ID du client
     * @return true si le client existe, false sinon
     */
    public boolean clientExists(Long id) {
        return clientRepository.existsById(id);
    }
    
    /**
     * Compter le nombre total de clients
     * @return le nombre de clients
     */
    public long countClients() {
        return clientRepository.count();
    }
}
