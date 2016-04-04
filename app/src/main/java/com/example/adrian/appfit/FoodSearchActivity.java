package com.example.adrian.appfit;

        import android.app.Activity;
        import android.content.Context;
        import android.os.Bundle;
        import android.view.View;
        import android.view.View.OnClickListener;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.Toast;

public class FoodSearchActivity extends Activity {
    DBAdapter db = new DBAdapter(this);
    EditText txtFood;
    EditText txtProtein;
    EditText txtFat;
    EditText txtCarb;


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_search);
        // Capture our button from layout
        Button setButton = (Button)findViewById(R.id.go);
        Button getButton = (Button)findViewById(R.id.genRan);
        // Register the onClick listener with the implementation above
        setButton.setOnClickListener(mAddListener);
        getButton.setOnClickListener(mAddListener);
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
            switch(v.getId())
            {
                case R.id.go:
                    db.open();
                    long id = 0;





                    if(txtFood.getText().length()==0||txtCarb.getText().length() == 0 || txtFat.getText().length() == 0 || txtProtein.getText().length() == 0)
                    {
                        Toast toast = Toast.makeText(getApplicationContext(), "DANE NIE ZOSTAŁY WPISANE", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    else
                    try
                    {

                        db.insertFood(txtFood.getText().toString(), txtProtein.getText().toString(), txtCarb.getText().toString(), txtFat.getText().toString());

                        id = db.getAllEntries();

                        Context context = getApplicationContext();
                        CharSequence text = "'" + txtFood.getText()+ "'" + " dodało się pomyślnie!\nObecna ilość produktów w bazie: " + id + ".";
                        int duration = Toast.LENGTH_LONG;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();

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
                case R.id.genRan:
                    db.open();


                    try
                    {
                        String quote = "";
                        quote = db.getEntry(txtFood.getText().toString());
                        Context context = getApplicationContext();
                        CharSequence text = quote;
                        int duration = Toast.LENGTH_LONG;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
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
        }
    };
}