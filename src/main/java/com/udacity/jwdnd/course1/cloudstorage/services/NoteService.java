package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class NoteService {
    private final NoteMapper noteMapper;
    
    public int deleteFile(Note note){
        return noteMapper.deleteNote(note);
    }

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public int addNote(Note note) {
        return noteMapper.insert(note);
    }
    
    public Note getFile(Integer noteId) {
        return noteMapper.getNote(noteId);
    }

    public List<String> getFileNames() {
        return noteMapper.getNotesTitles();
    }
    
    public List<Note> getFiles(){
        return noteMapper.getNotes();
    }
}
