package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import java.util.List;
import java.util.Random;
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
public class CredentialController {

    private final CredentialService credentialService;
    private final UserService userService;

    public CredentialController(UserService userService, CredentialService credentialService) {
        this.userService = userService;
        this.credentialService = credentialService;
    }

    @PostMapping("/saveCredential")
    public String guardar(@Valid Credential credential, Authentication authentication, RedirectAttributes ra) {
        String message = "";
        User user = userService.getUser(authentication.getPrincipal().toString());
        credential.setUserId(user.getUserId());

        if (user != null) {
            if (credential.getCredentialId() == null) {
                try {
                    credentialService.addCredential(credential);
                    message = "Credential saved successfully!";
                } catch (Exception e) {
                    message = "Error to save credential: " + e.getMessage();
                }
            } else {
                try {
                    credentialService.updateCredential(credential);
                    message = "Credential modified successfully!";
                } catch (Exception e) {
                    message = "Error to edit credential: " + e.getMessage();
                }
            }
        }

        ra.addFlashAttribute("message", message);
        return "redirect:/home";
    }

    @GetMapping("/deleteCredential")
    public String eliminar(Credential credential,  RedirectAttributes ra) {
        String message = "Credential deleted successfully!";;
        try {
            credentialService.deleteCredentials(credential);
            message = "Credential deleted successfully!";
        } catch (Exception e) {
            message = "Error to delete credential: " + e.getMessage();
        }

        ra.addFlashAttribute("message", message);
        return "redirect:/home";
    }
}
