package com.example.adrian.appfit;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class KalkulatorActivity extends AppCompatActivity {
    double bmi;
    double waga;
    double wzrost;
    Button button1;
    Button button2;
    TextView txtView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kalkulator);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        button1 = (Button) findViewById(R.id.button5);
        button2 = (Button) findViewById(R.id.button6);
        txtView = (TextView) findViewById(R.id.textView28);

        SharedPreferences shared = getSharedPreferences("PREFS", MODE_PRIVATE);

        //SPRAWDZENIE CZY BMI I PLAN DZIALANIA ZOSTALY OKRESLONE
        if(shared.getBoolean("flagFood", false) == true) {
            txtView.setText("Twoje BMI oraz plan działania są już ustalone. Jeśli chcesz uaktualnić dane," +
                    " jeszcze raz przejdź przez ten proces.");
        }
    }

    public void podajWage(View view){
        //PIERWSZY ALERT Z PODANIEM WAGI
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Podaj wagę");
        alert.setMessage("Wagę należy podać w kilogramach.");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        alert.setView(input);

        alert.setPositiveButton("Akceptuj", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                SharedPreferences.Editor editor = getSharedPreferences("PREFS", MODE_PRIVATE).edit();
                try {
                    waga = Double.parseDouble(input.getText().toString());
                    editor.putFloat("waga", (float)waga);

                    button1.setText(waga + " kg");
                }
                catch(NumberFormatException e) {
                    //DOUBLE MA NIEPRAWIDLOWA WARTOSC
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

    public void podajWzrost(View view) {
        //PIERWSZY ALERT Z PODANIEM WAGI
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Podaj wzrost");
        alert.setMessage("Wzrost należy podać w centymetrach");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        alert.setView(input);

        alert.setPositiveButton("Akceptuj", new DialogInterface.OnClickListener() {
            SharedPreferences.Editor editor = getSharedPreferences("PREFS", MODE_PRIVATE).edit();
            public void onClick(DialogInterface dialog, int whichButton) {

                try {
                    wzrost = Double.parseDouble(input.getText().toString());
                    editor.putFloat("wzrost", (float)wzrost);
                    editor.commit();

                    button2.setText(wzrost + " cm");
                } catch (NumberFormatException e) {
                    //DOUBLE MA NIEPRAWIDLOWA WARTOSC
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

    public void liczBmi(View view)
    {
        if(wzrost !=0 && waga != 0) {
            Button mButton = (Button) findViewById(R.id.button4);
            bmi = waga / ((wzrost / 100.0) * (wzrost / 100.0)); //LICZENIE BMI
            mButton.setText("" + String.format("%.2f", bmi));

            //WYSWIETLANIE ODPOWIEDNIEGO KOMUNIKATU
            TextView mTextView = (TextView) findViewById(R.id.infobmi);
            Button m2Button = (Button) findViewById(R.id.button7);
            if (bmi < 18.5)
                mTextView.setText(getResources().getString(R.string.bmiunder));
            else if (bmi < 25)
                mTextView.setText(getResources().getString(R.string.bmiok));
            else
                mTextView.setText(getResources().getString(R.string.bmiover));

            m2Button.setVisibility(View.VISIBLE);
        }
        else
        {
            Toast toast = Toast.makeText(getApplicationContext(), "Nie wszystkie dane zostały wpisane.", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void goToPlanDzialania(View view){
        Intent intent = new Intent(this, PlanDzialania.class);
        this.startActivity(intent);
    }

}
