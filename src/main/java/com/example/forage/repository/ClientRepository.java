package com.example.forage.repository;

import com.example.forage.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository pour l'entité Client.
 * Fournit les opérations CRUD de base pour les clients.
 */
@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    
    // JpaRepository fournit déjà :
    // - save() : sauvegarder/mettre à jour
    // - findById() : rechercher par ID
    // - findAll() : lister tous les clients
    // - deleteById() : supprimer par ID
    // - existsById() : vérifier l'existence
    // - count() : compter le nombre de clients
    
    // Méthodes personnalisées possibles :
    // List<Client> findByNom(String nom);
    // List<Client> findByContactContaining(String contact);
    // Optional<Client> findByNomAndContact(String nom, String contact);
}
