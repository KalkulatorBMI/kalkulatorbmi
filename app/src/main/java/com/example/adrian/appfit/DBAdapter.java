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
    public static final String KEY_TEXT = "text";
    public static final String TEXT_OPTIONS = "TEXT NOT NULL";

    //TABELA DLA AKTUALNEJ WAGI
    public static final String KEY_WEIGHT = "weight";
    public static final String WEIGHT_OPTIONS = "REAL";

    //TABELA DLA CWICZEN
    public static final String KEY_IMGSRC = "imgsrc";
    public static final String IMGSRC_OPTIONS = "TEXT NOT NULL";


    private static final String TAG = "DBAdapter";

    //Informacje o bazie danych i tabeli
    private static final String DATABASE_NAME = "MyFood";
    private static final String DATABASE_TABLE = "tblFood";
    private static final String DATABASE_TABLE_2 = "tblMeals";
    private static final String DATABASE_TABLE_3= "tblNotes";
    private static final String DATABASE_TABLE_4 = "tblStats";
    private static final String DATABASE_TABLE_5 = "tblExer";
    private static final String DATABASE_TABLE_6 = "tblQuote";


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

    private static final String DATABASE_CREATE4 =
            "CREATE TABLE " + DATABASE_TABLE_4 + " ( " + KEY_ROWID + " " + ID_OPTIONS + ", " + KEY_DATE + " " + DATE_OPTIONS
                    + ", " + KEY_WEIGHT + " " + WEIGHT_OPTIONS +");";

    private static final String DATABASE_CREATE5 =
            "CREATE TABLE " + DATABASE_TABLE_5 + " ( " + KEY_ROWID + " " + ID_OPTIONS + ", " + KEY_IMGSRC + " " + IMGSRC_OPTIONS
                    + ", " + KEY_TITLE + " " + TITLE_OPTIONS + ", " + KEY_TEXT + " " + TEXT_OPTIONS + ");";


    private static final String DATABASE_CREATE6 =
            "CREATE TABLE " + DATABASE_TABLE_6 + " ( " + KEY_ROWID + " " + ID_OPTIONS + ", " + KEY_TITLE + " " + TITLE_OPTIONS + ", " + KEY_TEXT + " " + TEXT_OPTIONS + ");";


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

        String sql1 = "INSERT INTO tblFood (food, carb, protein, fat) VALUES ('banan', '24', '1', '0');";
        String sql2 = "INSERT INTO tblFood (food, carb, protein, fat) VALUES ('bułki kajzerki', '60', '8', '4');";
        String sql3 = "INSERT INTO tblFood (food, carb, protein, fat) VALUES ('chleb zwykły', '57', '5', '1');";
        String sql4 = "INSERT INTO tblFood (food, carb, protein, fat) VALUES ('indyk pierś','0', '20', '1');";
        String sql5 = "INSERT INTO tblFood (food, carb, protein, fat) VALUES ('jaja gotowane', '1', '10', '8');";
        String sql6 = "INSERT INTO tblFood (food, carb, protein, fat) VALUES ('kasza gryczana', '70', '13', '3');";
        String sql7 = "INSERT INTO tblFood (food, carb, protein, fat) VALUES ('kurczak pierś', '0', '22', '1');";
        String sql8 = "INSERT INTO tblFood (food, carb, protein, fat) VALUES ('makaron', '79', '10', '2');";
        String sql9 = "INSERT INTO tblFood (food, carb, protein, fat) VALUES ('makrela wędzona', '0', '20', '16');";
        String sql10 = "INSERT INTO tblFood (food, carb, protein, fat) VALUES ('mleko 2%', '5', '3', '2');";
        String sql11 = "INSERT INTO tblFood (food, carb, protein, fat) VALUES ('płatki owsiane', '70', '12', '7');";
        String sql12 = "INSERT INTO tblFood (food, carb, protein, fat) VALUES ('ryż biały', '80', '7', '2');";
        String sql13 = "INSERT INTO tblFood (food, carb, protein, fat) VALUES ('ser twarogowy chudy', '4', '20', '1');";
        String sql14 = "INSERT INTO tblFood (food, carb, protein, fat) VALUES ('serek wiejski light', '2', '11', '3');";
        String sql15 = "INSERT INTO tblFood (food, carb, protein, fat) VALUES ('ser twarogowy chudy', '4', '20', '1');";
        String sql16 = "INSERT INTO tblFood (food, carb, protein, fat) VALUES ('śmietana 12%', '4', '3', '12');";
        String sql17 = "INSERT INTO tblFood (food, carb, protein, fat) VALUES ('śmietana 18%', '4', '3', '18');";
        String sql18 = "INSERT INTO tblFood (food, carb, protein, fat) VALUES ('śmietana 30%', '3', '2', '30');";
        String sql19 = "INSERT INTO tblFood (food, carb, protein, fat) VALUES ('mąka orkiszowa', '59', '15', '2');";
        String sql20 = "INSERT INTO tblFood (food, carb, protein, fat) VALUES ('mąka gryczana', '71', '12', '3');";
        String sql21 = "INSERT INTO tblFood (food, carb, protein, fat) VALUES ('mąka pszenna graham', '71', '11', '2');";
        String sql22 = "INSERT INTO tblFood (food, carb, protein, fat) VALUES ('mąka tortowa', '71', '10', '1');";
        String sql23 = "INSERT INTO tblFood (food, carb, protein, fat) VALUES ('skrobia ziemniaczana', '84', '1', '1');";
        String sql24 = "INSERT INTO tblFood (food, carb, protein, fat) VALUES ('jajecznica', '1', '10', '9');";
        String sql25 = "INSERT INTO tblFood (food, carb, protein, fat) VALUES ('jajko przepiórcze', '1', '13', '11');";
        String sql26 = "INSERT INTO tblFood (food, carb, protein, fat) VALUES ('jajko sadzone', '1', '10', '14');";
        String sql27 = "INSERT INTO tblFood (food, carb, protein, fat) VALUES ('jajko kurze gotowane', '1', '13', '10');";
        String sql28 = "INSERT INTO tblFood (food, carb, protein, fat) VALUES ('masło osełkowe', '0', '0', '82');";
        String sql29 = "INSERT INTO tblFood (food, carb, protein, fat) VALUES ('masło śmietankowe', '1', '1', '74');";
        String sql30 = "INSERT INTO tblFood (food, carb, protein, fat) VALUES ('margaryna Benecol', '0', '0', '60');";
        String sql31 = "INSERT INTO tblFood (food, carb, protein, fat) VALUES ('olej rzepakowy', '0', '0', '100');";
        String sql32 = "INSERT INTO tblFood (food, carb, protein, fat) VALUES ('oliwa z oliwek', '1', '0', '100');";
        String sql33 = "INSERT INTO tblFood (food, carb, protein, fat) VALUES ('kurczak gotowany w jarzynach', '5', '8', '6');";
        String sql34 = "INSERT INTO tblFood (food, carb, protein, fat) VALUES ('smażona pierś z kurczaka', '0', '30', '4');";
        String sql35 = "INSERT INTO tblFood (food, carb, protein, fat) VALUES ('duszona pierś z kurczaka', '0', '27', '3');";
        String sql36 = "INSERT INTO tblFood (food, carb, protein, fat) VALUES ('wątróbka z kurczaka', '1', '25', '9');";
        String sql37 = "INSERT INTO tblFood (food, carb, protein, fat) VALUES ('kabanosy drobiowe', '0', '25', '45');";
        String sql38 = "INSERT INTO tblFood (food, carb, protein, fat) VALUES ('bukiet surówek', '9', '2', '6');";
        String sql39 = "INSERT INTO tblFood (food, carb, protein, fat) VALUES ('mizeria', '7', '2', '5');";
        String sql40 = "INSERT INTO tblFood (food, carb, protein, fat) VALUES ('omlet ze szpinakiem', '6', '7', '17');";
        String sql41 = "INSERT INTO tblFood (food, carb, protein, fat) VALUES ('placki z cukinii i szpinaku', '29', '32', '40');";
        String sql42 = "INSERT INTO tblFood (food, carb, protein, fat) VALUES ('łosoś', '0', '20', '14');";
        String sql43 = "INSERT INTO tblFood (food, carb, protein, fat) VALUES ('tuńczyk', '0', '24', '5');";
        String sql44 = "INSERT INTO tblFood (food, carb, protein, fat) VALUES ('panga', '0', '13', '4');";
        String sql45 = "INSERT INTO tblFood (food, carb, protein, fat) VALUES ('makrela wędzona', '0', '21', '15');";
        String sql46 = "INSERT INTO tblFood (food, carb, protein, fat) VALUES ('dorsz', '0', '17', '1');";
        String sql47 = "INSERT INTO tblFood (food, carb, protein, fat) VALUES ('szynka z piersi kurczaka', '1', '21', '1');";
        String sql48 = "INSERT INTO tblFood (food, carb, protein, fat) VALUES ('szynka z indyka', '0', '17', '2');";
        String sql49 = "INSERT INTO tblFood (food, carb, protein, fat) VALUES ('kiełbasa krakowska sucha', '4', '23', '3');";
        String sql50 = "INSERT INTO tblFood (food, carb, protein, fat) VALUES ('kabanosy wieprzowe', '0', '27', '24');";

        String ex_sql1 = "INSERT INTO tblExer (title, text, imgsrc) VALUES ('Pompki', 'Należy ustawić ręce tak, aby przy wykonaniu pompki w stawie łokciowym był kąt prosty, czyli ręce powinny być ustawione szerzej niż stawy barkowe. Ilość: 3 serie po MAX powtórzeń.', 'pompki');";
        String ex_sql2 = "INSERT INTO tblExer (title, text, imgsrc) VALUES ('Przysiady', 'Nogi rozstaw na szerokość barków, rozluźnij kolana a stopy ustaw równolegle do siebie. Ciało obniżaj aż do uzyskania kąta 90 stopni między udami a podudziami. Ilość: 4 serie po 20 powtórzeń.', 'squats');";
        String ex_sql3 = "INSERT INTO tblExer (title, text, imgsrc) VALUES ('Brzuszki', 'Połóż się tyłem. Nogi ugięte w stawach kolanowych pod kątem ok. 90 stopni. Stopy uniesione w powietrzu. Dłonie trzymamy na potylicy lub skroniach. Ilość: 4 serie po 25 powtórzeń. Zwiększać co tydzień o 5.', 'crunch');";
        String ex_sql4 = "INSERT INTO tblExer (title, text, imgsrc) VALUES ('Dipsy', 'Postaw równolegle dwa krzesła, w takiej odległości od siebie, abyś mógł na jednym oprzeć się piętami, a na drugim umieścić dłonie. Wykonaj między nimi pompkę, uginając przedramiona w łokciach. Ilość: 3 serie po MAX powtórzeń.', 'dips');";

        String qu_sql1 = "INSERT INTO tblQuote (title, text) VALUES ('Michael Jordan', 'Mogę zaakceptować porażkę, ale nie mogę zaakceptować braku próby.')";
        String qu_sql2 = "INSERT INTO tblQuote (title, text) VALUES ('Edward John Phelps', 'Człowiek, który nie robi błędów, zwykle nie robi niczego.')";
        String qu_sql3 = "INSERT INTO tblQuote (title, text) VALUES ('Philip Wylie', 'Sukces to drabina, po której nie sposób wspiąć się z rękami w kieszeniach.')";
        String qu_sql4 = "INSERT INTO tblQuote (title, text) VALUES ('Thomas Fowell Buxton', 'Dysponując zwykłymi talentami i niezwykłą wytrwałością możesz osiągnąć wszystko.')";
        String qu_sql5 = "INSERT INTO tblQuote (title, text) VALUES ('Arystoteles', 'Nasze szczęście zależy od nas samych.')";


        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE);
            db.execSQL(DATABASE_CREATE2);
            db.execSQL(DATABASE_CREATE3);
            db.execSQL(DATABASE_CREATE4);
            db.execSQL(DATABASE_CREATE5);
            db.execSQL(DATABASE_CREATE6);

            //UZUPELNIANIE TABELI PRODUKTOW
            String[] statements = new String[]{sql1,sql2,sql3,sql4,sql5,sql6,sql7,sql8,sql9,sql10,sql11,sql12,sql13,
                    sql14,sql15,sql16,sql17,sql18,sql19,sql20,sql21,sql22,sql23,sql24,sql25,sql26,sql27,sql28,sql29
                    ,sql30,sql31,sql32,sql33,sql34,sql35,sql36,sql37,sql38,sql39,sql40,sql41,sql42,sql43,sql44,sql45,
                    sql46,sql47,sql48,sql49,sql50};

            for(String sql : statements){
                db.execSQL(sql);
            }

            //UZUPELNIANIE TABELI CWICZEN
            String [] statements2 = new String []{ex_sql1, ex_sql2, ex_sql3, ex_sql4};

            for(String sql: statements2) {
                db.execSQL(sql);
            }

            //UZUPELNIANIE TABELI CYTATOW
            String[] statements3 = new String []{qu_sql1,qu_sql2,qu_sql3,qu_sql4,qu_sql5};

            for(String sql:statements3) {
                db.execSQL(sql);
            }
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
            db.execSQL("DTOP TABLE IF EXISTS tblStats");
            db.execSQL("DROP TABLE IF EXISTS tblExer");
            db.execSQL("DROP TABLE IF EXISTS tblQuote");
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

    //--------------Tabela FOOD------------------------------------------------------

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
                    c.getString(c.getColumnIndex(KEY_PROTEIN)),c.getString(c.getColumnIndex(KEY_FAT)),
                    c.getString(c.getColumnIndex(KEY_CARB)));

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

    //----------------------------Tabela MEAL------------------------------------

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

    //-----------------------------Tabela NOTES---------------------------------------

    //DODAJ NOWA NOTATKE

    public boolean insertNote(String date, String title, String text) {

            ContentValues initialValues = new ContentValues();
            initialValues.put(KEY_DATE, date);
            initialValues.put(KEY_TITLE, title);
            initialValues.put(KEY_TEXT, text);

            return db.insert(DATABASE_TABLE_3, null, initialValues) > 0;
        }

    //POBIERZ NOTATKI
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

    //---------------------------------Tabela STATS----------------------------


    public boolean insertStat(String date, float weight) {

        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_DATE, date);
        initialValues.put(KEY_WEIGHT, weight);
        return db.insert(DATABASE_TABLE_4, null, initialValues) > 0;
    }

    public ArrayList<StatClass> getStats() {
        String query = "SELECT * FROM " + DATABASE_TABLE_4;

        ArrayList<StatClass> list = new ArrayList<StatClass>();
        Cursor c = db.rawQuery(query, new String[] {});

        if(c.moveToFirst()) {
            do {
                StatClass statClass = new StatClass(c.getString(c.getColumnIndex(KEY_DATE)),
                        c.getFloat(c.getColumnIndex(KEY_WEIGHT)), c.getInt(c.getColumnIndex(KEY_ROWID)));
                list.add(statClass);
            } while (c.moveToNext());
        }
        if(c != null && !c.isClosed()) {
            c.close();
        }
        return list;
    }

    //TABELA Z CWICZENIAMI, POBIERANIE CWICZEN

    public ArrayList<ExercClass> getExer() {
        String query = "SELECT * FROM " + DATABASE_TABLE_5;

        ArrayList<ExercClass> list = new ArrayList<ExercClass>();
        Cursor c = db.rawQuery(query, new String[] {});

        if(c.moveToFirst()) {
            do {
                ExercClass exerClass = new ExercClass(c.getString(c.getColumnIndex(KEY_IMGSRC)),
                        c.getString(c.getColumnIndex(KEY_TITLE)),c.getString(c.getColumnIndex(KEY_TEXT)), c.getInt(c.getColumnIndex(KEY_ROWID)));
                list.add(exerClass);
            } while (c.moveToNext());
        }
        if(c != null && !c.isClosed()) {
            c.close();
        }
        return list;
    }


    //Znajdź produkt
    public ExercClass getSinExerc(String arg) {
        String query = "SELECT * FROM " + DATABASE_TABLE_5 + " WHERE title =?";

        Cursor c = db.rawQuery(query, new String[] {arg});

        if(c.moveToFirst()){
            ExercClass exerClass = new ExercClass(c.getString(c.getColumnIndex(KEY_IMGSRC)),
                    c.getString(c.getColumnIndex(KEY_TITLE)),c.getString(c.getColumnIndex(KEY_TEXT)), c.getInt(c.getColumnIndex(KEY_ROWID)));
            if(c != null && !c.isClosed()) {
                c.close();
            }

            return exerClass;
        }
        else {
            if(c != null && !c.isClosed()) {
                c.close();
            }
            return null;
        }
    }

    //--------------------------------------------Tabela NOTATKI --------------------------------------

    public int getAmQuotes() {
        Cursor cursor = db.rawQuery(
                "SELECT COUNT(_id) FROM tblQuote", null);
        if (cursor.moveToFirst()) {
            return cursor.getInt(0);
        }
        return cursor.getInt(0);
    }

    public QuoteClass getRandQuo(int id) {
        String query = "SELECT * FROM " + DATABASE_TABLE_6 + " WHERE _id =?";
        Cursor c = db.rawQuery(query, new String[] {id+""});

        if(c.moveToFirst()){
            QuoteClass quoteClass = new QuoteClass(c.getString(c.getColumnIndex(KEY_TITLE)),
                    c.getString(c.getColumnIndex(KEY_TEXT)),c.getInt(c.getColumnIndex(KEY_ROWID))
                    );

            if(c != null && !c.isClosed()) {
                c.close();
            }

            return quoteClass;
        }
        else {
            if(c != null && !c.isClosed()) {
                c.close();
            }
            return null;
        }
    }

}


