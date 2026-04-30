package com.forage.model;

import java.util.List;

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
public class DevisType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "nom", nullable = false)
    private String nom; // Etude de terrain / Forage

    @OneToMany(mappedBy="devisType", cascade = CascadeType.ALL)
    List<Devis> devis;

}