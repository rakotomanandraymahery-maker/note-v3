package com.note.service;

import com.note.model.Candidat;
import com.note.model.Matiere;
import com.note.model.Note;
import com.note.model.NoteFinal;
import com.note.model.parametre.Parametre;
import com.note.model.parametre.Resolution;
import com.note.repository.CandidatRepo;
import com.note.repository.MatiereRepo;
import com.note.repository.NoteFinalRepo;
import com.note.repository.NoteRepo;
import com.note.repository.parametre.ParametreRepo;
import com.note.service.parametre.ParametreServ;

// import org.hibernate.mapping.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.List;

@Service
public class NoteFinalServ {
    @Autowired
    private CandidatRepo candidatRepo;

    @Autowired
    private MatiereRepo matiereRepo;

    @Autowired
    private NoteFinalRepo noteFinalRepo;

    @Autowired
    private ParametreRepo parametreRepo;

    @Autowired
    private ParametreServ parametreServ;

    @Autowired
    private NoteServ noteServ;
    
//////
    public double getNoteFinalByMatiere(Long candidatId, Long matiereId, Long examenId) {
        List<Parametre> parametres = parametreRepo.findByMatiereId(matiereId); //   ,examenId
        
        // Vérification si des paramètres existent pour cette matière
        if (parametres == null || parametres.isEmpty()) {
            throw new RuntimeException("Aucun paramètre de calcul configuré pour la matière ID: " + matiereId);
        }
        
        List<Note> notes = noteServ.getNotesByCandidatIdAndMatiereIdAndExamenId(candidatId,  matiereId, examenId);
        
        // Vérification si des notes existent
        if (notes == null || notes.isEmpty()) {
            throw new RuntimeException("Aucune note trouvée pour le candidat ID: " + candidatId + ", matière ID: " + matiereId + ", examen ID: " + examenId);
        }
        
        double sumDiff = noteServ.getSumDiffsNotes(notes);
        System.out.println("DEBUG: SumDiff calculé = " + sumDiff);
        System.out.println("DEBUG: Notes = " + notes.stream().map(n -> n.getNote()).toList());

        List<Parametre> selectedParametres = noteServ.getAllParametreSumDiff(sumDiff, parametres);
        
        // Vérification si des paramètres correspondent aux conditions
        if (selectedParametres == null || selectedParametres.isEmpty()) {
            throw new RuntimeException("Aucun paramètre ne correspond aux conditions pour sumDiff: " + sumDiff);
        }
        
        String plusProche_plusLoin = "plus proche";
        System.out.println("DEBUG: Paramètres sélectionnés = " + selectedParametres.size());
        for (Parametre p : selectedParametres) {
            System.out.println("DEBUG: Paramètre - Op: " + p.getOperateur().getOperateur() + 
                             ", Seuil: " + p.getSeuilSumDiff() + 
                             ", Résolution: " + p.getResolution().getResolution());
        }
        
        Parametre parametre = noteServ.getParametreBySelected(selectedParametres, sumDiff, plusProche_plusLoin);
        
        // Vérification si un paramètre a été sélectionné
        if (parametre == null) {
            throw new RuntimeException("Aucun paramètre sélectionné pour le calcul");
        }
        
        System.out.println("DEBUG: Paramètre choisi - Op: " + parametre.getOperateur().getOperateur() + 
                         ", Seuil: " + parametre.getSeuilSumDiff() + 
                         ", Résolution: " + parametre.getResolution().getResolution());

        Candidat candidat = candidatRepo.findById(candidatId).orElse(null);
        Matiere matiere = matiereRepo.findById(matiereId).orElse(null);
        double note = getNoteByResolution(parametre, notes);

        NoteFinal noteFinal = new NoteFinal(candidat, matiere, parametre.getResolution(), note);

        return note;
    }

//////
    private double getNoteByResolution(Parametre parametre, List<Note> notes) {
        double note = 0;
        if (parametre != null) {
            if (parametre.getResolution().getResolution().equals("plus petit")) {
                note = getNotePlusPetit(notes);
            } else if (parametre.getResolution().getResolution().equals("moyenne")) {
                note = getNoteMoyenne(notes);
            } else if (parametre.getResolution().getResolution().equals("plus grand")) {
                note = getNotePlusGrand(notes);
            } else {
                note = getNoteMoyenne(notes); 
            }
        }
        return note;
    }

//////
    private Double getNotePlusPetit (List<Note> notes) {
        notes = noteServ.sortNotesReversed(notes);
        return notes.get(notes.size() - 1).getNote();
    }

    private Double getNoteMoyenne(List<Note> notes) {
        if (notes == null || notes.isEmpty()) { return 0.0; }
        double somme = 0.0;
        for (Note note : notes) {
            somme += note.getNote();
        }
        return somme / notes.size();
    }

    private Double getNotePlusGrand(List<Note> notes) {
        List<Note> sortedNotes = noteServ.sortNotesReversed(notes);
        return sortedNotes.get(0).getNote();
    }


    
}