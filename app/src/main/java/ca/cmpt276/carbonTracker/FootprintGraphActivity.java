package ca.cmpt276.carbonTracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

import com.example.sasha.carbontracker.R;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

/**
 * The FootprintGraphActivity takes all journeys from the JourneyCollection in the Carbon Model
 * and creates a graph that the user is able to interact with and view the output in a more visual
 * representation.
 *
 * @author Team Teal
 */

public class FootprintGraphActivity extends AppCompatActivity {

    private final String tableLabel = "CO2 Emission of Journeys (in gram)";

    CarbonModel currentInstance = CarbonModel.getInstance();
    String journeys[] = currentInstance.getJourneys().getJourneyDescription();

    int emissions[] = currentInstance.getJourneys().getJourneyEmission();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_footprint_graph);

        setupPieChart();
        setupButtons();
    }

    private void setupPieChart() {
        List<PieEntry> pieEntries = new ArrayList<>();
        for (int i = 0; i < emissions.length; i++) {
            pieEntries.add(new PieEntry((float) emissions[i]));
        }

        PieDataSet dataSet = new PieDataSet(pieEntries, tableLabel);
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        dataSet.setSliceSpace(2);
        dataSet.setValueTextSize(12);
        PieData data = new PieData(dataSet);

        PieChart chart = (PieChart) findViewById(R.id.chart);
        chart.setData(data);
        chart.setDescription(null);
        chart.setHoleRadius(25f);
        chart.setTransparentCircleAlpha(0);
        chart.setRotationEnabled(true);
        chart.animateY(1500);

        chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                if (e == null)
                    return;

                Toast.makeText(FootprintGraphActivity.this,
                        journeys[(int) h.getX()], Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected() {
            }
        });
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