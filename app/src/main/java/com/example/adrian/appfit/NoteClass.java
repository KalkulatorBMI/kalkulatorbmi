package com.example.adrian.appfit;

/**
 * Created by Adrian on 16.05.2016.
 */
public class NoteClass {
    private String date;
    private String title;
    private String text;
    private int id;

    public NoteClass() {
        date = "";
        title = "";
        text = "";
        id = 0;
    }

    public NoteClass(String date, String title, String text, int id){
        this.date = date;
        this.title = title;
        this.text = text;
        this.id = id;
    }

    public String getDate(){
        return date;
    }

    public String getTitle(){
        return title;
    }

    public String getText(){
        return text;
    }

    public int getId() {return id;}
}
