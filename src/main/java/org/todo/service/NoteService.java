package org.todo.service;


import org.springframework.stereotype.Service;
import org.todo.pojos.Note;
import org.todo.repository.NoteRepository;

import java.util.List;
import java.util.Optional;

@Service
public class NoteService {

    private final NoteRepository noteRepository;


    //CONSTRUCTOR INJECTION
    public NoteService(NoteRepository noteRepository){
        this.noteRepository = noteRepository;
    }

    public void addNote(String title,String content,String emoji){
        noteRepository.save(new Note(title,content,emoji));
    }
    public void saveUpdatedNote(Note note){
        noteRepository.save(note);
    }

    public List<Note> getAllNotes() {
        return noteRepository.findAll();
    }

    public void deleteNote(int id) {
        noteRepository.deleteById(id);
    }

    public Optional<Note> getNoteById(int id) {
        return noteRepository.findById(id);
    }

    public boolean doesNoteExist(int noteID){
       return noteRepository.existsById(noteID);
    }

    public boolean doesAnyNoteExist(){
        return !(noteRepository.count() ==0);
    }
}
