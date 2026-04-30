package com.forage.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DevisDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "devis_id", nullable = false)
    private Devis devis;

    @Column(name = "libelle", nullable = false)
    private String libelle;

    @Column(name = "pu", nullable = false)
    private double pu;

    @Column(name = "qte", nullable = false)
    private double qte;

}