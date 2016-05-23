package com.example.adrian.appfit;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.Calendar;

public class UstawieniaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ustawienia);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    //USTAWIENIE POWIADOMIEN
    public void aktCzas(View view){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Co ile chcesz sprawdzać wagę?");

        final RadioButton button1, button2, button4;
        final RadioGroup okres = new RadioGroup(this);
        button1 = new RadioButton(this);
        button1.setText("co tydzień");
        okres.addView(button1);
        button2 = new RadioButton(this);
        button2.setText("co dwa tygodnie");
        okres.addView(button2);
        button4 = new RadioButton(this);
        button4.setText("co miesiąc");
        okres.addView(button4);
        alert.setView(okres);

        alert.setPositiveButton("Ustaw", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                int interval;
                SharedPreferences prefs = getSharedPreferences("PREFS", MODE_PRIVATE);
                if (okres.getCheckedRadioButtonId() == -1)
                {
                    Toast.makeText(getApplicationContext(), "Nie wybrałeś żadnej opcji. Powiadomienie ustawione na 1 tydzień.", Toast.LENGTH_SHORT).show();
                     interval = 1;
                }
                else
                {
                    if(button1.isChecked())
                        interval = 1;
                    else if(button2.isChecked())
                        interval = 2;
                    else
                        interval = 4;
                }

                //USTALENIE ALARMU CO TYDZIEN, 2 TYGODNIE LUB MIESIAC ZGODNIE ZE ZMIENNA INTERVAL
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                calendar.set(Calendar.HOUR_OF_DAY, 18);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                Intent intent1 = new Intent(UstawieniaActivity.this, Reciever.class);

                PendingIntent pendingIntent = PendingIntent.getBroadcast(UstawieniaActivity.this, 0,intent1, PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager am = (AlarmManager) UstawieniaActivity.this.getSystemService(UstawieniaActivity.this.ALARM_SERVICE);
                am.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY*7*interval, pendingIntent);
            }
        });

        alert.setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //NIE ROBISZ NIC
            }
        });
        alert.show();
    }

    //WYSWIETL POWIADOMIENIE O AUTORACH
    public void oAut(View view){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Autorzy: ");
        alert.setMessage("Adrian Jankowski \nSylwester Kalinowski \nMateusz Wielgosz \nSylwia Rusinek \n\n                grupa: E3C2S1 \n                projekt PUMO 2016");
        alert.setPositiveButton("Wróć", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
             //NIE ROBISZ NIC
            }
        });
        alert.show();
    }
}

