package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
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
public class CredentialController {

    @Autowired
    private CredentialService credentialService;

    @PostMapping("/saveCredential")
    public String guardar(@Valid Credential credential, Errors errores, Model model) {

        if (errores.hasErrors()) {
            return "home";
        }

        if (credential.getCredentialId() == null) {
            credentialService.addCredential(credential);;
            log.info("Insertantdo credential..." + credential.toString());
        } else {
            credentialService.updateCredential(credential);
            log.info("Actualizando credential..." + credential.toString());
        }
        List<Credential> credentials = credentialService.getAllCredentials();
        model.addAttribute("credentials", credentials);
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
