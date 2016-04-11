package com.example.adrian.appfit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static java.util.Calendar.getInstance;

public class DodajPosilekActivity extends AppCompatActivity {
    DBAdapter db = new DBAdapter(this);
    TextView mTextView;
    TextView howTextView;
    EditText productEditText;
    EditText amountEditText;
    int sizeOfArray;
    double calories = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dodaj_posilek);

        Calendar c = Calendar.getInstance();
        SimpleDateFormat _clockDateFormat = new SimpleDateFormat("dd.MM.yy");
        howTextView = (TextView) findViewById(R.id.textView2);

        //AKTUALIZACJA NA STARCIE
        db.open();

        sizeOfArray = db.getMeals(_clockDateFormat.format(c.getTime()).toString()).size();

        if(sizeOfArray==0)
            howTextView.setText("Dziś jeszcze nic nie jadłeś!");
        else
        {
            calories = 0;
            for (int i = 0; i < sizeOfArray; i++) {
                MealClass mealClass = db.getMeals(_clockDateFormat.format(c.getTime()).toString()).get(i);
                calories += (double)Integer.valueOf(mealClass.getAmount())/100.0  *((double) Integer.valueOf(db.getEntry(mealClass.getName()).getFat())
                        * 9.0 + (double) Integer.valueOf(db.getEntry(mealClass.getName()).getCarbo()) * 4.0 + (double) Integer.valueOf(db.getEntry(mealClass.getName()).getProtein())
                        *4.0);

                howTextView.setText("Ilość spożytych kalorii: " + calories);
            }

        }
        db.close();
    }

    public void dodajPosilek(View view){
        mTextView = (TextView) findViewById(R.id.textView);

        productEditText = (EditText) findViewById(R.id.editText5);
        amountEditText = (EditText) findViewById(R.id.editText6);


        db.open();

        Calendar c = Calendar.getInstance();
        SimpleDateFormat _clockDateFormat = new SimpleDateFormat("dd.MM.yy");

        if(productEditText.getText().toString().length()!=0 && amountEditText.getText().toString().length()!=0) // Sprawdzenie czy wprowadzono produkt
            if(db.getEntry(productEditText.getText().toString())!=null) //Sprawdzenie czy taki produkt jest w bazie
            {
                amountEditText.setClickable(true);
                db.insertMeal(productEditText.getText().toString(), _clockDateFormat.format(c.getTime()).toString(), amountEditText.getText().toString());
                mTextView.setText("Posiłek został dodany.");

                sizeOfArray = db.getMeals(_clockDateFormat.format(c.getTime()).toString()).size();

                calories = 0;

                    howTextView.setText(""); //CZYSZCZENIE TABLICY
                    for (int i = 0; i < sizeOfArray; i++) {
                        MealClass mealClass = db.getMeals(_clockDateFormat.format(c.getTime()).toString()).get(i);
                        calories += (double)Integer.valueOf(mealClass.getAmount())/100.0  *((double) Integer.valueOf(db.getEntry(mealClass.getName()).getFat())
                        * 9.0 + (double) Integer.valueOf(db.getEntry(mealClass.getName()).getCarbo()) * 4.0 + (double) Integer.valueOf(db.getEntry(mealClass.getName()).getProtein())
                                *4.0);

                        howTextView.setText("Ilość spożytych kalorii: " + calories);
                    }


            }
            else
                mTextView.setText("Nie ma takiego produktu w bazie. Dodaj go do bazy danych.");
        else
            mTextView.setText("Nieprawidłowo wpisane dane. Sprawdź czy podałeś nazwę produktu, oraz jego ilość.");
        db.close();
    }
}
