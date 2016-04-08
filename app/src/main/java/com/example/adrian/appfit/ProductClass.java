package com.example.adrian.appfit;

/**
 * Created by Adrian on 07.04.2016.
 */
public class ProductClass {

    private String name;
    private String protein;
    private String fat;
    private String carbo;

    ProductClass() {
        name = "";
        protein = "";
        fat = "";
        carbo = "";

    }

    ProductClass(String name, String protein, String fat, String carbo){
        this.name = name;
        this.protein = protein;
        this.fat = fat;
        this.carbo = carbo;
    }

    public String getCarbo() {
        return carbo;
    }

    public String getFat() {
        return fat;
    }

    public String getProtein() {
        return protein;
    }

    public String getName(){
        return name;
    }
}
