package ca.cmpt276.carbonTracker.UI;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.sasha.carbontracker.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ca.cmpt276.carbonTracker.Internal_Logic.Car;
import ca.cmpt276.carbonTracker.Internal_Logic.CarbonModel;
import ca.cmpt276.carbonTracker.Internal_Logic.DateHandler;
import ca.cmpt276.carbonTracker.Internal_Logic.DayData;
import ca.cmpt276.carbonTracker.Internal_Logic.Journey;

public class MonthlyEmissionGraphActivity extends AppCompatActivity {

    private LineChart lineChart;
    CarbonModel model = CarbonModel.getInstance();

    ArrayList<Integer> colorList = new ArrayList<>();
    int currentColor = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly_emission_graph);

        lineChart = (LineChart) findViewById(R.id.lineChartMonthly);

        XAxis xAxis = lineChart.getXAxis();

        YAxis yAxis = lineChart.getAxisLeft();

        colorList.add(Color.RED);
        colorList.add(Color.BLUE);
        colorList.add(Color.GREEN);
        colorList.add(Color.CYAN);
        colorList.add(Color.MAGENTA);

        ArrayList<String> xAXES = new ArrayList<>();
        ArrayList<ArrayList> yAXES = new ArrayList<>();

        ArrayList<DayData> dayDataList = DayData.getDayDataWithinInterval
                (DateHandler.getDateLastMonth(new Date()), new Date());

        System.out.println("TST 1");
        for (DayData dayData : dayDataList) {
            for (Car car : model.getCarCollection().getCarCollection()) {
                System.out.println("Date: " + dayData.getDate() + ", Car: " + car.getNickname() + ", CO2: " + dayData.getCarEmissions_KM(car));
            }

        }

        ArrayList<Entry> electricity = new ArrayList<>();
        ArrayList<Entry> naturalGas = new ArrayList<>();
        ArrayList<Entry> bus = new ArrayList<>();
        ArrayList<Entry> skytrain = new ArrayList<>();

        ArrayList<ArrayList> vehicleEntryList = new ArrayList<>();
        ArrayList<String> vehicleNameList = new ArrayList<>();

        for (int i = 0; i < 28; i++) {
            electricity.add(new Entry(i, dayDataList.get(i).getElectricityEmissions()));
            naturalGas.add(new Entry(i, dayDataList.get(i).getNaturalGasEmissions()));
            bus.add(new Entry(i, dayDataList.get(i).
                    getTransportTypeEmissions_KM(Journey.Type.BUS)));
            skytrain.add(new Entry(i, dayDataList.get(i).
                    getTransportTypeEmissions_KM(Journey.Type.SKYTRAIN)));
        }

        for (Car car : model.getCarCollection().getCarCollection()) {
            ArrayList<Entry> vehicle = new ArrayList<>();

            for (int i = 0; i < 28; i++) {
                vehicle.add(new Entry(i, dayDataList.get(i).getCarEmissions_KM(car)));
            }
            System.out.println("TST 2");
            if (vehicle.size() > 0) {
                vehicleEntryList.add(vehicle);
                vehicleNameList.add(car.getNickname());
                System.out.println("TST 3");
            }
            vehicleEntryList.add(vehicle);
            vehicleNameList.add(car.getNickname());
        }

        xAXES.add("Electricity");
        xAXES.add("Natural Gas");
        xAXES.add("Bus");
        xAXES.add("Skytrain");
        for (String name : vehicleNameList) {
            xAXES.add(name);
        }

        String[] xAXES_Array = new String[xAXES.size()];
        for (int i = 0; i < xAXES_Array.length; i++) {
            xAXES_Array[i] = xAXES.get(i);
        }

        yAXES.add(electricity);
        yAXES.add(naturalGas);
        yAXES.add(bus);
        yAXES.add(skytrain);
        for (ArrayList entryList : vehicleEntryList) {
            yAXES.add(entryList);
        }

        ArrayList<ILineDataSet> lineDataSetList = new ArrayList<>();

        for (int i = 0; i < yAXES.size(); i++) {
            LineDataSet lineDataSet = new LineDataSet(yAXES.get(i), xAXES.get(i));
            lineDataSet.setDrawCircles(false);
            lineDataSet.setColor(getRandomColor());
            lineDataSetList.add(lineDataSet);
        }

        lineChart.setData(new LineData(lineDataSetList));
        lineChart.setVisibleXRange(28, 28);

    }

    private int getRandomColor() {
        int colorCode = colorList.get(currentColor);
        currentColor++;
        if (currentColor >= colorList.size()) {
            currentColor = 0;
        }
        return colorCode;
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
