package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class FileService {

    private final FileMapper fileMaper;

    public FileService(FileMapper fileMaper) {
        this.fileMaper = fileMaper;
    }

    public int addFile(File file) {
        return fileMaper.insert(file);
    }
    
    public File getFile(Integer fileId) {
        return fileMaper.getFile(fileId);
    }

    public List<String> getFileNames() {
        return fileMaper.getFileNames();
    }
    
    public List<File> getFiles(){
        return fileMaper.getFiles();
    }
}
