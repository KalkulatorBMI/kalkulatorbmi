package com.example.adrian.appfit;

        import android.app.Activity;
        import android.content.Context;
        import android.os.Bundle;
        import android.text.Html;
        import android.view.View;
        import android.view.View.OnClickListener;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.TextView;
        import android.widget.Toast;

public class JedzenieSzukajActivity extends Activity {
    DBAdapter db = new DBAdapter(this);
    EditText txtFood;
    EditText txtProtein;
    EditText txtFat;
    EditText txtCarb;
    TextView txtInfoProduct;


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jedzenie_szukaj);
        // Capture our button from layout
        Button searchButton = (Button)findViewById(R.id.searchFor);
        Button addButton = (Button)findViewById(R.id.addIt);
        Button delButton = (Button) findViewById(R.id.deleteIt);

        // Register the onClick listener with the implementation above
        searchButton.setOnClickListener(mAddListener);
        addButton.setOnClickListener(mAddListener);
        delButton.setOnClickListener(mAddListener);
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
                    db.open();

                    try
                    {
                        ProductClass sProductClass;
                        sProductClass = db.getEntry(txtFood.getText().toString());
                        if(sProductClass == null)
                            txtInfoProduct.setText("W bazie nie ma takiego produktu. Pamiętaj, że zawsze możesz go dodać.");
                        else
                            txtInfoProduct.setText(Html.fromHtml("<h1>" + sProductClass.getName() + "</h1>"
                                +"<b>Zawartość węglowodanów: </b>" + sProductClass.getCarbo() + " g.<br/>"
                                    +"<b>Zawartość białka: </b>" + sProductClass.getProtein() + " g.<br/>"
                                    +"<b>Zawartość tłuszczy: </b>" + sProductClass.getFat() + " g.<br/>"));
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
                    break;

                case R.id.deleteIt:
                    db.open();

                    try
                    {
                        if(db.deleteFood(txtFood.getText().toString())==true){
                            Toast toast = Toast.makeText(getApplicationContext(), "Produkt '" + txtFood.getText().toString() + "' został usunięty z bazy danych.", Toast.LENGTH_SHORT);
                            toast.show();
                            txtInfoProduct.setText("Wyszukaj, dodaj lub usuń produkt.");
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
                    break;

            }
        }
    };
}