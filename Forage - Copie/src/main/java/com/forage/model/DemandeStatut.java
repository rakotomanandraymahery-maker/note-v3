package com.forage.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "demande_statut")
public class DemandeStatut {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "demande_id", nullable = false)
    private Demande demande;

    @ManyToOne
    @JoinColumn(name = "statut_id", nullable = false)
    private Statut statut;

    @Column(name = "date_heure", nullable = false)
    private LocalDateTime dateHeure;

    @Column(name="observation", length = 255)
    private String observation;
    
    public DemandeStatut(Demande demande, Statut statut, LocalDateTime dateHeure) {
        this.demande = demande;
        this.statut = statut;
        this.dateHeure = dateHeure;
    }

    public DemandeStatut(Demande demande, Statut statut, LocalDateTime dateHeure, String observation) {
        this.demande = demande;
        this.statut = statut;
        this.dateHeure = dateHeure;
        this.observation = observation;
    }
    
    public DemandeStatut() {}

}