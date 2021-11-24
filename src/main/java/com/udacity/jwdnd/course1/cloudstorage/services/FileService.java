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

    public int addFile(User user, File file) {

        return fileMaper.insert(new File(null,
                file.getFileName(),
                file.getContentType(),
                file.getFileSize(),
                user.getUserId(),
                file.getFileData()));
    }
    
    public File getFile(Integer fileId) {
        return fileMaper.getFile(fileId);
    }

    public List<String> getFileNames() {
        return fileMaper.getFileNames();
    }

}
