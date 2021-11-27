package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class NoteService {

    private final NoteMapper noteMapper;

    public int updateNote(Note note) {
        return noteMapper.updateNote(note);
    }

    public int deleteNote(Note note) {
        return noteMapper.deleteNote(note);
    }

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public int addNote(Note note) {
        return noteMapper.insert(note);
    }

    public Note getNote(Integer noteId) {
        return noteMapper.getNote(noteId);
    }

    public List<String> getNotesNames(Integer userId) {
        return noteMapper.getNotesTitles(userId);
    }

    public List<Note> getNotes(Integer userId) {
        return noteMapper.getNotes(userId);
    }
}
