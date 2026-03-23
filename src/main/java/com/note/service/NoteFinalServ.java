package com.note.service;

import com.note.model.Candidat;
import com.note.model.Matiere;
import com.note.model.Note;
import com.note.model.NoteFinal;
import com.note.model.parametre.Parametre;
import com.note.repository.CandidatRepo;
import com.note.repository.MatiereRepo;
import com.note.repository.NoteFinalRepo;
import com.note.repository.parametre.ParametreRepo;
import com.note.service.parametre.ParametreServ;

// import org.hibernate.mapping.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
        List<Note> notes = noteServ.getNotesByCandidatIdAndMatiereIdAndExamenId(candidatId,  matiereId, examenId);
        double sumDiff = noteServ.getSumDiffsNotes(notes);

        List<Parametre> selectedParametres = noteServ.getAllParametreSumDiff(sumDiff, parametres);
        String plusProche_plusLoin = "plus proche";
        Parametre parametre = noteServ.getParametreBySelected(selectedParametres, sumDiff, plusProche_plusLoin);

        Candidat candidat = candidatRepo.findById(candidatId).orElse(null);
        Matiere matiere = matiereRepo.findById(matiereId).orElse(null);
        double note = getNoteByResolution(parametre, notes);

        NoteFinal noteFinal = new NoteFinal();
noteFinal.setCandidat(candidat);
noteFinal.setMatiere(matiere);
noteFinal.setNote(note);

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