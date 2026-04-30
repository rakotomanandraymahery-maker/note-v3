package com.forage.controller.dto;

import java.time.LocalDateTime;

public class DemandeDTO {
    private Long id;
    private String date;
    private String lieu;
    private String district;
    private String clientNom;
    private String statutActuel;
    
    public DemandeDTO() {}
    
    public DemandeDTO(Long id, String date, String lieu, String district, String clientNom, String statutActuel) {
        this.id = id;
        this.date = date;
        this.lieu = lieu;
        this.district = district;
        this.clientNom = clientNom;
        this.statutActuel = statutActuel;
    }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    
    public String getLieu() { return lieu; }
    public void setLieu(String lieu) { this.lieu = lieu; }
    
    public String getDistrict() { return district; }
    public void setDistrict(String district) { this.district = district; }
    
    public String getClientNom() { return clientNom; }
    public void setClientNom(String clientNom) { this.clientNom = clientNom; }
    
    public String getStatutActuel() { return statutActuel; }
    public void setStatutActuel(String statutActuel) { this.statutActuel = statutActuel; }
    
}