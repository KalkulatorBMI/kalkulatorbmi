package com.example.adrian.appfit;

/**
 * Created by Adrian on 09.04.2016.
 */
//KLASA ZAWIERAJACA DANE O POSILKACH
public class MealClass {
    private String date;
    private String name;
    private String amount;
    private int id;

    public MealClass() {
        date = "";
        name = "";
        amount = "";
        id = 0;
    }

    public MealClass(String name, String date, String amount, int id){
        this.date = date;
        this.name = name;
        this.amount = amount;
        this.id = id;
    }

    public String getDate(){
        return date;
    }

    public String getName(){
        return name;
    }

    public String getAmount(){
        return amount;
    }

    public int getId() {return id;}
}
