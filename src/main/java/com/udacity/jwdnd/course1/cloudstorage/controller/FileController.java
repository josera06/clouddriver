package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Controller
@RequestMapping()
public class FileController {

    @Autowired
    private FileService fileService;

    @Autowired
    private NoteService noteService;

    @Autowired
    private CredentialService credentialsService;

    @GetMapping("/deleteFile")
    public String eliminar(File file) {
        log.info("Archivo a eliminar: " + file.toString());
        int borrador = fileService.deleteFile(file);
        log.info("Registros borrados: " + borrador);
        return "redirect:/home";
    }

    @PostMapping("/file-upload")
    public String addFile(@RequestParam("fileUpload") MultipartFile fileUpload, Note note, Credential credential, Model model) throws IOException {
        InputStream fis = fileUpload.getInputStream();

        File newFile = new File(null, fileUpload.getOriginalFilename(), fileUpload.getContentType(), Long.toString(fileUpload.getSize()), 1, fis.readAllBytes());

        log.info("Archivo: " + newFile.toString());

        try {
            int rowsAdded = fileService.addFile(newFile);
        } catch (Exception e) {
            model.addAttribute("errorUpload", "errorUpload");
            log.info("Error: " + e.getMessage());
        }

        List<Note> notes = noteService.getNotes();
        model.addAttribute("notes", notes);

        List<File> files = fileService.getFiles();
        model.addAttribute("files", files);

        List<Credential> allCredentials = credentialsService.getAllCredentials();
        model.addAttribute("Credentials", allCredentials);

        return "home";
    }
}
