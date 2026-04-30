package com.forage.service;

import com.forage.model.Demande;
import com.forage.model.DemandeStatut;
import com.forage.repository.DemandeStatutRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DemandeStatutServ {

    @Autowired
    private DemandeStatutRepo demandeStatutRepo;

    public List<DemandeStatut> findAll() {
        return demandeStatutRepo.findAll();
    }

    public DemandeStatut save(DemandeStatut demandeStatut) {
        return demandeStatutRepo.save(demandeStatut);
    }

    public Optional<DemandeStatut> findById(Long id) {
        return demandeStatutRepo.findById(id);
    }

    public DemandeStatut update(Long id, DemandeStatut demandeDetails) {
        Optional<DemandeStatut> demandeOpt = demandeStatutRepo.findById(id);
        if (demandeOpt.isPresent()) {
            DemandeStatut demande = demandeOpt.get();
            demande.setDemande(demandeDetails.getDemande());
            demande.setStatut(demandeDetails.getStatut());
            demande.setDateHeure(demandeDetails.getDateHeure());
            return demandeStatutRepo.save(demande);
        }
        return null;
    }

    public void delete(Long id) {
        demandeStatutRepo.deleteById(id);
    }

    public void deleteByStatutId(Long statutId) {
        demandeStatutRepo.deleteByStatutId(statutId);
    }

    
}