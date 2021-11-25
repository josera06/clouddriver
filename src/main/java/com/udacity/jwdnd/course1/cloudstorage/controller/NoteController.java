package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping()
public class NoteController {

    @Autowired
    private NoteService noteService;
    
    /*@GetMapping("/deleteNote")
    
    @PostMapping("/saveNote")
    
    @GetMapping("/edit/{noteId}")*/
}
