package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import java.util.List;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping()
public class NoteController {

    @Autowired
    private NoteService noteService;

    @PostMapping("/saveNote")
    public String guardar(@Valid Note note, Errors errores, Model model) {
        
        if (errores.hasErrors()) {
            return "home";
        }
        if (note.getNoteId() == null) {
            noteService.addNote(note);
            log.info("Insertantdo..." + note.toString());
        } else {
            noteService.updateNote(note);
            log.info("Actualizando..." + note.toString());
        }
        List<Note> notes = noteService.getNotes();
        model.addAttribute("notes", notes);
        return "redirect:/home";
    }

    @GetMapping("/deleteNote")
    public String eliminar(Note note) {
        log.info("Nota a eliminar: " + note.toString());
        int notas_borrados = noteService.deleteNote(note);
        log.info("Registros borrados: " + notas_borrados);
        return "redirect:/home";
    }

    /*@GetMapping("/edit/{noteId}")*/
}
