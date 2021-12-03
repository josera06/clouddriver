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
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
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
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        String fileName = StringUtils.cleanPath(fileUpload.getOriginalFilename());
        File document = new File();
        document.setFileName(fileName);
        document.setFileData(fileUpload.getBytes());
        document.setFileSize(fileUpload.getSize() + "");
        document.setContentType(fileUpload.getContentType());
        document.setUserId(user.getUserId());

        fileService.addFile(document);
        message = "The file was successfully loaded.";

        ra.addFlashAttribute("message", message);

        return "redirect:/home";

    }
}
