package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Controller
@RequestMapping("/file-upload")
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping()
    public String addFile(@RequestParam("fileUpload") MultipartFile fileUpload, Model model) throws IOException {
        InputStream fis = fileUpload.getInputStream();

        File newFile = new File(null, fileUpload.getOriginalFilename(), fileUpload.getContentType(), Long.toString(fileUpload.getSize()), 1, fis.readAllBytes());

        log.info("Archivo: " + newFile.toString());

        try {
            int rowsAdded = fileService.addFile(newFile);
        } catch (Exception e) {
            model.addAttribute("errorUpload", "errorUpload");
            log.info("Error: " + e.getMessage());
        }

        List<String> archivos = fileService.getFileNames();

        for (String archivo : archivos) {
            log.info("Elemento: " + archivo);
        }

        List<File> files = fileService.getFiles();
        model.addAttribute("files", files);

        return "home";
    }
}
