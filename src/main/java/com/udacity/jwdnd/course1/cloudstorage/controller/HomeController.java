package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {
    
    @GetMapping()
    public String getHomePage(Model model) {
        model.addAttribute("greetings", "hola");
        return "home";
    }

    @PostMapping()
    public String addMessage(Model model) {
        model.addAttribute("greetings", "hola");
        return "home";
    }


}
