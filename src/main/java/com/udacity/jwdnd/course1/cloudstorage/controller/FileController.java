package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.http.HttpHeaders;
import java.sql.Blob;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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

    private final CredentialService credentialService;
    private final UserService userService;

    public FileController(UserService userService, CredentialService credentialService) {
        this.userService = userService;
        this.credentialService = credentialService;
    }

    @GetMapping("/viewFile")
    public void viewFile(File file, Authentication authentication, HttpServletResponse response) throws FileNotFoundException, IOException {
        User user = userService.getUser(authentication.getPrincipal().toString());
        file.setUserId(user.getUserId());
        File archivoRecuperado = fileService.getFile(file.getFileId());

        // gets file name and file blob data
        String fileName = archivoRecuperado.getFileName();
        byte[] fileData = archivoRecuperado.getFileData();
        FileOutputStream fos = new FileOutputStream(new java.io.File(fileName));
        fos.write(fileData);
        fos.close();
        try {
            java.io.File fileToDownload = new java.io.File(fileName);
            InputStream inputStream = new FileInputStream(fileToDownload);
            response.setContentType("application/force-download");
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
            IOUtils.copy(inputStream, response.getOutputStream());
            response.flushBuffer();
            inputStream.close();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    @GetMapping("/deleteFile")
    public String eliminar(File file, Model model) {
        String fileMessageError = null;
        if (file != null) {
            try {
                int borrador = fileService.deleteFile(file);
            } catch (Exception e) {
                fileMessageError = "Error to delete file: " + e.getMessage();
            }
        } else {
            fileMessageError = "The file not exist ";
        }
        if (fileMessageError == null) {
            model.addAttribute("fileUploadSuccess", true);
        } else {
            model.addAttribute("fileMessageError", fileMessageError);
        }
        return "redirect:/home";
    }

    @PostMapping("/file-upload")
    public String addFile(@RequestParam("fileUpload") MultipartFile fileUpload, Note note, Credential credential, Authentication authentication, Model model) {
        String fileMessageError = null;
        User user = userService.getUser(authentication.getPrincipal().toString());

        if (!fileUpload.getOriginalFilename().equals("")) {
            try {
                InputStream fis = fileUpload.getInputStream();
                File newFile = new File(null, fileUpload.getOriginalFilename(), fileUpload.getContentType(), Long.toString(fileUpload.getSize()), user.getUserId(), fis.readAllBytes());
                if (fileService.existFileName(user.getUserId(), fileUpload.getOriginalFilename())) {
                    fileMessageError = "The FileName already exists.";
                } else {
                    try {
                        int rowsAdded = fileService.addFile(newFile);
                    } catch (Exception e) {
                        return "redirect:/uplosdStatus";
                    }

                }
            } catch (Exception e) {
                fileMessageError = "Error to upload file: " + e.getMessage();
            }

        } else {
            fileMessageError = "Please, select a file to download";
        }

        List<Note> notes = noteService.getNotes(user.getUserId());
        model.addAttribute("notes", notes);

        List<Credential> allCredentials = credentialsService.getAllCredentials();
        model.addAttribute("Credentials", allCredentials);

        List<File> files = fileService.getFiles(user.getUserId());
        model.addAttribute("files", files);

        if (fileMessageError == null) {
            model.addAttribute("fileUploadSuccess", true);
        } else {
            model.addAttribute("fileMessageError", fileMessageError);
        }

        return "home";
    }
}
