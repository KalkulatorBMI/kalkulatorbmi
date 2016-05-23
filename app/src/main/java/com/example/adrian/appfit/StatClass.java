package com.example.adrian.appfit;

/**
 * Created by Adrian on 21.05.2016.
 */

//KLASA ZAWIERAJACA INFORMACJE O STATYSTYKACH
public class StatClass {
    private String data;
    private int id;
    private float weight;

    public StatClass(){
        data = "";
        id = 0;
        weight = 0f;
    }

    public StatClass(String data, float weight, int id) {
        this.data = data;
        this.weight = weight;
        this.id = id;
    }

    public float getWeight() {
        return weight;
    }

    public int getId() {
        return id;
    }

    public String getData() {
        return data;
    }
}
