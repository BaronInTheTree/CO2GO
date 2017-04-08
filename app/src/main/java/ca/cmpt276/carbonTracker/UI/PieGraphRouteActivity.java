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
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ca.cmpt276.carbonTracker.Internal_Logic.CarbonModel;
import ca.cmpt276.carbonTracker.Internal_Logic.DayData;
import ca.cmpt276.carbonTracker.Internal_Logic.EmissionCollection;

import static ca.cmpt276.carbonTracker.UI.CalendarDialogMainMenu.selectedDate;
import static ca.cmpt276.carbonTracker.UI.GraphMenuActivity.dayMode;
import static ca.cmpt276.carbonTracker.UI.GraphMenuActivity.monthMode;
import static ca.cmpt276.carbonTracker.UI.GraphMenuActivity.yearMode;

public class PieGraphRouteActivity extends AppCompatActivity {

    private final String tableLabel = "CO2 Emission of Each Route Mode (in gram)";
    private final String tableLabelTree = "CO2 Emission of Each Route Mode (in Tree Units)";

    Date today = new Date();
    List<DayData> dataList;
    EmissionCollection emissionColl;
    String[] routeModes;
    Float[] emissions;
    CarbonModel cm = CarbonModel.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_graph_route_modes);

        initializeData();
        setupPieChart();
        setupButtons();
    }

    private void initializeData() {
        if (dayMode){
            dataList= DayData.getDayDataWithinInterval(selectedDate,selectedDate);
            dayMode = false;   // reset it to original value.
        }
        if (monthMode){
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -28);   // get date 28 days before today
            dataList = DayData.getDayDataWithinInterval(cal.getTime(),today);
            monthMode=false;   // reset it to original value.
        }
        if (yearMode){
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -365);  // get date 365 days before today
            dataList = DayData.getDayDataWithinInterval(cal.getTime(),today);
            yearMode=false;   // reset it to original value.
        }
        emissionColl = new EmissionCollection(dataList);
        routeModes = emissionColl.getRouteModes();
        emissions = emissionColl.getEmissionsRoute();

    }

    private void setupPieChart() {
        List<PieEntry> pieEntries = new ArrayList<>();
        PieDataSet dataSet = new PieDataSet(pieEntries, tableLabel);

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

        PieChart chart = (PieChart) findViewById(R.id.routePieGraph);
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
                Toast.makeText(PieGraphRouteActivity.this,
                        routeModes[(int) h.getX()], Toast.LENGTH_SHORT).show();
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
