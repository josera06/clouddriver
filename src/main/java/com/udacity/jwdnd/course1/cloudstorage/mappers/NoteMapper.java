package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface NoteMapper {

    @Delete("DELETE FROM NOTES WHERE noteId = #{NoteId}")
    int deleteNote(Note note);

    @Select("SELECT * FROM NOTES WHERE noteId = #{noteId}")
    Note getNote(Integer noteId);

    @Select("SELECT notetitle FROM NOTES")
    List<String> getNotesTitles();

    @Select("SELECT * FROM NOTESS")
    List<Note> getNotes();


    @Insert("INSERT INTO NOTES (notetitle,notedescription,userId) VALUES(#{fileName}, #{notedescription}, #{userId}")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    int insert(Note note);
}
