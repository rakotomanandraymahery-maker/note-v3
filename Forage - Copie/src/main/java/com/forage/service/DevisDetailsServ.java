package com.forage.service;

import com.forage.model.DevisDetails;
import com.forage.repository.DevisDetailsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DevisDetailsServ {

    @Autowired
    private DevisDetailsRepo devisDetailsRepo;

    public DevisDetails createDevisDetails(DevisDetails devisDetails) {
        return devisDetailsRepo.save(devisDetails);
    }

    public List<DevisDetails> getAllDevisDetails() {
        return devisDetailsRepo.findAll();
    }

    public Optional<DevisDetails> getDevisDetailsById(Long id) {
        return devisDetailsRepo.findById(id);
    }

    public DevisDetails updateDevisDetails(Long id, DevisDetails devisDetailsDetails) {
        DevisDetails devisDetails = devisDetailsRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Détail de devis non trouvé avec l'id: " + id));
        
        devisDetails.setDevis(devisDetailsDetails.getDevis());
        devisDetails.setLibelle(devisDetailsDetails.getLibelle());
        devisDetails.setPu(devisDetailsDetails.getPu());
        devisDetails.setQte(devisDetailsDetails.getQte());
        
        return devisDetailsRepo.save(devisDetails);
    }

    public void delete(Long id) {
        devisDetailsRepo.deleteById(id);
    }

    public boolean existsDevisDetails(Long id) {
        return devisDetailsRepo.existsById(id);
    }

    public void deleteAllDevisDetails() {
        devisDetailsRepo.deleteAll();
    }

    public List<DevisDetails> getDevisDetailsByDevisId(Long devisId) {
        return devisDetailsRepo.findByDevisId(devisId);
    }
}