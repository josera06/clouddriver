package com.udacity.jwdnd.course1.cloudstorage.controller;

import static com.sun.org.apache.xerces.internal.util.FeatureState.is;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
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

    @GetMapping("/viewFile")
    public void viewFile(File file, HttpServletResponse response) throws FileNotFoundException, IOException {
        File archivoRecuperado = fileService.getFile(file.getFileId());
        byte[] output = archivoRecuperado.getFileData();

        log.info("Archivo buscado: " + file.toString());
        log.info("Archivo recuperado: " + archivoRecuperado.toString());
        FileOutputStream os = null;

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
