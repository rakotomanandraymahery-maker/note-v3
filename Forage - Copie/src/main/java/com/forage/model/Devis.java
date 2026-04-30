package com.forage.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
public class Devis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "date_heure", nullable = false)
    private LocalDateTime dateHeure;

    @ManyToOne
    @JoinColumn(name = "devis_type_id", nullable = false)
    private DevisType devisType;
    
    @ManyToOne
    @JoinColumn(name = "demande_id", nullable = false)
    private Demande demande;

    @OneToMany(mappedBy="devis", cascade = CascadeType.ALL)
    List<DevisDetails> devisDetails;

    public double getTotal() {
        if (devisDetails == null) { return 0; }
        return devisDetails.stream()
                .mapToDouble(detail -> detail.getPu() * detail.getQte())
                .sum();
    }

    public BigDecimal getTotal1() {
        if (devisDetails == null || devisDetails.isEmpty()) { 
            return BigDecimal.ZERO; 
        }
        double total = 0;
        for (DevisDetails detail : devisDetails) {
            total += detail.getPu() * detail.getQte();
        }
        BigDecimal valiny = BigDecimal.valueOf(total);
        valiny = valiny.setScale(1);
        return valiny;
    }

}