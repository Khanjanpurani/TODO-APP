package org.todo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.todo.pojos.Note;
import org.todo.repository.NoteRepository;

import java.util.Optional;

//TODO ADD LOGS AND EXCEPTION HANDLING

@RestController
public class NotesController {

    @Autowired
     NoteRepository noteRepository;
//    private List<Note> notes;
//    private int noteID=0;


    //TODO USE PROPERTY EDITOR TO PREPROCESS NOTES AND CONVERT THE NOTE URL ENCODED


    @RequestMapping(value = "/add",method = {RequestMethod.POST,RequestMethod.GET})
    ResponseEntity<String> addNotes(@RequestParam (name = "title") String title,@RequestParam(value = "content" ) String content,@RequestParam(value = "emoji",required = false) String emoji ){
        //notes.add(new Note(title,content,emoji));
        noteRepository.save(new Note(title,content,emoji));
        return ResponseEntity.status(HttpStatus.OK).body("note added successfully");
       // return ResponseEntity.ok("note added successfully");
    }

    @RequestMapping(value = "/remove",method = {RequestMethod.POST,RequestMethod.GET})
    ResponseEntity<String> removeNotes(@RequestParam(value = "id") int noteID){


        if(noteRepository.existsById(noteID)){
            noteRepository.deleteById(noteID);
            return ResponseEntity.status(HttpStatus.OK).body("note removed successfully");
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("note not present");
        }
//        boolean removed = notes.removeIf(note -> note.getId() == noteID);
//        if(removed){
//            return ResponseEntity.status(HttpStatus.OK).body("note removed successfully");
//            // return ResponseEntity.ok("note removed successfully");
//        }
//
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("note not present");
    }

    @RequestMapping(value = "/view")
    ResponseEntity<?> showNotes(){

        if(!(noteRepository.count() == 0)){
            return ResponseEntity.status(HttpStatus.OK).body(noteRepository.findAll());
        }else {
            return ResponseEntity.status(HttpStatus.OK).body("NO notes Present");
        }
//        if(notes.isEmpty())
//        {
//            return ResponseEntity.status(HttpStatus.OK).body("NO notes Present");
//        }
//        return ResponseEntity.status(HttpStatus.OK).body(notes);
        //return ResponseEntity.ok(notes);
    }

    @RequestMapping(value = "/edit",method = {RequestMethod.GET,RequestMethod.POST})
    ResponseEntity<String> editNote(@RequestParam(value = "id") int noteID ,@RequestParam (name = "title",required = false) String title,@RequestParam(value = "content" ,required = false ) String content,@RequestParam(value = "emoji", required = false) String emoji) {

       Optional<Note> note = noteRepository.findById(noteID);
       if(note.isEmpty()){
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No such note found");
       }else {
           Note toUpdateNote =  note.get();
           if(title != null) toUpdateNote.setTitle(title);
           if(content != null)  toUpdateNote.setContent(content);
           if(emoji != null) toUpdateNote.setEmoji(emoji);
           noteRepository.save(toUpdateNote);
           return ResponseEntity.status(HttpStatus.OK).body("Note edited successfully");


       }
//        for (Note toEditNote : notes) {
//            if (toEditNote.getId() == noteID) {
//                if(content != null) toEditNote.setContent(content);
//                if(title!= null) toEditNote.setTitle(title);
//                if(emoji != null) toEditNote.setEmoji(emoji);
//                return ResponseEntity.status(HttpStatus.OK).body("Note edited successfully");
//            }
//        }
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No such note found");

    }

}
