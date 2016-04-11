package com.example.adrian.appfit;

/**
 * Created by Adrian on 09.04.2016.
 */
public class MealClass {
    private String date;
    private String name;
    private String amount;

    public MealClass() {
        date = "";
        name = "";
        amount = "";
    }

    public MealClass(String name, String date, String amount){
        this.date = date;
        this.name = name;
        this.amount = amount;
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
}
