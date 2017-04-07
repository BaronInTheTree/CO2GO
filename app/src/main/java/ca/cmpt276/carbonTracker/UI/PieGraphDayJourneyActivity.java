package ca.cmpt276.carbonTracker.UI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.sasha.carbontracker.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import ca.cmpt276.carbonTracker.Internal_Logic.CarbonModel;
import ca.cmpt276.carbonTracker.Internal_Logic.DayData;
import ca.cmpt276.carbonTracker.Internal_Logic.Journey;
import ca.cmpt276.carbonTracker.Internal_Logic.Utility;

import static ca.cmpt276.carbonTracker.UI.CalendarDialog.selectedDate;
import static ca.cmpt276.carbonTracker.UI.GraphMenuActivity.dayMode;

public class PieGraphDayJourneyActivity extends AppCompatActivity {

    private final String tableLabelCO2 = "CO2 Emission of Each Journey and Utility (in gram)";
    private final String tableLabelTree = "CO2 Emission of Each Journey and Utility (in Tree Units)";

    DayData dayData;
    String[] journeyModes;
    Float[] emissions;
    CarbonModel cm = CarbonModel.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_graph_day_journey);

        initializeData();
        setupPieChart();
        setupButtons();
    }

    private void initializeData() {
        dayData= DayData.getDayDataAtDate(selectedDate);
        dayMode = false;   // reset it to original value.

        List<Journey> journeyList = dayData.getJourneyList();
        List<Utility> utilityList = dayData.getUtilityList();
        int listSize = journeyList.size()+utilityList.size();

        journeyModes = new String[listSize];
        emissions = new Float[listSize];

        for (int i=0;i<journeyList.size();i++){
            journeyModes[i] = journeyList.get(i).getDescription();
            emissions[i] = journeyList.get(i).getEmissions();
        }

        for (int i =0, j= journeyList.size();i<utilityList.size();i++,j++){
            journeyModes[j] = utilityList.get(i).displayToList();
            emissions[j]=utilityList.get(i).getCO2PerDayPerPerson();
        }
    }

    private void setupPieChart() {
        List<PieEntry> pieEntries = new ArrayList<>();
        PieDataSet dataSet = new PieDataSet(pieEntries, tableLabelCO2);

        for (int i = 0; i < emissions.length; i++) {
            pieEntries.add(new PieEntry((cm.getTreeUnit().getUnitValueGraphs(emissions[i]))));
        }

        if (cm.getTreeUnit().getTreeUnitStatus()) {
            dataSet = new PieDataSet(pieEntries, tableLabelTree);
        }

        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        dataSet.setSliceSpace(2);
        dataSet.setValueTextSize(12);
        PieData data = new PieData(dataSet);

        PieChart chart = (PieChart) findViewById(R.id.dayJourneyPieGraph);
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
                Toast.makeText(PieGraphDayJourneyActivity.this,
                        journeyModes[(int) h.getX()], Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected() {
            }
        });
        chart.invalidate();
    }

    private void setupButtons() {
        Button back_btn = (Button) findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
