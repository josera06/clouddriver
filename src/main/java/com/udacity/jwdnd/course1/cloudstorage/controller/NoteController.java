package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import java.util.List;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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

    private final CredentialService credentialService;
    private final UserService userService;

    public NoteController(UserService userService, CredentialService credentialService) {
        this.userService = userService;
        this.credentialService = credentialService;
    }

    @PostMapping("/saveNote")
    public String guardar(@Valid Note note, Authentication authentication, Errors errores, Model model) {
        User user = userService.getUser(authentication.getPrincipal().toString());
        note.setUserid(user.getUserId());

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
        List<Note> notes = noteService.getNotes(user.getUserId());
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
