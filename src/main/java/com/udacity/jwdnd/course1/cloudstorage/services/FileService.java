package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class FileService {

    private final FileMapper fileMapper;

    public FileService(FileMapper fileMaper) {
        this.fileMapper = fileMaper;
    }

    public int deleteFile(File file) {
        return fileMapper.deleteFile(file);
    }

    public int addFile(File file) {
        return fileMapper.insert(file);
    }

    public File getFile(Integer fileId) {
        return fileMapper.getFile(fileId);
    }

    public List<String> getFileNames(Integer userId) {
        return fileMapper.getFileNames(userId);
    }

    public List<File> getFiles(Integer userId) {
        return fileMapper.getFiles(userId);
    }
    
    public boolean existFileName(Integer userId, String newName){
        List <String> names = getFileNames(userId);
        return names.stream().anyMatch(name -> (name.equals(newName)));
    }
}
