package com.forage.repository;

import com.forage.model.DevisType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DevisTypeRepo extends JpaRepository<DevisType, Long> {
    
}