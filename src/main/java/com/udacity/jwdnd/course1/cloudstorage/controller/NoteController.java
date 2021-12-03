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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String guardar(@Valid Note note, Authentication authentication, RedirectAttributes ra) {
        String message = "";
        User user = userService.getUser(authentication.getPrincipal().toString());
        note.setUserid(user.getUserId());

        if (user != null) {
            if (note.getNoteId() == null) {
                try {
                    noteService.addNote(note);
                    message = "Note saved successfully!";
                } catch (Exception e) {
                    message = "Error to save note: " + e.getMessage();
                }
            } else {
                try {
                    noteService.updateNote(note);
                    message = "Note modified successfully!";
                } catch (Exception e) {
                    message = "Error to edit note: " + e.getMessage();
                }
            }
        }

        ra.addFlashAttribute("message", message);
        return "redirect:/home";
    }

    @GetMapping("/deleteNote")
    public String eliminar(Note note,  RedirectAttributes ra) {
        String message = "";
        try {
            noteService.deleteNote(note);
            message = "Note deleted successfully!";
        } catch (Exception e) {
            message = "Error to delete note: " + e.getMessage();
        }

        ra.addFlashAttribute("message", message);
        return "redirect:/home";
    }
}
