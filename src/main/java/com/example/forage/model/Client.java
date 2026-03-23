package com.example.forage.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;

/**
 * Entité Client représente un client dans le système de forage.
 * Un client peut avoir plusieurs demandes de forage.
 */
@Entity
@Table(name = "clients")
public class Client {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nom;
    
    private String contact;
    
    private Double devis;
    
    /**
     * Relation OneToMany : Un client peut avoir plusieurs demandes
     * mappedBy = "client" : indique que le champ 'client' dans l'entité Demande gère la relation
     * cascade = CascadeType.ALL : les opérations (créer, mettre à jour, supprimer) sur le client
     *                          seront répercutées sur ses demandes associées
     */
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Demande> demandes;
    
    // Constructeur par défaut requis par JPA
    public Client() {
    }
    
    // Constructeur avec paramètres
    public Client(String nom, String contact, Double devis) {
        this.nom = nom;
        this.contact = contact;
        this.devis = devis;
    }
    
    // Getters et Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getNom() {
        return nom;
    }
    
    public void setNom(String nom) {
        this.nom = nom;
    }
    
    public String getContact() {
        return contact;
    }
    
    public void setContact(String contact) {
        this.contact = contact;
    }
    
    public Double getDevis() {
        return devis;
    }
    
    public void setDevis(Double devis) {
        this.devis = devis;
    }
    
    public List<Demande> getDemandes() {
        return demandes;
    }
    
    public void setDemandes(List<Demande> demandes) {
        this.demandes = demandes;
    }
    
    // toString() pour faciliter le débogage
    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", contact='" + contact + '\'' +
                ", devis=" + devis +
                '}';
    }
}
