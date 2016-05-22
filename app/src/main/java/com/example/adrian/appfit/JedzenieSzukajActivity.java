package com.example.adrian.appfit;

        import android.app.Activity;
        import android.content.Context;
        import android.content.DialogInterface;
        import android.content.SharedPreferences;
        import android.os.Bundle;
        import android.support.v7.app.AlertDialog;
        import android.support.v7.app.AppCompatActivity;
        import android.text.Html;
        import android.text.InputType;
        import android.view.View;
        import android.view.View.OnClickListener;
        import android.view.WindowManager;
        import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ListView;
        import android.widget.TextView;
        import android.widget.Toast;

        import java.text.SimpleDateFormat;
        import java.util.ArrayList;
        import java.util.Calendar;
        import java.util.List;

public class JedzenieSzukajActivity extends AppCompatActivity {

    DBAdapter db = new DBAdapter(this);
    EditText txtFood;
    EditText txtProtein;
    EditText txtFat;
    EditText txtCarb;
    TextView txtInfoProduct;
    Button delButton;
    Button addButton;
    String nazwa;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jedzenie_szukaj);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Button searchButton = (Button)findViewById(R.id.searchFor);
        Button addButton = (Button)findViewById(R.id.addIt);

        searchButton.setOnClickListener(mAddListener);
        addButton.setOnClickListener(mAddListener);


    }

    // Create an anonymous implementation of OnClickListener
    private OnClickListener mAddListener = new OnClickListener()
    {

        public void onClick(View v)
        {
            txtFood = (EditText)findViewById(R.id.editText4);
            txtProtein = (EditText)findViewById(R.id.editText);
            txtCarb = (EditText)findViewById(R.id.editText2);
            txtFat = (EditText)findViewById(R.id.editText3);
            txtInfoProduct = (TextView)findViewById(R.id.infoProduct);
            delButton = (Button)findViewById(R.id.deleteIt);
            addButton = (Button) findViewById(R.id.button14);

            switch(v.getId())
            {
                case R.id.addIt:
                    db.open();
                    long id = 0;

                    if(txtFood.getText().length()==0||txtCarb.getText().length() == 0 || txtFat.getText().length() == 0 || txtProtein.getText().length() == 0)
                    {
                        Toast toast = Toast.makeText(getApplicationContext(), "Nie wszystkie dane zostały wpisane.", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    else
                    try
                    {
                        Context context = getApplicationContext();

                        if(db.getEntry(txtFood.getText().toString())!=null)
                        {
                            Toast toast = Toast.makeText(getApplicationContext(), "Produkt '" + txtFood.getText().toString() + "' jest już w bazie danych.", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                        else {
                            db.insertFood(txtFood.getText().toString(), txtProtein.getText().toString(), txtCarb.getText().toString(), txtFat.getText().toString());
                            Toast toast = Toast.makeText(getApplicationContext(), "Produkt " + txtFood.getText().toString() + " został pomyślnie dodany do bazy danych.", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }
                    catch (Exception ex)
                    {
                        Context context = getApplicationContext();
                        CharSequence text = ex.toString() + "ID = " + id;
                        int duration = Toast.LENGTH_LONG;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }
                    db.close();
                    break;

                case R.id.searchFor:

                    //WYSWIETLANIE DANE LISTY PRODUKTOW
                    final ArrayList<String> LISTA_PRODUKTY = new ArrayList<>();
                   final ArrayAdapter<String> adapter = new ArrayAdapter<String>(JedzenieSzukajActivity.this, android.R.layout.simple_list_item_1, LISTA_PRODUKTY);

                    db.open();
                    try
                    {
                        List<ProductClass> products = db.getProducts(txtFood.getText().toString());

                        for(ProductClass prod : products) {
                            LISTA_PRODUKTY.add(prod.getName());
                        }

                    }
                    catch (Exception ex)
                    {
                        Context context = getApplicationContext();
                        CharSequence text = ex.toString();
                        int duration = Toast.LENGTH_LONG;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }
                    db.close();

                    final ListView listView = (ListView) findViewById(R.id.listView);
                    listView.setVisibility(View.VISIBLE);
                    delButton.setVisibility(View.INVISIBLE);
                    addButton.setVisibility(View.INVISIBLE);

                    txtInfoProduct.setText("Wyszukaj, dodaj lub usuń produkt.");

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            listView.setVisibility(View.INVISIBLE);
                            nazwa = (String) ((TextView) view).getText();

                            ProductClass sProductClass;
                            db.open();
                            sProductClass = db.getEntry(nazwa);

                            txtInfoProduct.setText(Html.fromHtml("<h1>" + sProductClass.getName() + "</h1>"
                                    +"<b>Zawartość węglowodanów: </b>" + sProductClass.getCarbo() + " g.<br/>"
                                    +"<b>Zawartość białka: </b>" + sProductClass.getProtein() + " g.<br/>"
                                    +"<b>Zawartość tłuszczy: </b>" + sProductClass.getFat() + " g.<br/>"));
                            delButton.setVisibility(View.VISIBLE);
                            addButton.setVisibility(View.VISIBLE);
                            db.close();
                        }
                    });
                    listView.setAdapter(adapter);
                    break;
            }
        }
    };

    //USUWANIE PRODUKTU
    public void del(View view){
        db.open();

        try
        {
            if(db.deleteFood(nazwa)){
                Toast toast = Toast.makeText(getApplicationContext(), "Produkt '" + txtFood.getText().toString() + "' został usunięty z bazy danych.", Toast.LENGTH_SHORT);
                toast.show();
               db.deleteMeals(nazwa);
                txtInfoProduct.setText("Wyszukaj, dodaj lub usuń produkt.");
                delButton.setVisibility(View.INVISIBLE);
                addButton.setVisibility(View.INVISIBLE);
            }
            else{
                Toast toast = Toast.makeText(getApplicationContext(), "Produktu '" + txtFood.getText().toString() + "' nie ma w bazie danych.", Toast.LENGTH_SHORT);
                toast.show();
            }

        }
        catch (Exception ex)
        {
            Context context = getApplicationContext();
            CharSequence text = ex.toString();
            int duration = Toast.LENGTH_LONG;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
        db.close();
    }

    //DODAWANIE POSILKU
    public void addMeal(View view){

        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Ile teraz zjadłeś - '" + nazwa +"'?");
        alert.setMessage("Ilość podaj w gramach.");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        alert.setView(input);

        alert.setPositiveButton("Akceptuj", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                int amount;
                try {
                    amount = Integer.parseInt(input.getText().toString());

                    Calendar c = Calendar.getInstance();
                    SimpleDateFormat _clockDateFormat = new SimpleDateFormat("dd.MM.yy");
                    db.open();
                    db.insertMeal(nazwa, _clockDateFormat.format(c.getTime()).toString(), "" + amount);
                    db.close();
                    Toast toast = Toast.makeText(getApplicationContext(), "Posiłek został dodany.", Toast.LENGTH_SHORT);
                    toast.show();

                } catch (NumberFormatException e) {
                    //INT MA NIEPRAWIDLOWA WARTOSC
                }
            }
        });

        alert.setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //NIE ROBISZ NIC
                Toast toast = Toast.makeText(getApplicationContext(), "Posiłek nie został dodany.", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
        alert.show();
        db.close();
    }
}