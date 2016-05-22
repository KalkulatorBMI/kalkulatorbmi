package com.example.adrian.appfit;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class SplashScreen extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 4000;
    DBAdapter db = new DBAdapter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        //WYSWIETLANIE LOSOWEGO CYTATU
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        Animation myFadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        imageView.startAnimation(myFadeInAnimation);

        TextView textView = (TextView) findViewById(R.id.textView3);
        db.open();
        Random random = new Random();
        int id_rand;
            id_rand = random.nextInt(db.getAmQuotes())+1;
        QuoteClass quoteClass = db.getRandQuo(id_rand);
        textView.setText(Html.fromHtml( "<i>       '" + quoteClass.getText() + "'<i>"
                +"<br/>             <b>" + quoteClass.getTitle() +"<b>"));
        db.close();


        Animation RightSwipe = AnimationUtils.loadAnimation(this, R.anim.slide);
        textView.startAnimation(RightSwipe);

        //UKRYWAMY TITLE BAR I PASEK NAWIGACJI
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);

    }
}
