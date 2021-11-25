
package com.udacity.jwdnd.course1.cloudstorage.model;


public class Note {
    private Integer noteId;
    private String notetitle;
    private String notedescription;
    private Integer userid;

    public Note(Integer noteId, String notetitle, String notedescription, Integer userid) {
        this.noteId = noteId;
        this.notetitle = notetitle;
        this.notedescription = notedescription;
        this.userid = userid;
    }

    public Note() {
    }

    public Integer getNoteId() {
        return noteId;
    }

    public void setNoteId(Integer noteId) {
        this.noteId = noteId;
    }

    public String getNotetitle() {
        return notetitle;
    }

    public void setNotetitle(String notetitle) {
        this.notetitle = notetitle;
    }

    public String getNotedescription() {
        return notedescription;
    }

    public void setNotedescription(String notedescriptio) {
        this.notedescription = notedescriptio;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Note{noteId=").append(noteId);
        sb.append(", notetitle=").append(notetitle);
        sb.append(", notedescription=").append(notedescription);
        sb.append(", userid=").append(userid);
        sb.append('}');
        return sb.toString();
    }
    
    
    
}
