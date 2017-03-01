package ca.cmpt276.carbonTracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.sasha.carbontracker.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class FootprintGraphActivity extends AppCompatActivity {

    double emissions[] = {23.4,5.8,56.12,189.46,70.77,23.5,94.23};
    String journeys[]={"#1", "#2", "#3", "#4", "#5", "#6","#7"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_footprint_graph);

        setupPieChart();

        setupButtons();

    }


    private void setupPieChart() {
        List<PieEntry> pieEntries = new ArrayList<>();
        for (int i=0;i<emissions.length;i++){
            pieEntries.add(new PieEntry((float) emissions[i],journeys[i]));
        }

        PieDataSet dataSet = new PieDataSet(pieEntries,"Emission of Each Journey");
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        PieData data = new PieData(dataSet);

        PieChart chart = (PieChart) findViewById(R.id.chart);
        chart.setData(data);
        chart.invalidate();
    }

    private void setupButtons() {
        Button toTable_btn = (Button) findViewById(R.id.toTable_btn);
        toTable_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(FootprintGraphActivity.this, FootprintTableActivity.class));

            }
        });
    }
}
