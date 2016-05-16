package com.example.adrian.appfit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

public class JedzenieActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jedzenie);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    public void goToFoodSearch(View view){
        Intent intent = new Intent(this, JedzenieSzukajActivity.class);
        startActivity(intent);
    }

    public void goToDodajPosilek(View view){
        Intent intent = new Intent(this, DodajPosilekActivity.class);
        startActivity(intent);
    }
}
