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
public class Statut {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "nom", nullable = false, unique = true)
    private String nom;
    
    @OneToMany(mappedBy = "statut", cascade = CascadeType.ALL)
    private List<DemandeStatut> demandeStatuts;

}