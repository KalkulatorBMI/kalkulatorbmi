package com.example.adrian.appfit;

/**
 * Created by Adrian on 22.05.2016.
 */
public class ExercClass {
    private String imgsrc;
    private String title;
    private String text;
    private int id;

    public ExercClass() {
        imgsrc = "";
        title = "";
        text = "";
        id = 0;
    }

    public ExercClass(String imgsrc, String title, String text, int id){
        this.imgsrc = imgsrc;
        this.title = title;
        this.text = text;
        this.id = id;
    }

    public String getImgsrc(){
        return imgsrc;
    }

    public String getTitle(){
        return title;
    }

    public String getText(){
        return text;
    }

    public int getId() {return id;}
}
