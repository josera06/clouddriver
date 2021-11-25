package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private FileService fileService;

    @Autowired
    private NoteService noteService;

    @GetMapping()
    public String getHomePage(@ModelAttribute("note") Note note, BindingResult bindingResult, Model model) {
        List<File> files = fileService.getFiles();
        List<Note> notes = noteService.getNotes();
        model.addAttribute("files", files);
        model.addAttribute("notes", notes);
        log.info("Listado de archivos: " + files);
        log.info("Listado de notas: " + notes);
        //log.info("Usuario: "+ user.toString());
        return "home";
    }

}
