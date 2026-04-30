package com.forage.repository;

import com.forage.model.Demande;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DemandeRepo extends JpaRepository<Demande, Long> {
}