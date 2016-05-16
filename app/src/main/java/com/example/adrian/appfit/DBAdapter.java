package com.example.adrian.appfit;

import java.util.Random;
import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.Html;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Adrian on 03.04.2016.
 */
public class DBAdapter {
    int id = 0;

    //TABELA DLA PRODUKTOW
    public static final String KEY_ROWID = "_id";
    public static final String ID_OPTIONS = "INTEGER PRIMARY KEY AUTOINCREMENT";
    public static final String KEY_FOOD = "food";
    public static final String FOOD_OPTIONS = "TEXT NOT NULL";
    public static final String KEY_PROTEIN = "protein";
    public static final String PROTEIN_OPTIONS = "TEXT NOT NULL";
    public static final String KEY_CARB = "carb";
    public static final String CARB_OPTIONS = "TEXT NOT NULL";
    public static final String KEY_FAT = "fat";
    public static final String FAT_OPTIONS = "TEXT NOT NULL";

    //TABELA DLA POSILKOW
    private static final String KEY_DATE = "date";
    private static final String DATE_OPTIONS = "DATE";
    private static final String KEY_AMOUNT = "amount";
    private static final String AMOUNT_OPTIONS = "TEXT NOT NULL";

    //TABELA DLA NOTATEK
    public static final String KEY_TITLE = "title";
    public static final String TITLE_OPTIONS = "TEXT NOT NULL";
    public static final String KEY_TEXT = "fat";
    public static final String TEXT_OPTIONS = "TEXT NOT NULL";
/*
    //TABELA DLA DANYCH O UZYTKOWNIKU
    public static final String KEY_SEX = "sex";
    public static final String SEX_OPTIONS="TEXT NOT NULL";
    public static final String KEY_AGE = "age";
    public static final String AGE_OPTIONS="TEXT NOT NULL";
    public static final String KEY_WEIGHT = "age";
    public static final String WEIGHT_OPTIONS = "TEXT NOT NULL";
    public static final String KEY_GOAL = "goal";
    public static final String GOAL_OPTIONS = "TEXT NOT NULL";
    public static final String KEY_HEIGHT = "height";
    public static final String HEIGHT_OPTIONS = "TEXT NOT NULL";
*/
    private static final String TAG = "DBAdapter";

    //Informacje o bazie danych i tabeli
    private static final String DATABASE_NAME = "MyFood";
    private static final String DATABASE_TABLE = "tblFood";
    private static final String DATABASE_TABLE_2 = "tblMeals";
    private static final String DATABASE_TABLE_3= "tblNotes";
    private static final int DATABASE_VERSION = 1;

    //Tworzenie tabeli
    private static final String DATABASE_CREATE =
            "CREATE TABLE " + DATABASE_TABLE + " ( " + KEY_ROWID + " " + ID_OPTIONS + ", " + KEY_FOOD + " " + FOOD_OPTIONS + ", " + KEY_PROTEIN + " " +
                    PROTEIN_OPTIONS + ", " + KEY_CARB + " " + CARB_OPTIONS + ", " + KEY_FAT + " " + FAT_OPTIONS + ");";

    private static final String DATABASE_CREATE2 =
            "CREATE TABLE " + DATABASE_TABLE_2 + " ( " + KEY_ROWID + " " + ID_OPTIONS + ", " + KEY_FOOD + " " + FOOD_OPTIONS +
                    ", " + KEY_DATE + " " + DATE_OPTIONS + ", " + KEY_AMOUNT + " " + AMOUNT_OPTIONS + ");";

    private static final String DATABASE_CREATE3 =
            "CREATE TABLE " + DATABASE_TABLE_3 + " ( " + KEY_ROWID + " " + ID_OPTIONS + ", " + KEY_DATE + " " + DATE_OPTIONS
            + ", " + KEY_TITLE + " " + TITLE_OPTIONS + ", " + KEY_TEXT + " " + TEXT_OPTIONS + ");";

    private final Context context;
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    public DBAdapter(Context ctx) {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE);
            db.execSQL(DATABASE_CREATE2);
            db.execSQL(DATABASE_CREATE3);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion,
                              int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion
                    + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS tblFood");
            db.execSQL("DROP TABLE IF EXISTS tblMeals");
            db.execSQL("DROP TABLE IF EXISTS tblData");
            onCreate(db);
        }
    }

    //Otwórz bazę danych
    public DBAdapter open() throws SQLException {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    //Zamknij bazę danych
    public void close() {
        DBHelper.close();
    }

    //Wprowadź nowe produkty
    public boolean insertFood(String food, String protein, String carb, String fat) {

        if(isEntry(food))
            return false;
        else {
            ContentValues initialValues = new ContentValues();
            initialValues.put(KEY_FOOD, food);
            initialValues.put(KEY_CARB, carb);
            initialValues.put(KEY_FAT, fat);
            initialValues.put(KEY_PROTEIN, protein);

            return db.insert(DATABASE_TABLE, null, initialValues) > 0;
        }
    }

    public boolean deleteFood(String food) {
            return db.delete(DATABASE_TABLE, KEY_FOOD +"=?", new String[] {food}) > 0;
    }

    public int getAllEntries() {
        Cursor cursor = db.rawQuery(
                "SELECT COUNT(food) FROM tblFood", null);
        if (cursor.moveToFirst()) {
            return cursor.getInt(0);
        }
        return cursor.getInt(0);
    }

    //ZWROC PRODUKTY Z PODANA CZESCIOWA NAZWA
    public ArrayList<ProductClass> getProducts(String pattern) {
        String query = "SELECT * FROM " + DATABASE_TABLE + " WHERE food like" + "'%" + pattern + "%'";

        ArrayList<ProductClass> list = new ArrayList<ProductClass>();
        Cursor c = db.rawQuery(query, new String[] {});

        if(c.moveToFirst()) {
            do {
                ProductClass productClass = new ProductClass(c.getString(c.getColumnIndex(KEY_FOOD)),
                        c.getString(c.getColumnIndex(KEY_PROTEIN)), c.getString(c.getColumnIndex(KEY_FAT)), c.getString(c.getColumnIndex(KEY_CARB)));
                ;
                list.add(productClass);
            } while (c.moveToNext());
        }
        if(c != null && !c.isClosed()) {
            c.close();
        }
        return list;
    }



    //Znajdź produkt
    public ProductClass getEntry(String arg) {
        String query = "SELECT * FROM " + DATABASE_TABLE + " WHERE food =?";

        Cursor c = db.rawQuery(query, new String[] {arg});

        if(c.moveToFirst()){
            ProductClass productClass = new ProductClass(c.getString(c.getColumnIndex(KEY_FOOD)),
                    c.getString(c.getColumnIndex(KEY_CARB)),c.getString(c.getColumnIndex(KEY_PROTEIN)),
                    c.getString(c.getColumnIndex(KEY_FAT)));

            if(c != null && !c.isClosed()) {
                c.close();
            }

            return productClass;
        }
        else {
            if(c != null && !c.isClosed()) {
                c.close();
            }
            return null;
        }
    }

    public boolean isEntry(String isFood) {
        String query = "SELECT * FROM " + DATABASE_TABLE + " WHERE food =?";

        Cursor c = db.rawQuery(query, new String[]{query});

        if(c.moveToFirst()) {
            c.close();
            return true;
        }
        else {
            c.close();
            return false;
        }
    }

    //WPROWADZ POSILEK
    public boolean insertMeal(String food, String date, String amount) {
            ContentValues initialValues = new ContentValues();
            initialValues.put(KEY_FOOD, food);
            initialValues.put(KEY_DATE, date);
            initialValues.put(KEY_AMOUNT, amount);

            return db.insert(DATABASE_TABLE_2, null, initialValues) > 0;
    }

    //Funkcja zwracajaca wszystkie posilki z danego dnia
    public ArrayList<MealClass> getMeals(String date) {
        String query = "SELECT * FROM " + DATABASE_TABLE_2 + " WHERE date =?";

        ArrayList<MealClass> list = new ArrayList<MealClass>();
        Cursor c = db.rawQuery(query, new String[] {date});

        if(c.moveToFirst()) {
            do {
                MealClass mealClass = new MealClass(c.getString(c.getColumnIndex(KEY_FOOD)),
                        c.getString(c.getColumnIndex(KEY_DATE)), c.getString(c.getColumnIndex(KEY_AMOUNT)),
                        c.getInt(c.getColumnIndex(KEY_ROWID)));
                list.add(mealClass);
            } while (c.moveToNext());
        }
            if(c != null && !c.isClosed()) {
                c.close();
            }
            return list;
        }

    public boolean deleteMeals(String name) {
      return db.delete(DATABASE_TABLE_2, KEY_FOOD +"=?", new String[] {name}) > 0;
    }


    public boolean deleteMeal(int id) {
        return db.delete(DATABASE_TABLE_2, KEY_ROWID +"=?", new String[] {""+id}) > 0;
    }
    //NOTATKI
    //Dodaj nowe notatki

    public boolean insertNote(String date, String title, String text) {

            ContentValues initialValues = new ContentValues();
            initialValues.put(KEY_DATE, date);
            initialValues.put(KEY_TITLE, title);
            initialValues.put(KEY_TEXT, text);

            return db.insert(DATABASE_TABLE_3, null, initialValues) > 0;
        }

    public ArrayList<NoteClass> getNotes() {
        String query = "SELECT * FROM " + DATABASE_TABLE_3;

        ArrayList<NoteClass> list = new ArrayList<NoteClass>();
        Cursor c = db.rawQuery(query, new String[] {});

        if(c.moveToFirst()) {
            do {
                NoteClass noteClass = new NoteClass(c.getString(c.getColumnIndex(KEY_DATE)),
                        c.getString(c.getColumnIndex(KEY_TITLE)), c.getString(c.getColumnIndex(KEY_TEXT)), c.getInt(c.getColumnIndex(KEY_ROWID)));
                list.add(noteClass);
            } while (c.moveToNext());
        }
        if(c != null && !c.isClosed()) {
            c.close();
        }
        return list;
    }
    }



