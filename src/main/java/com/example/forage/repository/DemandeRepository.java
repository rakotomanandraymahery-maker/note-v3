package com.example.forage.repository;

import com.example.forage.model.Demande;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repository pour l'entité Demande.
 * Fournit les opérations CRUD de base pour les demandes.
 */
@Repository
public interface DemandeRepository extends JpaRepository<Demande, Long> {
    
    // JpaRepository fournit déjà les opérations CRUD de base
    
    /**
     * Rechercher les demandes par district
     * @param district le district à rechercher
     * @return la liste des demandes dans ce district
     */
    List<Demande> findByDistrict(String district);
    
    /**
     * Rechercher les demandes par date
     * @param date la date à rechercher
     * @return la liste des demandes pour cette date
     */
    List<Demande> findByDate(LocalDate date);
    
    /**
     * Rechercher les demandes par client
     * @param clientId l'ID du client
     * @return la liste des demandes de ce client
     */
    List<Demande> findByClientId(Long clientId);
    
    /**
     * Rechercher les demandes dans une plage de dates
     * @param dateDebut date de début
     * @param dateFin date de fin
     * @return la liste des demandes dans cette période
     */
    @Query("SELECT d FROM Demande d WHERE d.date BETWEEN :dateDebut AND :dateFin")
    List<Demande> findByDateBetween(@Param("dateDebut") LocalDate dateDebut, 
                                   @Param("dateFin") LocalDate dateFin);
    
    /**
     * Compter les demandes par district
     * @param district le district
     * @return le nombre de demandes dans ce district
     */
    long countByDistrict(String district);
}
