package com.example.adrian.appfit;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.InputType;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class PlanDzialania extends AppCompatActivity {
    String gender = "";
    Integer wiek = 0;
    String typB = "";
    String aktyw = "";
    String cel = "";
    Button nextButton;
    ImageView imageView1;
    ImageView imageView2;
    ImageView imageView3;
    ImageView imageView4;
    ImageView imageView5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_dzialania);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        imageView1 = (ImageView) findViewById(R.id.imageView2);
        imageView2 = (ImageView) findViewById(R.id.imageView3);
        imageView3 = (ImageView) findViewById(R.id.imageView4);
        imageView4 = (ImageView) findViewById(R.id.imageView5);
        imageView5 = (ImageView) findViewById(R.id.imageView6);

        imageView1.setVisibility(View.GONE);
        imageView2.setVisibility(View.GONE);
        imageView3.setVisibility(View.GONE);
        imageView4.setVisibility(View.GONE);
        imageView5.setVisibility(View.GONE);
    }

    public void podajPlec(View view) {
        //PIERWSZY ALERT Z PODANIEM PLCI
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Podaj płeć:");

        final RadioButton maleButton, femaleButton;
        final RadioGroup plec = new RadioGroup(this);
        femaleButton = new RadioButton(this);
        femaleButton.setText("kobieta");
        plec.addView(femaleButton);
        maleButton = new RadioButton(this);
        maleButton.setText("mężczyzna");
        plec.addView(maleButton);
        alert.setView(plec);

        alert.setPositiveButton("Akceptuj", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                SharedPreferences prefs = getSharedPreferences("PREFS", MODE_PRIVATE);
                if (plec.getCheckedRadioButtonId() == -1)
                {
                    Toast.makeText(getApplicationContext(), "Nie wybrałeś płci.", Toast.LENGTH_SHORT).show();
                    gender = "";
                }
                else
                {
                    if(femaleButton.isChecked())
                      gender = "kobieta";
                    else
                        gender = "mężczyzna";

                    imageView1.setVisibility(View.VISIBLE); //CHECKED
                }
                //DO SHAREDPREFERENCE
                SharedPreferences.Editor editor = getSharedPreferences("PREFS", MODE_PRIVATE).edit();
                editor.putString("plec", gender);
                editor.commit();

                //SPRAWDZENIE CZY MOZNA WYSWIETLIC
                if(gender.length()!=0 && wiek != 0 && typB.length()!=0 && aktyw.length()!=0 && cel.length()!=0){
                    nextButton = (Button) findViewById(R.id.button12);
                    nextButton.setVisibility(View.VISIBLE);
                }
            }
        });

        alert.setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //NIE ROBISZ NIC
            }
        });
        alert.show();
    }

    public void podajWiek(View view) {
        //DRUGI ALERT Z PODANIEM WIEKU
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Podaj wiek:");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        alert.setView(input);

        alert.setPositiveButton("Akceptuj", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                SharedPreferences.Editor editor = getSharedPreferences("PREFS", MODE_PRIVATE).edit();
                try {
                    wiek = Integer.parseInt(input.getText().toString());
                    editor.putInt("wiek", wiek);
                    editor.commit();

                    if(wiek==0)
                        Toast.makeText(getApplicationContext(), "Źle podany wiek.", Toast.LENGTH_SHORT).show();
                    else
                        imageView2.setVisibility(View.VISIBLE); //CHECKED
                } catch (NumberFormatException e) {
                    //INT MA NIEPRAWIDLOWA WARTOSC
                }
                //SPRAWDZENIE CZY MOZNA WYSWIETLIC
                if(gender.length()!=0 && wiek != 0 && typB.length()!=0 && aktyw.length()!=0 && cel.length()!=0){
                    nextButton = (Button) findViewById(R.id.button12);
                    nextButton.setVisibility(View.VISIBLE);
                }
            }
        });

        alert.setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //NIE ROBISZ NIC

            }
        });
        alert.show();
    }

    public void podajTyp(View view) {
        //TRZECI ALERT Z PODANIEM TYPU BUDOWY
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Wybierz typ budowy:");
        alert.setMessage(Html.fromHtml("<b>Ektomorfik</b> - drobna budowa, mały obwód kości, szybki metabolizm. <br/>" +
                "<b>Mezomorfik</b> - solidna budowa, duża ilość mięśni, mała ilość tkanki tłuszczowej. <br/>" +
                "<b>Endomorfik</b> - masywna budowa ciała, grube kości, tendencja do tycia."));

        final RadioButton ectoButton, mesoButton, endoButton;
        final RadioGroup typBudowy = new RadioGroup(this);
        ectoButton = new RadioButton(this);
        ectoButton.setText("ektomorfik");
        typBudowy.addView(ectoButton);
        mesoButton = new RadioButton(this);
        mesoButton.setText("mezomorfik");
        typBudowy.addView(mesoButton);
        endoButton = new RadioButton(this);
        endoButton.setText("endomorfik");
        typBudowy.addView(endoButton);
        alert.setView(typBudowy);

        alert.setPositiveButton("Akceptuj", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                SharedPreferences prefs = getSharedPreferences("PREFS", MODE_PRIVATE);
                if (typBudowy.getCheckedRadioButtonId() == -1)
                {
                    Toast.makeText(getApplicationContext(), "Nie wybrałeś typu budowy.", Toast.LENGTH_SHORT).show();
                    typB = "";
                }
                else
                {
                    if(ectoButton.isChecked())
                        typB = "ektomorfik";
                    else if(mesoButton.isChecked())
                        typB = "mezomorfik";
                    else
                        typB = "endomorfik";

                    imageView3.setVisibility(View.VISIBLE); //CHECKED
                }
                //DO SHAREDPREFERENCE
                SharedPreferences.Editor editor = getSharedPreferences("PREFS", MODE_PRIVATE).edit();
                editor.putString("typBudowy", typB);
                editor.commit();

                //SPRAWDZENIE CZY MOZNA WYSWIETLIC
                if(gender.length()!=0 && wiek != 0 && typB.length()!=0 && aktyw.length()!=0 && cel.length()!=0){
                    nextButton = (Button) findViewById(R.id.button12);
                    nextButton.setVisibility(View.VISIBLE);
                }
            }
        });

        alert.setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //NIE ROBISZ NIC

            }
        });
        alert.show();
    }

    public void podajAktywnosc(View view) {
        //CZWARTY ALERT PODANIE AKTYWNOSCI
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Wybierz intensywność aktywności:");
        alert.setMessage(Html.fromHtml("<b>Mała aktywność</b> - brak treningów w ciągu tygodnia.<br/> " +
                "<b>Średnia aktywność</b> - 2 treningi 30 lub 45 minutowe w ciągu tygodnia. <br/>" +
                "<b>Duża aktywność</b> - przynajmniej 3 treningi 60 minutowe w ciągu tygodnia. <br/>"));

        final RadioButton mButton, sButton, dButton;
        final RadioGroup aktywnosc = new RadioGroup(this);
        mButton = new RadioButton(this);
        mButton.setText("Mała aktywność");
        aktywnosc.addView(mButton);
        sButton = new RadioButton(this);
        sButton.setText("Średnia aktywność");
        aktywnosc.addView(sButton);
        dButton = new RadioButton(this);
        dButton.setText("Duża aktywność");
        aktywnosc.addView(dButton);
        alert.setView(aktywnosc);

        alert.setPositiveButton("Akceptuj", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                SharedPreferences prefs = getSharedPreferences("PREFS", MODE_PRIVATE);
                if (aktywnosc.getCheckedRadioButtonId() == -1)
                {
                    Toast.makeText(getApplicationContext(), "Nie wybrałeś aktywności w ciągu tygodnia.", Toast.LENGTH_SHORT).show();
                    aktyw = "";
                }
                else
                {
                    imageView4.setVisibility(View.VISIBLE); //CHECKED

                    if(mButton.isChecked())
                        aktyw = "mała";
                    else if(sButton.isChecked())
                        aktyw = "średnia";
                    else
                        aktyw = "duża";
                }
                //DO SHAREDPREFERENCE
                SharedPreferences.Editor editor = getSharedPreferences("PREFS", MODE_PRIVATE).edit();
                editor.putString("typAktywnosci", aktyw);
                editor.commit();

                //SPRAWDZENIE CZY MOZNA WYSWIETLIC
                if(gender.length()!=0 && wiek != 0 && typB.length()!=0  && aktyw.length()!=0 && cel.length()!=0){
                    nextButton = (Button) findViewById(R.id.button12);
                    nextButton.setVisibility(View.VISIBLE);
                }
            }
        });

        alert.setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //NIE ROBISZ NIC
            }
        });
        alert.show();
    }

    public void podajCel(View view) {
        //PIATY ALERT PODANIE CELU
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Wybierz swój cel:");

        final RadioButton redButton, masButton, norButton;
        final RadioGroup cele = new RadioGroup(this);
        redButton = new RadioButton(this);
        redButton.setText("Redukcja wagi");
        cele.addView(redButton);
        norButton = new RadioButton(this);
        norButton.setText("Utrzymanie wagi");
        cele.addView(norButton);
        masButton = new RadioButton(this);
        masButton.setText("Zwiększenie wagi");
        cele.addView(masButton);
        alert.setView(cele);
        nextButton = (Button) findViewById(R.id.button12);
        alert.setPositiveButton("Akceptuj", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int whichButton) {
                SharedPreferences prefs = getSharedPreferences("PREFS", MODE_PRIVATE);
                if (cele.getCheckedRadioButtonId() == -1)
                {
                    Toast.makeText(getApplicationContext(), "Nie wybrałeś celu.", Toast.LENGTH_SHORT).show();
                    cel = "";
                }
                else
                {
                    if(redButton.isChecked())
                        cel = "redukcja";
                    else if(norButton.isChecked())
                        cel = "utrzymanie";
                    else
                        cel = "masa";

                    imageView5.setVisibility(View.VISIBLE); //CHECKED
                }
                //DO SHAREDPREFERENCE
                SharedPreferences.Editor editor = getSharedPreferences("PREFS", MODE_PRIVATE).edit();
                editor.putString("cel", cel);
                editor.commit();

                //SPRAWDZENIE CZY MOZNA WYSWIETLIC
                if(gender.length()!=0 && wiek != 0 && typB.length()!=0  && aktyw.length()!=0 && cel.length()!=0){
                    nextButton = (Button) findViewById(R.id.button12);
                    nextButton.setVisibility(View.VISIBLE);
                }
            }
        });

        alert.setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //NIE ROBISZ NIC
            }
        });
        alert.show();
    }

    public void goToPodsumowanie(View view) {
        Intent intent = new Intent(this, Podsumowanie.class);
        this.startActivity(intent);
    }

    public void backMenu(View view){
        Intent intent = new Intent(PlanDzialania.this, MainActivity.class);
        this.startActivity(intent);
    }
}
