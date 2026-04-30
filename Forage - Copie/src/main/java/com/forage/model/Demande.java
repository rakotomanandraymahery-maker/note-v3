package com.forage.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class Demande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "date")
    private LocalDateTime date;
    
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
    
    @Column(name = "lieu")
    private String lieu;
    
    @Column(name = "district")
    private String district;

    @OneToMany(mappedBy = "demande", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DemandeStatut> demandeStatuts;

    public Demande(LocalDateTime date, Client client, String lieu, String district) {
        this.date = date;
        this.client = client;
        this.lieu = lieu;
        this.district = district;
    }

    public Demande() {}
    
    public Statut getStatutActuel() {
        if (demandeStatuts == null || demandeStatuts.isEmpty()) {
            return null;
        }
        return demandeStatuts.stream()
                .max((ds1, ds2) -> ds1.getDateHeure().compareTo(ds2.getDateHeure()))
                .map(DemandeStatut::getStatut)
                .orElse(null);
    }
    
    public List<DemandeStatut> getHistoriqueStatuts() {
        if (demandeStatuts == null) {
            return new ArrayList<>();
        }
        return demandeStatuts.stream()
                .sorted((ds1, ds2) -> ds2.getDateHeure().compareTo(ds1.getDateHeure()))
                .toList();
    }
    
    public void ajouterStatut(Statut statut, LocalDateTime dateHeure, String observation) {
        DemandeStatut demandeStatut = new DemandeStatut(this, statut, dateHeure, observation);
        if (demandeStatuts == null) {
            demandeStatuts = new ArrayList<>();
        }
        demandeStatuts.add(demandeStatut);
    }

}