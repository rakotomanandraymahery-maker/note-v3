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
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "contact", nullable = false)
    private String contact;
    
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<Demande> demandes;

}