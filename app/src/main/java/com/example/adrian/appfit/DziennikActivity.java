package com.example.adrian.appfit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DziennikActivity extends AppCompatActivity {
    DBAdapter db = new DBAdapter(this);
    EditText etTytul;
    EditText etText;

    Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dziennik);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //WYSWIETLANIE NOTATEK
        final ArrayList<String> NOTATKI = new ArrayList<>();
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(DziennikActivity.this, android.R.layout.simple_list_item_1, NOTATKI);

        db.open();

        List<NoteClass> notes = db.getNotes();

        for(NoteClass note : notes) {
            NOTATKI.add(note.getTitle() + "         " +note.getDate() + "\n " + note.getText());
        }

        ListView listView = (ListView) findViewById(R.id.listView3);
        listView.setAdapter(adapter);
        db.close();

    }

    public void addNote(View view){

        final ArrayList<String> NOTATKI = new ArrayList<>();
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(DziennikActivity.this, android.R.layout.simple_list_item_1, NOTATKI);

        db.open();
        List<NoteClass> notes = db.getNotes();

        for(NoteClass note : notes) {
            NOTATKI.add(note.getTitle() + "         " +note.getDate() + "\n " + note.getText());
        }


        etTytul = (EditText)findViewById(R.id.editText6);
        etText = (EditText) findViewById(R.id.editText5);

        Calendar c = Calendar.getInstance();
        SimpleDateFormat _clockDateFormat = new SimpleDateFormat("dd.MM.yy");

        if(etTytul.getText().toString().length()!=0 &&etText.getText().toString().length()!=0) {
            db.insertNote(_clockDateFormat.format(c.getTime()).toString(), etTytul.getText().toString(), etText.getText().toString());
            db.close();
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }
        else
        {
            Toast toast = Toast.makeText(getApplicationContext(), "Nie wszystkie dane zostały wpisane.", Toast.LENGTH_SHORT);
            toast.show();
        }

    }
}
