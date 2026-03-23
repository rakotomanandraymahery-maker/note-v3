package com.note.service;

import com.note.model.Note;
import com.note.model.parametre.Parametre;
import com.note.repository.NoteRepo;
import com.note.repository.parametre.ParametreRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
// import java.util.Optional;
import java.util.Optional;

@Service
public class NoteServ {

    @Autowired
    private NoteRepo noteRepo;

    @Autowired
    private ParametreRepo parametreRepo;
    
//////
    public List<Note> getNotesByCandidatIdAndMatiereIdAndExamenId(Long candidatId, Long matiereId, Long examenId) {
        return sortNotesReversed(noteRepo.findByCandidatIdAndMatiereIdAndExamenId(candidatId, matiereId, examenId));
    }

//////
    public Double getSumDiffsNotes(List<Note> notes) {
        List<Double> diffs = getDiffsNotes(notes);
        double sum = 0;
        for (Double diff : diffs) {
            sum += diff;
        }
        return sum;
    }

    private List<Double> getDiffsNotes(List<Note> notes) {
        notes = sortNotesReversed(notes);
        List<Double> diffs = new ArrayList<>();
        for (int i = 0; i < notes.size(); i++) {
            for (int j = i + 1; j < notes.size(); j++) {
                diffs.add(notes.get(i).getNote() - notes.get(j).getNote());
            }
        }
        return diffs;
    }

    public List<Note> sortNotesReversed(List<Note> notes) {
        notes.sort(Comparator.comparingDouble(Note::getNote).reversed());
        return notes;
    }

/////
    public List<Parametre> getAllParametreSumDiff(double sumDiff, List<Parametre> parametresMatiere) {
        List<Parametre> selectedParametres = new ArrayList<Parametre>();

        for (Parametre parametre : parametresMatiere) {
            String op = parametre.getOperateur().getOperateur();
            if (op.equals(">") && sumDiff > parametre.getSeuilSumDiff()) {
                selectedParametres.add(parametre);
            } else if (op.equals(">=") && sumDiff >= parametre.getSeuilSumDiff()) {
                selectedParametres.add(parametre);
            } else if (op.equals("<") && sumDiff < parametre.getSeuilSumDiff()) {
                selectedParametres.add(parametre);
            } else if (op.equals("<=") && sumDiff <= parametre.getSeuilSumDiff()) {
                selectedParametres.add(parametre);
            } else if (op.equals("==") && sumDiff == parametre.getSeuilSumDiff()) {
                selectedParametres.add(parametre);
            }
        }
        return selectedParametres;
    }

    public Parametre getParametreBySelected(List<Parametre> selectedParametres, double sumDiff, String plusProche_plusLoin) {

        Parametre meilleur = null;
        double meilleureDistance = Double.MAX_VALUE;

        if (plusProche_plusLoin.equals("plus proche")) {
            for (Parametre parametre : selectedParametres) {
                double seuil = parametre.getSeuilSumDiff();
                double distance;
                if (sumDiff > seuil) {
                    distance = sumDiff - seuil;
                } else {
                    distance = seuil - sumDiff;
                }

                if (meilleur == null || distance < meilleureDistance || (distance == meilleureDistance && seuil < meilleur.getSeuilSumDiff())) {
                    meilleureDistance = distance;
                    meilleur = parametre;
                }
            }
        }
        else if (plusProche_plusLoin.equals("plus loin")) {
            for (Parametre parametre : selectedParametres) {
                double seuil = parametre.getSeuilSumDiff();
                double distance;
                if (sumDiff > seuil) {
                    distance = sumDiff - seuil;
                } else {
                    distance = seuil - sumDiff;
                }

                if (meilleur == null || distance > meilleureDistance || (distance == meilleureDistance && seuil > meilleur.getSeuilSumDiff())) {
                    meilleureDistance = distance;
                    meilleur = parametre;
                }
            }
        }

        return meilleur;
    }

////// CRUD

   public List<Note> findAll() {
        return noteRepo.findAll();
    }

    public Note save(Note note) {
        return noteRepo.save(note);
    }

    public Optional<Note> findById(Long id) {
        return noteRepo.findById(id);
    }

    public Note update(Long id, Note noteDetails) {
        Optional<Note> noteOpt = noteRepo.findById(id);
        if (noteOpt.isPresent()) {
            Note note = noteOpt.get();
            note.setCorrecteur(noteDetails.getCorrecteur());
            note.setCandidat(noteDetails.getCandidat());
            note.setMatiere(noteDetails.getMatiere());
            note.setExamen(noteDetails.getExamen());
            note.setNote(noteDetails.getNote());
            return noteRepo.save(note);
        }
        return null;
    }

    public void delete(Long id) {
        noteRepo.deleteById(id);
    }




}