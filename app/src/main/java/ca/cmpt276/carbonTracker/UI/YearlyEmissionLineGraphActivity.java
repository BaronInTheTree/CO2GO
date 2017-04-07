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
import java.util.Date;

import ca.cmpt276.carbonTracker.Internal_Logic.Car;
import ca.cmpt276.carbonTracker.Internal_Logic.CarbonModel;
import ca.cmpt276.carbonTracker.Internal_Logic.DateHandler;
import ca.cmpt276.carbonTracker.Internal_Logic.DayData;
import ca.cmpt276.carbonTracker.Internal_Logic.Journey;

public class YearlyEmissionLineGraphActivity extends AppCompatActivity {

    private LineChart lineChart;
    CarbonModel model = CarbonModel.getInstance();
    final int GRAMS_PER_KG = 1000;
    final int DATA_POINTS = 53;

    ArrayList<Integer> colorList = new ArrayList<>();
    int currentColor = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yearly_emission_line_graph);

        lineChart = (LineChart) findViewById(R.id.lineChartYearly);

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
                (DateHandler.getDateLastYear(new Date()), new Date());
        ArrayList<ArrayList> yearDataList = DayData.getDayDataPerWeekInYear(dayDataList);

        ArrayList<Entry> electricity = new ArrayList<>();
        ArrayList<Entry> naturalGas = new ArrayList<>();
        ArrayList<Entry> bus = new ArrayList<>();
        ArrayList<Entry> skytrain = new ArrayList<>();

        ArrayList<ArrayList> vehicleEntryList = new ArrayList<>();
        ArrayList<String> vehicleNameList = new ArrayList<>();


        for (int i = 0; i < DATA_POINTS; i++) {
            System.out.println("TST 3.1: Week = " + i + ", Elec CO2 = " + DayData.getTotalElectricityEmissions(yearDataList.get(i)));
            System.out.println("TST 3.2: Week = " + i + ", Gas CO2 = " + DayData.getTotalGasEmissions(yearDataList.get(i)));
            System.out.println("TST 3.3: Week = " + i + ", Bus CO2 = " + DayData.getTotalBusEmissions(yearDataList.get(i)));
            System.out.println("TST 3.4: Week = " + i + ", Skytrain CO2 = " + DayData.getTotalSkytrainEmissions(yearDataList.get(i)));
            electricity.add(new Entry(i, model.getTreeUnit().getUnitValueGraphs(
                    DayData.getTotalElectricityEmissions(yearDataList.get(i)) / GRAMS_PER_KG)));
            naturalGas.add(new Entry(i, model.getTreeUnit().getUnitValueGraphs(
                    DayData.getTotalGasEmissions(yearDataList.get(i)) / GRAMS_PER_KG)));
            bus.add(new Entry(i, model.getTreeUnit().getUnitValueGraphs(
                    DayData.getTotalBusEmissions(yearDataList.get(i)) / GRAMS_PER_KG)));
            skytrain.add(new Entry(i, model.getTreeUnit().getUnitValueGraphs(
                    DayData.getTotalSkytrainEmissions(yearDataList.get(i)) / GRAMS_PER_KG)));
        }

        for (Car car : model.getCarCollection().getCarCollection()) {
            ArrayList<Entry> vehicle = new ArrayList<>();

            for (int i = 0; i < DATA_POINTS; i++) {
                vehicle.add(new Entry(i, model.getTreeUnit().getUnitValueGraphs(
                        DayData.getTotalCarEmissions(yearDataList.get(i), car) / GRAMS_PER_KG)));
            }
            if (vehicle.size() > 0) {
                vehicleEntryList.add(vehicle);
                vehicleNameList.add(car.getNickname());
            }
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
        lineChart.setVisibleXRange(DATA_POINTS, DATA_POINTS);
    }

    private int getRandomColor() {
        int colorCode = colorList.get(currentColor);
        currentColor++;
        if (currentColor >= colorList.size()) {
            currentColor = 0;
        }
        return colorCode;
    }
}
