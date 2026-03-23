package com.example.forage.service;

import com.example.forage.model.Client;
import com.example.forage.model.Demande;
import com.example.forage.repository.DemandeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Service pour la gestion des demandes de forage.
 * Contient la logique métier pour les opérations CRUD sur les demandes.
 */
@Service
public class DemandeService {
    
    @Autowired
    private DemandeRepository demandeRepository;
    
    @Autowired
    private ClientService clientService;
    
    /**
     * Créer une nouvelle demande
     * @param demande la demande à créer
     * @return la demande sauvegardée avec son ID
     */
    public Demande saveDemande(Demande demande) {
        return demandeRepository.save(demande);
    }
    
    /**
     * Créer une demande en l'associant à un client existant
     * @param demande la demande à créer
     * @param clientId l'ID du client à associer
     * @return la demande sauvegardée ou null si le client n'existe pas
     */
    public Demande saveDemandeWithClient(Demande demande, Long clientId) {
        Optional<Client> client = clientService.getClientById(clientId);
        if (client.isPresent()) {
            demande.setClient(client.get());
            return demandeRepository.save(demande);
        }
        return null;
    }
    
    /**
     * Récupérer toutes les demandes
     * @return la liste de toutes les demandes
     */
    public List<Demande> getAllDemandes() {
        return demandeRepository.findAll();
    }
    
    /**
     * Récupérer une demande par son ID
     * @param id l'ID de la demande
     * @return la demande trouvée ou Optional.empty() si non trouvée
     */
    public Optional<Demande> getDemandeById(Long id) {
        return demandeRepository.findById(id);
    }
    
    /**
     * Supprimer une demande par son ID
     * @param id l'ID de la demande à supprimer
     * @return true si la demande existait et a été supprimée, false sinon
     */
    public boolean deleteDemande(Long id) {
        if (demandeRepository.existsById(id)) {
            demandeRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    /**
     * Mettre à jour une demande existante
     * @param id l'ID de la demande à mettre à jour
     * @param demandeDetails les nouvelles informations de la demande
     * @return la demande mise à jour ou null si la demande n'existe pas
     */
    public Demande updateDemande(Long id, Demande demandeDetails) {
        Optional<Demande> optionalDemande = demandeRepository.findById(id);
        if (optionalDemande.isPresent()) {
            Demande demande = optionalDemande.get();
            demande.setDate(demandeDetails.getDate());
            demande.setDistrict(demandeDetails.getDistrict());
            demande.setDevis(demandeDetails.getDevis());
            
            // Mise à jour du client si nécessaire
            if (demandeDetails.getClient() != null) {
                demande.setClient(demandeDetails.getClient());
            }
            
            return demandeRepository.save(demande);
        }
        return null;
    }
    
    /**
     * Rechercher les demandes par district
     * @param district le district à rechercher
     * @return la liste des demandes dans ce district
     */
    public List<Demande> getDemandesByDistrict(String district) {
        return demandeRepository.findByDistrict(district);
    }
    
    /**
     * Rechercher les demandes par date
     * @param date la date à rechercher
     * @return la liste des demandes pour cette date
     */
    public List<Demande> getDemandesByDate(LocalDate date) {
        return demandeRepository.findByDate(date);
    }
    
    /**
     * Rechercher les demandes par client
     * @param clientId l'ID du client
     * @return la liste des demandes de ce client
     */
    public List<Demande> getDemandesByClient(Long clientId) {
        return demandeRepository.findByClientId(clientId);
    }
    
    /**
     * Rechercher les demandes dans une plage de dates
     * @param dateDebut date de début
     * @param dateFin date de fin
     * @return la liste des demandes dans cette période
     */
    public List<Demande> getDemandesBetweenDates(LocalDate dateDebut, LocalDate dateFin) {
        return demandeRepository.findByDateBetween(dateDebut, dateFin);
    }
    
    /**
     * Compter les demandes par district
     * @param district le district
     * @return le nombre de demandes dans ce district
     */
    public long countDemandesByDistrict(String district) {
        return demandeRepository.countByDistrict(district);
    }
}
