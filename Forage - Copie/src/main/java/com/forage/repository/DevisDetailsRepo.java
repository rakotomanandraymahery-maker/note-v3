package com.forage.repository;

import com.forage.model.DevisDetails;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DevisDetailsRepo extends JpaRepository<DevisDetails, Long> {
    List<DevisDetails> findByDevisId(Long devisId);
}