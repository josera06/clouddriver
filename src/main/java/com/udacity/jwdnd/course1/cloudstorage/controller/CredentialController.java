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
    public String guardar(@Valid Credential credential, Authentication authentication, Model model) {
        String fileMessageError = null;
        User user = userService.getUser(authentication.getPrincipal().toString());
        credential.setUserId(user.getUserId());

        if (user != null) {
            if (credential.getCredentialId() == null) {
                try {
                    credentialService.addCredential(credential);
                } catch (Exception e) {
                    fileMessageError = "Error to save credential: " + e.getMessage();
                }
            } else {
                try {
                    credentialService.updateCredential(credential);
                } catch (Exception e) {
                    fileMessageError = "Error to edit credential: " + e.getMessage();
                }
            }
        }

        List<Credential> credentials = credentialService.getAllCredentialsByUser(user.getUserId());
        model.addAttribute("credentials", credentials);
        if (fileMessageError == null) {
            model.addAttribute("fileUploadSuccess", true);
        } else {
            model.addAttribute("fileMessageError", fileMessageError);
        }
        return "redirect:/home";
    }

    @GetMapping("/deleteCredential")
    public String eliminar(Credential credential, Model model) {
        String fileMessageError = null;
        try {
            int credential_borrados = credentialService.deleteCredentials(credential);
        } catch (Exception e) {
            fileMessageError = "Error to delete credential: " + e.getMessage();
        }
        if (fileMessageError == null) {
            model.addAttribute("fileUploadSuccess", true);
        } else {
            model.addAttribute("fileMessageError", fileMessageError);
        }
        return "redirect:/home";
    }
}
