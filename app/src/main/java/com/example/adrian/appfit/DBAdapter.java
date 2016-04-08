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
    public static final String KEY_ROWID = "_id";
    public static final String ID_OPTIONS = "INTEGER PRIMARY KEY AUTOINCREMENT";
    public static final int ID_COLUMN = 0;
    public static final String KEY_FOOD = "food";
    public static final String FOOD_OPTIONS = "TEXT NOT NULL";
    public static final int FOOD_COLUMN = 1;
    public static final String KEY_PROTEIN = "protein";
    public static final String PROTEIN_OPTIONS = "TEXT NOT NULL";
    public static final int PROTEIN_COLUMN = 2;
    public static final String KEY_CARB = "carb";
    public static final String CARB_OPTIONS = "TEXT NOT NULL";
    public static final int CARB_COLUMN = 3;
    public static final String KEY_FAT = "fat";
    public static final String FAT_OPTIONS = "TEXT NOT NULL";
    public static final int FAT_COLUMN = 4;


    private static final String TAG = "DBAdapter";

    //Informacje o bazie danych i tabeli
    private static final String DATABASE_NAME = "MyFood";
    private static final String DATABASE_TABLE = "tblFood";
    private static final int DATABASE_VERSION = 1;

    //Tworzenie tabeli
    private static final String DATABASE_CREATE =
            "CREATE TABLE " + DATABASE_TABLE + " ( " + KEY_ROWID + " " + ID_OPTIONS + ", " + KEY_FOOD + " " + FOOD_OPTIONS + ", " + KEY_PROTEIN + " " +
                    PROTEIN_OPTIONS + ", " + KEY_CARB + " " + CARB_OPTIONS + ", " + KEY_FAT + " " + FAT_OPTIONS + ");";

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
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion,
                              int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion
                    + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS tblFood");
            onCreate(db);
        }
    }

    //---opens the database---
    public DBAdapter open() throws SQLException {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    //---closes the database---
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

    //Znajdź produkt
    public ProductClass getEntry(String arg) {
        String query = "SELECT * FROM " + DATABASE_TABLE + " WHERE food =?";

        Cursor c = db.rawQuery(query, new String[] {arg});

        if(c.moveToFirst()){
            ProductClass productClass = new ProductClass(c.getString(c.getColumnIndex(KEY_FOOD)),
                    c.getString(c.getColumnIndex(KEY_CARB)),c.getString(c.getColumnIndex(KEY_PROTEIN)),
                    c.getString(c.getColumnIndex(KEY_FAT)));

                    return productClass;
        }
        else
            return null;
    }

    public boolean isEntry(String isFood) {
        String query = "SELECT * FROM " + DATABASE_TABLE + " WHERE food =?";

        Cursor c = db.rawQuery(query, new String[]{query});

        if(c.moveToFirst())
            return true;
        else
            return false;
    }
}
