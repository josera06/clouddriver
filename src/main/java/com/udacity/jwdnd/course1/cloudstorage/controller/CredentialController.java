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
    public String guardar(@Valid Credential credential, Authentication authentication, Errors errores, Model model) {
        User user = userService.getUser(authentication.getPrincipal().toString());
        credential.setUserId(user.getUserId());

        if (errores.hasErrors()) {
            return "home";
        }
        try {
            if (credential.getCredentialId() == null) {
                credentialService.addCredential(credential);
                log.info("Insertantdo credential..." + credential.toString());
            } else {
                credentialService.updateCredential(credential);
                log.info("Actualizando credential..." + credential.toString());
            }
        } catch (Exception e) {
            log.info("Error al guardar Credenciales" + e.getMessage());
        }

        List<Credential> credentials = credentialService.getAllCredentialsByUser(user.getUserId());
        model.addAttribute("credentials", credentials);
        
        model.addAttribute("fileTab", false);
        model.addAttribute("noteTab", false);
        model.addAttribute("credentialTab", true);

        return "redirect:/home";
    }

    @GetMapping("/deleteCredential")
    public String eliminar(Credential credential) {
        log.info("Credential a eliminar: " + credential.toString());
        int credential_borrados = credentialService.deleteCredentials(credential);
        log.info("Registros borrados: " + credential_borrados);
        return "redirect:/home";
    }
}
