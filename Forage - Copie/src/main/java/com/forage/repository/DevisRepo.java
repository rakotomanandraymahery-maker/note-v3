package com.forage.repository;

import com.forage.model.Devis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DevisRepo extends JpaRepository<Devis, Long> {
    
}