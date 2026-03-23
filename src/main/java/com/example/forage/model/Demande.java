package com.example.forage.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDate;

/**
 * Entité Demande représente une demande de forage.
 * Une demande appartient à un seul client.
 */
@Entity
@Table(name = "demandes")
public class Demande {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private LocalDate date;
    
    private String district;
    
    private Double devis;
    
    /**
     * Relation ManyToOne : Plusieurs demandes peuvent appartenir à un seul client
     * @JoinColumn : Spécifie la colonne de clé étrangère dans la table demandes
     */
    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    @JsonIgnoreProperties({"demandes"})
    private Client client;
    
    // Constructeur par défaut requis par JPA
    public Demande() {
    }
    
    // Constructeur avec paramètres
    public Demande(LocalDate date, String district, Double devis, Client client) {
        this.date = date;
        this.district = district;
        this.devis = devis;
        this.client = client;
    }
    
    // Getters et Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public LocalDate getDate() {
        return date;
    }
    
    public void setDate(LocalDate date) {
        this.date = date;
    }
    
    public String getDistrict() {
        return district;
    }
    
    public void setDistrict(String district) {
        this.district = district;
    }
    
    public Double getDevis() {
        return devis;
    }
    
    public void setDevis(Double devis) {
        this.devis = devis;
    }
    
    public Client getClient() {
        return client;
    }
    
    public void setClient(Client client) {
        this.client = client;
    }
    
    // toString() pour faciliter le débogage
    @Override
    public String toString() {
        return "Demande{" +
                "id=" + id +
                ", date=" + date +
                ", district='" + district + '\'' +
                ", devis=" + devis +
                ", client=" + (client != null ? client.getNom() : "null") +
                '}';
    }
}
