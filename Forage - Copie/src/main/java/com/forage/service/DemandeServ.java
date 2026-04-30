package com.forage.service;

import com.forage.model.Demande;
import com.forage.repository.DemandeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DemandeServ {

    @Autowired
    private DemandeRepo demandeRepo;

    public Demande findById(Long id) {
        Optional<Demande> demande = demandeRepo.findById(id);
        return demande.orElse(null);
    }

    public List<Demande> findAll() {
        return demandeRepo.findAll();
    }

    public Demande save(Demande demande) {
        return demandeRepo.save(demande);
    }

    public Demande update(Long id, Demande demandeDetails) {
        Optional<Demande> demandeOpt = demandeRepo.findById(id);
        if (demandeOpt.isPresent()) {
            Demande demande = demandeOpt.get();
            demande.setDate(demandeDetails.getDate());
            demande.setClient(demandeDetails.getClient());
            demande.setLieu(demandeDetails.getLieu());
            demande.setDistrict(demandeDetails.getDistrict());
            return demandeRepo.save(demande);
        }
        return null;
    }

    public void delete(Long id) {
        demandeRepo.deleteById(id);
    }

}