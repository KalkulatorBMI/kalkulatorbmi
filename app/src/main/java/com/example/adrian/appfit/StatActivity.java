package com.example.adrian.appfit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class StatActivity extends AppCompatActivity {
    DBAdapter db = new DBAdapter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stat);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        //TWORZENIE WYKRESU KOLOWEGO
        LineChart lineChart = (LineChart) findViewById(R.id.chart);
        ArrayList<Entry> entries = new ArrayList<>();
        int i=0;

        db.open();
        //TWORZENIE WARTOSCI
        List<StatClass> stats = db.getStats();
        for(StatClass stat : stats) {
            entries.add(new Entry(stat.getWeight(), i++));
        }

        LineDataSet dataset = new LineDataSet(entries, "");

        //TWORZENIE ETYKIET
        ArrayList<String> labels = new ArrayList<String>();
        i = 0;
        for(StatClass stat : stats) {
            labels.add(stat.getData());
        }
        db.close();

        LineData data = new LineData(labels, dataset);
        lineChart.setData(data);
        dataset.setColors(ColorTemplate.COLORFUL_COLORS);
        lineChart.getLegend().setEnabled(false);
        dataset.setDrawCubic(true);
        lineChart.setDescription("Twoja waga");
    }
}
