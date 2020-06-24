package com.example.third.Class;


public class NoteListClass {

    public String note_date;

    public String note_title;

    public String note_content;

    public String note_img;


    public NoteListClass(String note_date, String note_title, String note_content,  String note_img) {
        this.note_date = note_date;
        this.note_title = note_title;
        this.note_content = note_content;
        this.note_img = note_img;
    }


    public String getNote_title() {
        return note_title;
    }
}
