package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;

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
    public void viewFile(File file, Authentication authentication, HttpServletResponse response) throws FileNotFoundException, IOException, Exception {
        File document = fileService.getFile(file.getFileId());
        if (document == null) {
            throw new Exception("Could not find document with Id: " + file.getFileId());
        }

        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValues = "attachment; filename=" + document.getFileName();

        response.setHeader(headerKey, headerValues);

        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(document.getFileData());
        outputStream.close();
    }

    @GetMapping("/deleteFile")
    public String eliminar(File file, RedirectAttributes ra) throws Exception {
        String message = "";
        File document = fileService.getFile(file.getFileId());
        if (document == null) {
            throw new Exception("Could not find file with Id: " + file.getFileId());
        }
        int borrador = fileService.deleteFile(file);
        message = "The file was successfully deleted.";
        ra.addFlashAttribute("message", message);

        return "redirect:/home";
    }

    @PostMapping("/file-upload")
    public String addFile(@RequestParam("fileUpload") MultipartFile fileUpload, Authentication authentication, RedirectAttributes ra) throws IOException {
        User user = userService.getUser(authentication.getPrincipal().toString());
        String message = "";
        if (fileUpload.getSize() > 1000000)  // 1MB approx (actually less though)
        {
            message = "File is too big";
        } else {
            log.info("Entrando");
            String fileName = StringUtils.cleanPath(fileUpload.getOriginalFilename());
            File document = new File();
            document.setFileName(fileName);
            document.setFileData(fileUpload.getBytes());
            document.setFileSize(fileUpload.getSize() + "");
            document.setContentType(fileUpload.getContentType());
            document.setUserId(user.getUserId());
            fileService.addFile(document);
            message = "The file was successfully loaded.";
        }
        log.info("message: --------------------------------" + message);
        ra.addFlashAttribute("message", message);
        return "redirect:/home";
    }
}
