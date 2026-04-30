package com.forage.controller.dto;

import java.util.List;

public class DevisRequest {
    private Long demandeId;
    private Long devisTypeId;
    private String dateHeure;
    private List<DetailDTO> details;



    
    public Long getDemandeId() { return demandeId; }
    public void setDemandeId(Long demandeId) { this.demandeId = demandeId; }
    
    public Long getDevisTypeId() { return devisTypeId; }
    public void setDevisTypeId(Long devisTypeId) { this.devisTypeId = devisTypeId; }
    
    public String getDateHeure() { return dateHeure; }
    public void setDateHeure(String dateHeure) { this.dateHeure = dateHeure; }
    
    public List<DetailDTO> getDetails() { return details; }
    public void setDetails(List<DetailDTO> details) { this.details = details; }
    
    public static class DetailDTO {
        private String libelle;
        private double pu;
        private double qte;
        private double montant;
        
        public String getLibelle() { return libelle; }
        public void setLibelle(String libelle) { this.libelle = libelle; }
        
        public double getPu() { return pu; }
        public void setPu(double pu) { this.pu = pu; }
        
        public double getQte() { return qte; }
        public void setQte(double qte) { this.qte = qte; }
        
        public double getMontant() { return montant; }
        public void setMontant(double montant) { this.montant = montant; }
    }


}