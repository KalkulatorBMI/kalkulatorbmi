package com.example.adrian.appfit;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendPosition;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.util.ArrayList;

public class Podsumowanie extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_podsumowanie);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        SharedPreferences shared = getSharedPreferences("PREFS", MODE_PRIVATE);
        TextView plecTv = (TextView) findViewById(R.id.textView14);
        TextView wiekTv = (TextView) findViewById(R.id.textView15);
        TextView typbTv = (TextView) findViewById(R.id.textView16);
        TextView aktywTv = (TextView) findViewById(R.id.textView17);
        TextView celTv = (TextView) findViewById(R.id.textView18);

        plecTv.setText(shared.getString("plec", ""));
        wiekTv.setText(""+shared.getInt("wiek", 0));
        typbTv.setText(shared.getString("typBudowy", ""));
        aktywTv.setText(shared.getString("typAktywnosci", ""));
        celTv.setText(shared.getString("cel", ""));

        //LICZENIE ZAPOTRZEBOWANIA KALORYCZNEGO DLA OKRESLONYCH PARAMETROW
        float bmr_plec_dod;
        float bmr;
        float neat;
        float tea;
        float tdee;

        if(plecTv.getText().toString().equals("kobieta"))
            bmr_plec_dod = -161f;
        else
            bmr_plec_dod = 5f;

        bmr = (9.99f * shared.getFloat("waga", 0)) + (6.25f * shared.getFloat("wzrost", 0)) + bmr_plec_dod;

        if(typbTv.getText().toString().equals("ektomorfik"))
            neat = 800f;
        else if(typbTv.getText().toString().equals("mezomorfik"))
            neat = 450f;
        else
            neat = 300f;

        if(aktywTv.getText().equals("duża"))
            tea = (60f * 8f * 3f) / 7f; //TRZY TRENINGI PO 60 MINUT W CIAGU TYGODNIA
        else if(aktywTv.getText().equals("średnia"))  //DWA TRENINGI PO 30-45 MIN W CIAGU TYGODNIA
            tea = (40f * 8f * 3f) / 7f;
        else                                   //JEDEN TRENING  W CIAGU TYGODNIA, LUB BRAK TRENINGU
            tea = (20f * 8f * 3f) / 7f;

        tdee = bmr + neat + tea;
        tdee = 1.1f * tdee;          //10% EFEKT TERMICZNY JEDZENIA

        if(celTv.getText().toString().equals("redukcja"))       //DODATKOWE LUB ODEJMOWANE KALORIE
            tdee -= 400.0;
        else if(celTv.getText().toString().equals("masa"))
            tdee += 400.0;


        //LCZENIE GRAMOW MAKRO
        float bialko = (0.2f * tdee) /4f;
        float tluszcze = (0.25f * tdee)/9f;
        float wegle = (0.55f * tdee)/4f;

        SharedPreferences.Editor editor = getSharedPreferences("PREFS", MODE_PRIVATE).edit();
        editor.putFloat("zapotrzBialko", bialko);
        editor.putFloat("zapotrzTluszcz", tluszcze);
        editor.putFloat("zapotrzWegle", wegle);
        editor.putBoolean("flagFood", true); //FLAGA - MAMY OBLICZONE ZAPOTRZBOWANIE
        editor.commit();


        //"UDOSTEPNIANIE" DANYCH O GRAMACH DO INNYCH AKTYWNOSCI



        //TWORZENIE WYKRESU KOLOWEGO
        PieChart pieChart = (PieChart) findViewById(R.id.chart);
        // creating data values
        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry(bialko, 0));
        entries.add(new Entry(tluszcze, 1));
        entries.add(new Entry(wegle, 2));

        PieDataSet dataset = new PieDataSet(entries, "");

        ArrayList<String> labels = new ArrayList<String>();
        labels.add("g. Białko");
        labels.add("g. Tłuszcze");
        labels.add("g. Węgle");

        PieData data = new PieData(labels, dataset); // initialize Piedata
        pieChart.setData(data);
        dataset.setColors(ColorTemplate.COLORFUL_COLORS);
        pieChart.animateY(5000);
        pieChart.getLegend().setEnabled(false);
        pieChart.setDescription("");  // set the description
    }

    public void backMenu(View view){
        Intent intent = new Intent(Podsumowanie.this, MainActivity.class);
        this.startActivity(intent);
    }
}
