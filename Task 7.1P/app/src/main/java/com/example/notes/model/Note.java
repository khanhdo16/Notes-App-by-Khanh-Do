package com.example.notes.model;

public class Note {
    private int note_id;
    private String note_title;
    private String note_content;

    public Note(String note_title, String note_content) {
        this.note_title = note_title;
        this.note_content = note_content;
    }

    public Note(String note_title) {
        this.note_title = note_title;
    }

    public Note() {
    }

    public int getNoteId() {
        return note_id;
    }

    public void setNoteId(int note_id) {
        this.note_id = note_id;
    }

    public String getNoteTitle() {
        return note_title;
    }

    public void setNoteTitle(String note_title) {
        this.note_title = note_title;
    }

    public String getNoteContent() {
        return note_content;
    }

    public void setNoteContent(String note_content) {
        this.note_content = note_content;
    }
}
