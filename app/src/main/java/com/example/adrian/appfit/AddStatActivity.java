package com.example.adrian.appfit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddStatActivity extends AppCompatActivity {
    DBAdapter db = new DBAdapter(this);
    EditText mEditText;
    Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stat);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    //ZAKTUALIZUJ SWOJA OBECNA WAGE
    public void aktWaga(View view){
        float waga;
        String w;
        mEditText = (EditText) findViewById(R.id.editText7);
        mButton = (Button) findViewById(R.id.button17);
        Calendar c = Calendar.getInstance();
        SimpleDateFormat _clockDateFormat = new SimpleDateFormat("dd.MM.yy");

        try {
            db.open();
           w = mEditText.getText().toString();
            if(w.length()==0)
            {
                Toast toast = Toast.makeText(getApplicationContext(), "Waga nie została wpisana.", Toast.LENGTH_SHORT);
                toast.show();
            }
            else {
                waga = Float.parseFloat(w);
                db.insertStat(_clockDateFormat.format(c.getTime()).toString(), waga);
                mButton.setText("WAGA ZAKTUALIZOWANA");
                mButton.setClickable(false);
                db.close();
            }
        } catch (NumberFormatException e) {
            //TO NIE JEST FLOAT
        }
    }
}
