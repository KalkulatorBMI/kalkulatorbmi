package com.example.adrian.appfit;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static java.util.Calendar.getInstance;

public class DodajPosilekActivity extends AppCompatActivity {
    DBAdapter db = new DBAdapter(this);
    TextView howTextView;
    int sizeOfArray;
    double calories = 0.0;
    double protein = 0.0; //ILE ZJEDLISMY W DANYM DNIU G DANEGO MAKROSKLADNIKA
    double carb = 0.0;
    double fat = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dodaj_posilek);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //UZYSKANIE DZISIEJSZEJ DATY
        Calendar c = Calendar.getInstance();
        SimpleDateFormat _clockDateFormat = new SimpleDateFormat("dd.MM.yy");

        howTextView = (TextView) findViewById(R.id.textView2);
        //AKTUALIZACJA NA STARCIE, POBRANIE WSZYSTKICH POSILKOW LICZENIE KALORII
        db.open();
        sizeOfArray = db.getMeals(_clockDateFormat.format(c.getTime()).toString()).size();

        TextView bTextView = (TextView) findViewById(R.id.textView19);
        TextView wTextView = (TextView) findViewById(R.id.textViewWegle);
        TextView fTextView = (TextView) findViewById(R.id.textView22);

        if(sizeOfArray==0) {
            bTextView.setText("Zjedz coś!");
            wTextView.setText("Zjedz coś!");
            fTextView.setText("Zjedz coś!");
        }
        else
        {
            calories = 0;
            for (int i = 0; i < sizeOfArray; i++) {
                MealClass mealClass = db.getMeals(_clockDateFormat.format(c.getTime()).toString()).get(i);
                calories += (double)Integer.valueOf(mealClass.getAmount())/100.0  *((double) Integer.valueOf(db.getEntry(mealClass.getName()).getFat())
                        * 9.0 + (double) Integer.valueOf(db.getEntry(mealClass.getName()).getCarbo()) * 4.0 + (double) Integer.valueOf(db.getEntry(mealClass.getName()).getProtein())
                        *4.0);
                protein += Integer.valueOf(db.getEntry(mealClass.getName()).getProtein());
                carb += Integer.valueOf(db.getEntry(mealClass.getName()).getCarbo());
                fat +=  protein += Integer.valueOf(db.getEntry(mealClass.getName()).getFat());
            }

            howTextView.setText("Co dziś jadłeś?");

            //WYSWIETLANIE INFORMACJI NA PROGRESSBARACH
            ProgressBar bProgressBar = (ProgressBar) findViewById(R.id.progressBar);
            ProgressBar wProgressBar = (ProgressBar) findViewById(R.id.progressBar2);
            ProgressBar tProgressBar = (ProgressBar) findViewById(R.id.progressBar3);
            bProgressBar.setScaleY(2f);
            wProgressBar.setScaleY(2f);
            tProgressBar.setScaleY(2f);

            SharedPreferences shared = getSharedPreferences("PREFS", MODE_PRIVATE);

            bProgressBar.setMax((int)shared.getFloat("zapotrzBialko", 0f));
            wProgressBar.setMax((int)shared.getFloat("zapotrzWegle", 0f));
            tProgressBar.setMax((int)shared.getFloat("zapotrzTluszcz", 0f));

            bProgressBar.setProgress((int)protein);
            wProgressBar.setProgress((int)carb);
            tProgressBar.setProgress((int)fat);

            if((int)shared.getFloat("zapotrzBialko", 0f) >= protein)
                bTextView.setText("Do zjedzenia dziś: " + ((int)shared.getFloat("zapotrzBialko", 0f) - protein) +" g.");
            else
                bTextView.setText("Ogranicz dziś białko.");
            if((int)shared.getFloat("zapotrzWegle", 0f) >= carb)
                wTextView.setText("Do zjedzenia dziś: " + ((int)shared.getFloat("zapotrzWegle", 0f)- carb) +" g.");
            else
                wTextView.setText("Ogranicz dziś węgle.");
            if((int)shared.getFloat("zapotrzTluszcz", 0f) >= fat)
                fTextView.setText("Ddo zjedzenia dziś: " + ((int)shared.getFloat("zapotrzTluszcz", 0f) - fat)  +" g.");
            else
                fTextView.setText("Ogranicz dziś tłuszcze.");

            //WYSWIETLANIE LISTY ZJEDZONYCH DZIS PRODUKTOW
            final ArrayList<String> LISTA_POSILKI = new ArrayList<>();
            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(DodajPosilekActivity.this, android.R.layout.simple_list_item_1, LISTA_POSILKI);

            List<MealClass> meals = db.getMeals(_clockDateFormat.format(c.getTime()).toString());

            for(MealClass meal : meals) {
                LISTA_POSILKI.add(meal.getName() + " " + meal.getAmount() + "g.");
            }

            ListView listView = (ListView) findViewById(R.id.listView2);
            listView.setAdapter(adapter);

            db.close();



            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    AlertDialog.Builder alert = new AlertDialog.Builder(DodajPosilekActivity.this);
                    alert.setTitle("Chcesz usunąć posiłek?");

                    alert.setPositiveButton("Tak", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {


                        }
                    });
                    alert.setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            //NIE ROBISZ NIC

                        }
                    });
                    alert.show();
                }
             });



        }
    }



}
