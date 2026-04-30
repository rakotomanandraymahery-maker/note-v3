package com.forage.repository;

import com.forage.model.DemandeStatut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DemandeStatutRepo extends JpaRepository<DemandeStatut, Long> {
    public void deleteByStatutId(Long statutId);
}