package com.forage.repository;

import com.forage.model.Statut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatutRepo extends JpaRepository<Statut, Long> {
    public Statut findByNom(String nom);
}