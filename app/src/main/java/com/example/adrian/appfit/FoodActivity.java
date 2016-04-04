package com.example.adrian.appfit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class FoodActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
    }

    public void goToFoodSearch(View view){
        Intent intent = new Intent(this, FoodSearchActivity.class);
        startActivity(intent);
    }
}
