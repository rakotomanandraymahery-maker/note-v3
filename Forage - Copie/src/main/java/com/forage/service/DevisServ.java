package com.forage.service;

import com.forage.model.Devis;
import com.forage.repository.DevisRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DevisServ {

    @Autowired
    private DevisRepo devisRepo;

    public Devis createDevis(Devis devis) {
        return devisRepo.save(devis);
    }

    public List<Devis> getAllDevis() {
        return devisRepo.findAll();
    }

    public Optional<Devis> getDevisById(Long id) {
        return devisRepo.findById(id);
    }

    public Devis updateDevis(Long id, Devis devisDetails) {
        Devis devis = devisRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Devis non trouvé avec l'id: " + id));
        
        devis.setDateHeure(devisDetails.getDateHeure());
        devis.setDevisType(devisDetails.getDevisType());
        devis.setDemande(devisDetails.getDemande());
        
        return devisRepo.save(devis);
    }

    public void delete(Long id) {
        devisRepo.deleteById(id);
    }

    public boolean existsDevis(Long id) {
        return devisRepo.existsById(id);
    }

    public void deleteAllDevis() {
        devisRepo.deleteAll();
    }

    public double chiffreAffaire() {
        List<Devis> devis = devisRepo.findAll();
        double resultat = 0;
        for (Devis devi : devis) {
            resultat += devi.getTotal();
        }
        return resultat;
    }

    public BigDecimal chiffreAffaire1() {
        List<Devis> devisList = devisRepo.findAll();
        double resultat = 0;
        for (Devis devi : devisList) {
            String targetStatus = ("devis " + devi.getDevisType().getNom() + " acceptee").toLowerCase();
            boolean isAccepted = false;
            
            if (devi.getDemande() != null && devi.getDemande().getDemandeStatuts() != null) {
                for (com.forage.model.DemandeStatut ds : devi.getDemande().getDemandeStatuts()) {
                    if (ds.getStatut().getNom().toLowerCase().equals(targetStatus)) {
                        isAccepted = true;
                        break;
                    }
                }
            }
            
            if (isAccepted) {
                resultat += devi.getTotal();
            }
        }

        BigDecimal valiny = BigDecimal.valueOf(resultat);
        valiny = valiny.setScale(1);

        return valiny;
    }
    

    
}