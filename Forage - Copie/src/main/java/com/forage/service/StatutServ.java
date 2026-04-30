package com.forage.service;

import com.forage.model.Statut;
import com.forage.repository.StatutRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StatutServ {

    @Autowired
    private StatutRepo statutRepo;

    public List<Statut> findAll() {
        return statutRepo.findAll();
    }

    public Statut save(Statut statut) {
        return statutRepo.save(statut);
    }

    public Optional<Statut> findById(Long id) {
        return statutRepo.findById(id);
    }

    public Statut findByNom(String nom) {
        return statutRepo.findByNom(nom);
    }

    public Statut update(Long id, Statut statutDetails) {
        Optional<Statut> statutOpt = statutRepo.findById(id);
        if (statutOpt.isPresent()) {
            Statut statut = statutOpt.get();
            statut.setNom(statutDetails.getNom());
            return statutRepo.save(statut);
        }
        return null;
    }

    public void delete(Long id) {
        statutRepo.deleteById(id);
    }
}