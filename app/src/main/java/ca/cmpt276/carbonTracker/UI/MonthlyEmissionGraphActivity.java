package ca.cmpt276.carbonTracker.UI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.sasha.carbontracker.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import ca.cmpt276.carbonTracker.Internal_Logic.CarbonModel;

public class MonthlyEmissionGraphActivity extends AppCompatActivity {

    private LineChart chart;
    CarbonModel model = CarbonModel.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly_emission_graph);
        int currentYear = model.getDateHandler().getCurrentYear();
        int currentMonth = model.getDateHandler().getCurrentMonth();
        int currentDay = model.getDateHandler().getCurrentDay();

        chart = (LineChart) findViewById(R.id.chart);

        ArrayList<String> x_days = new ArrayList<>();
        ArrayList<Entry> y_carCO2 = new ArrayList<>();
        ArrayList<Entry> y_busCO2 = new ArrayList<>();
        ArrayList<Entry> y_skytrainCO2 = new ArrayList<>();
        ArrayList<Entry> y_utilityCO2 = new ArrayList<>();

        int days = 28;
        for (int d = model.getDateHandler().getCurrentDay(); d >= 0; d--) {
            // y_carCO2.add(new Entry(getCarCO2(currentYear, currentMonth, d), days));
            // y_busCO2.add(new Entry(getBusCO2(currentYear, currentMonth, d), days));
            // y_skytrainCO2.add(new Entry(getSkytrainCO2(currentYear, currentMonth, d), days));
            // y_utilityCO2.add(new Entry(getUtilityCO2(currentYear, currentMonth, d), days));
            days--;
        }

        if (currentMonth == 1) {
            currentYear--;
            currentMonth = 12;
        }

        for (int d = days; d >= 0; d--) {

        }

        int numDataPoints = 28;

        for (int i = 0; i < numDataPoints; i++) {

        }

        // get current Day.
        // Get list of dates from current day to beginning of month.
        // Get list of dates from end of last month - remainingDays
        // Set xAXES Strings to MM/dd strings for each date
        // For each Date in list, get emissions
        // For all Journeys, compare dates. If on date, add to day's TypeTotal
        // For all Utilities, if date falls between start and end date, add dailyUsage to day's Utilities
        // Add line point for each lineType for that day (x-value)
    }
}
