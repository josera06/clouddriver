package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Slf4j
@Controller()
@RequestMapping("/signup")
public class SignupController {

    private final UserService userService;

    public SignupController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String signupView() {
        return "signup";
    }

    @PostMapping()
    public RedirectView signupUser(@ModelAttribute User user, RedirectAttributes attributes, Model model) {
        String message = null;

        if (!userService.isUsernameAvailable(user.getUsername())) {
            message = "The username already exists.";
        }

        if (message == null) {
            int rowsAdded = userService.createUser(user);
            List<User> users = userService.getUsers();
            for (Object user1 : users) {
                log.info("Usuarios insertados: " + user1.toString());
            }
            
            if (rowsAdded < 0) {
                message = "There was an error signing you up. Please try again.";
            }
        }

        if (message == null) {
            RedirectView redirectView = new RedirectView("/login",true);
            attributes.addFlashAttribute("signupSuccess", true);
            attributes.addFlashAttribute("message", "You successfully signed up!");
            log.info("Usuario insertado");
            //model.addAttribute("signupSuccess", true);
            return redirectView;
        }

        RedirectView redirectView = new RedirectView("/signup",true);
        attributes.addFlashAttribute("signupSuccess", true);
        attributes.addFlashAttribute("message", message);
        log.info("Error al ingresar usuario");
        return redirectView;

    }
}
