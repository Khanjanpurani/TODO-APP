package org.todo.service;


import org.springframework.stereotype.Service;
import org.todo.dto.NoteResponse;
import org.todo.exception.NoteNotFoundException;
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
        return noteRepository.findAllByOrderByPinnedDescCreatedAtDesc();
    }

    public void deleteNote(int id) {
        noteRepository.deleteById(id);
    }

    public Note getNoteById(int id) {
        return noteRepository.findById(id)
                .orElseThrow(() -> new NoteNotFoundException(id));
    }

    public boolean doesNoteExist(int noteID){
       return noteRepository.existsById(noteID);
    }

    public boolean doesAnyNoteExist(){
        return !(noteRepository.count() ==0);
    }

    public NoteResponse convertToResponse(Note note) {
        return new NoteResponse(note.getId(), note.getTitle(), note.getContent(), note.getCreatedAt(), note.getEmoji(),note.isPinned());
    }

    public List<NoteResponse> convertToResponseList(List<Note> notes){
        return notes.stream().map(this::convertToResponse).toList();
    }
    public void togglePin(int id) {
        Note note = getNoteById(id);
        note.setPinned(!note.isPinned());
        noteRepository.save(note);
    }

    public List<Note> searchNotes(String keyword) {

        return noteRepository
                .findByTitleContainingIgnoreCaseOrContentContainingIgnoreCaseOrEmojiContaining(
                        keyword,
                        keyword,
                        keyword
                );
    }
}
