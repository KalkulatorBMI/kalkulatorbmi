package com.example.adrian.appfit;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {



    private int REQUEST_CODE = 0;
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //USTALENIE ALARMU CO TYDZIEN
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 21);
        calendar.set(Calendar.MINUTE, 5);
        calendar.set(Calendar.SECOND, 0);
        Intent intent1 = new Intent(MainActivity.this, Reciever.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0,intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) MainActivity.this.getSystemService(MainActivity.this.ALARM_SERVICE);
        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY*7, pendingIntent);
    }

    public void goToFood(View view){
        SharedPreferences shared = getSharedPreferences("PREFS", MODE_PRIVATE);

        if(shared.getBoolean("flagFood", false) == false) {
            Toast toast = Toast.makeText(getApplicationContext(), "Najpierw określ swoje bmi oraz dalszy plan działania. Kliknij w ikonkę pod spodem.", Toast.LENGTH_SHORT);
            toast.show();
        }
        else {
            Intent intent = new Intent(MainActivity.this, JedzenieActivity.class);
            this.startActivity(intent);
        }
    }

    public void goToCalc(View view) {
        Intent intent = new Intent(this, KalkulatorActivity.class);
        this.startActivity(intent);
    }

    public void goToDziennik(View view) {
        Intent intent = new Intent(this, DziennikActivity.class);
        this.startActivity(intent);
    }

    public void goToStat(View view) {
        Intent intent = new Intent(this, StatActivity.class);
        this.startActivity(intent);
    }
}
