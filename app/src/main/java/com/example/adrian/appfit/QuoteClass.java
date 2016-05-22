package com.example.adrian.appfit;

/**
 * Created by Adrian on 22.05.2016.
 */
public class QuoteClass {
    private String title;
    private String text;
    private int id;

    public QuoteClass() {
        title = "";
        text = "";
        id = 0;
    }

    public QuoteClass(String title, String text, int id){
        this.title = title;
        this.text = text;
        this.id = id;
    }

    public String getTitle(){
        return title;
    }

    public String getText(){
        return text;
    }

    public int getId() {return id;}
}
