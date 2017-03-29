package ca.cmpt276.carbonTracker.UI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import java.util.Date;
import java.util.List;

import ca.cmpt276.carbonTracker.Internal_Logic.Car;
import ca.cmpt276.carbonTracker.Internal_Logic.CarbonModel;
import ca.cmpt276.carbonTracker.Internal_Logic.DateHandler;
import ca.cmpt276.carbonTracker.Internal_Logic.DayData;
import ca.cmpt276.carbonTracker.Internal_Logic.Journey;

public class TestActivity extends AppCompatActivity {

    private Date currentDate = new Date();
    CarbonModel model = CarbonModel.getInstance();
    private final String tableLabel = "CO2 Emissions for " + DateHandler.getMonthOfDate(currentDate)
            + "/" + DateHandler.getDayOfDate(currentDate)
            + "/" + DateHandler.getYearOfDate(currentDate);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_layout);
        setupPieChart();
    }

    private void setupPieChart() {
        PieChart chart = (PieChart) findViewById(R.id.mainMenu_pieChart);

        List<PieEntry> pieEntries = new ArrayList<>();

        DayData dayData = DayData.getDayDataAtDate(currentDate);

        pieEntries.add(new PieEntry(dayData.getElectricityEmissions()));
        pieEntries.add(new PieEntry(dayData.getNaturalGasEmissions()));
        pieEntries.add(new PieEntry(dayData.getTransportTypeEmissions_KM(Journey.Type.BUS)));
        pieEntries.add(new PieEntry(dayData.getTransportTypeEmissions_KM(Journey.Type.SKYTRAIN)));

        for (Car car : model.getCarCollection().getCarCollection()) {
            pieEntries.add(new PieEntry(dayData.getCarEmissions_KM(car)));
        }

        PieDataSet dataSet = new PieDataSet(pieEntries, tableLabel);
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        dataSet.setSliceSpace(2);
        dataSet.setValueTextSize(12);
        PieData data = new PieData(dataSet);

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
            }

            @Override
            public void onNothingSelected() {
            }
        });
        chart.invalidate();
    }

}
