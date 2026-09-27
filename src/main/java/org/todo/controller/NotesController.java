package org.todo.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.todo.dto.NoteRequest;
import org.todo.exception.NoteNotFoundException;
import org.todo.pojos.Note;
import org.todo.service.NoteService;


@RestController
public class NotesController {

//    @Autowired
//     NoteRepository noteRepository;
    @Autowired
    NoteService noteService;//This is field injection
//    private List<Note> notes;
//    private int noteID=0;


    //TODO USE PROPERTY EDITOR TO PREPROCESS NOTES AND CONVERT THE NOTE URL ENCODED and ADD LOGS

    //ADDING A NOTE
    @RequestMapping(value = "/notes", method = {RequestMethod.POST})
    ResponseEntity<String> addNotes(@Valid @RequestBody NoteRequest request) {
        noteService.addNote(request.getTitle(), request.getContent(), request.getEmoji());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("note added successfully");
    }

    //REMOVING A NOTE
    @RequestMapping(value = "/notes/{id}",method = {RequestMethod.DELETE})
    ResponseEntity<String> removeNotes(@PathVariable("id") int noteID ){
        if (!noteService.doesNoteExist(noteID)) {
            throw new NoteNotFoundException(noteID);
        }
        noteService.deleteNote(noteID);
        return ResponseEntity.status(HttpStatus.OK).body("note removed successfully");
    }

    //VIEWING ALL NOTES
    @RequestMapping(value = "/notes",method = RequestMethod.GET)
    ResponseEntity<?> showNotes(){

        if(noteService.doesAnyNoteExist()){
            return ResponseEntity.status(HttpStatus.OK).body(noteService.convertToResponseList(noteService.getAllNotes()));
        }else {
            return ResponseEntity.status(HttpStatus.OK).body("NO notes Present");
        }
    }

    //VIEWING A NOTE
    @RequestMapping(value = "/notes/{id}",method = RequestMethod.GET)
    ResponseEntity<?> showANote(@PathVariable("id") int noteID){

        Note note = noteService.getNoteById(noteID);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(noteService.convertToResponse(note));
    }

    //EDITING A NOTE
    @RequestMapping(value = "/notes/{id}",method = {RequestMethod.PUT})
    ResponseEntity<String> editNote( @PathVariable("id") int noteID,
                                     @RequestBody NoteRequest request) {

        Note toUpdateNote = noteService.getNoteById(noteID);

        if (request.getTitle() != null) toUpdateNote.setTitle(request.getTitle());
        if (request.getContent() != null) toUpdateNote.setContent(request.getContent());
        if (request.getEmoji() != null) toUpdateNote.setEmoji(request.getEmoji());
        noteService.saveUpdatedNote(toUpdateNote);
        return ResponseEntity.status(HttpStatus.OK).body("Note edited successfully");
    }



}
