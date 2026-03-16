package com.note.model;

import java.time.LocalDate;

import com.note.model.parametre.Resolution;

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
public class NoteFinal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "candidat_id", nullable = false)
    private Candidat candidat;

    @ManyToOne
    @JoinColumn(name = "matiere_id", nullable = false)
    private Matiere matiere;

    @ManyToOne
    @JoinColumn(name = "resolution_id", nullable = false)
    private Resolution resolution;

    @Column(name = "note", nullable = false)
    private double note;

    public NoteFinal(Candidat candidat, Matiere matiere, Resolution resolution, double note) {
        this.candidat = candidat;
        this.matiere = matiere;
        this.resolution = resolution;
        this.note = note;
    }

    public Long getId() { return id; }
    public Candidat getCandidat() { return candidat; }
    public Matiere getMatiere() { return matiere; }
    public Resolution getResolution() { return resolution; }
    public double getNote() { return note; }
    
    public void setId(Long id) { this.id = id; }
    public void setCandidat(Candidat candidat) { this.candidat = candidat; }
    public void setMatiere(Matiere matiere) { this.matiere = matiere; }
    public void setResolution(Resolution resolution) { this.resolution = resolution; }
    public void setNote(double note) { this.note = note; }
    
}