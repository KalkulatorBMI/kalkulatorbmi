package com.example.adrian.appfit;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TreningActivity extends AppCompatActivity {
    DBAdapter db = new DBAdapter(this);
    String nazwa;
    TextView infoEx;
    ImageView imgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trening);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        final ArrayList<String> LISTA_CWICZENIA = new ArrayList<>();
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(TreningActivity.this, android.R.layout.simple_list_item_1, LISTA_CWICZENIA);

        db.open();

        List<ExercClass> exerc = db.getExer();
        for(ExercClass exer : exerc) {
            LISTA_CWICZENIA.add(exer.getTitle());
        }
        db.close();
        ListView listView = (ListView) findViewById(R.id.listView4);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                nazwa = (String) ((TextView) view).getText();
                infoEx = (TextView) findViewById(R.id.textView26);
                imgView = (ImageView) findViewById(R.id.imageView7);

                ExercClass sExercClass;
                db.open();
                sExercClass = db.getSinExerc(nazwa);

                infoEx.setText(Html.fromHtml("<h1>" + sExercClass.getTitle() + "</h1>"
                        +sExercClass.getText()));


                int resID = getResources().getIdentifier(sExercClass.getImgsrc().toString(), "drawable", getPackageName());
                imgView.setImageResource(resID);

                db.close();
            }
        });
        listView.setAdapter(adapter);
        listView.setAdapter(adapter);

        db.close();


    }
}
