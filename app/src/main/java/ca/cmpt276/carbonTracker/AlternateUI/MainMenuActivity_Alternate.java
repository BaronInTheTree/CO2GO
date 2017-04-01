package ca.cmpt276.carbonTracker.AlternateUI;

import android.graphics.DashPathEffect;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.sasha.carbontracker.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ca.cmpt276.carbonTracker.Internal_Logic.CalendarObserver;
import ca.cmpt276.carbonTracker.Internal_Logic.Car;
import ca.cmpt276.carbonTracker.Internal_Logic.CarbonModel;
import ca.cmpt276.carbonTracker.Internal_Logic.DateHandler;
import ca.cmpt276.carbonTracker.Internal_Logic.DayData;
import ca.cmpt276.carbonTracker.Internal_Logic.Route;
import ca.cmpt276.carbonTracker.UI.CalendarDialog;

public class MainMenuActivity_Alternate extends AppCompatActivity {

    public static final int gPerKG = 1000;
    private Date currentDate = new Date();
    private Date originDate = new Date();
    CarbonModel model = CarbonModel.getInstance();
    private String tableLabel;
    private enum GraphType {ROUTE, VEHICLE}
    private enum DateType {SINGLE, MONTH, YEAR}
    private GraphType graphType;
    private DateType dateType;
    private SimpleDateFormat chartDateFormat = new SimpleDateFormat("MM/dd/yy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu__alternate);
        graphType = GraphType.VEHICLE;
        dateType = DateType.SINGLE;
        registerAsObserver();
        setupChart();
        setupSingleDateText();
        setupSingleDayButton();
        setupMonthRangeButton();
        setupYearRangeButton();
        setupRouteFilterButton();
        setupVehicleFilterButton();
    }

    private void setupSingleDayButton() {
        Button singleDay = (Button) findViewById(R.id.buttonSingleDay);
        singleDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateType = DateType.SINGLE;
                setupChart();
            }
        });
    }

    private void setupMonthRangeButton() {
        Button monthRange = (Button) findViewById(R.id.button28Days);
        monthRange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateType = DateType.MONTH;
                setupChart();
            }
        });
    }

    private void setupYearRangeButton() {
        Button yearRange = (Button) findViewById(R.id.button365Days);
        yearRange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateType = DateType.YEAR;
                setupChart();
            }
        });
    }

    private void setupRouteFilterButton() {
        Button routeFilter = (Button) findViewById(R.id.buttonFilterByRoute);
        routeFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                graphType = GraphType.ROUTE;
                setupChart();
            }
        });
    }

    private void setupVehicleFilterButton() {
        Button vehicleFilter = (Button) findViewById(R.id.buttonFilterByVehicle);
        vehicleFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                graphType = GraphType.VEHICLE;
                setupChart();
            }
        });
    }

    private void setupSingleDateText() {
        TextView dateText = (TextView) findViewById(R.id.textViewSelectedDateRange);
        String date = chartDateFormat.format(currentDate);
        TextView dateTextHint = (TextView) findViewById(R.id.textViewDateTextHint);
        dateTextHint.setText("Click to Select Date");
        dateText.setText(date);
        dateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getSupportFragmentManager();
                CalendarDialog dialog = new CalendarDialog();
                dialog.show(manager,getResources().getString(R.string.calendarModeDialog));
            }
        });
    }

    private void setupDateRangeText() {
        TextView dateText = (TextView) findViewById(R.id.textViewSelectedDateRange);
        String originDate = chartDateFormat.format(this.originDate);
        String endDate = chartDateFormat.format(currentDate);
        TextView dateTextHint = (TextView) findViewById(R.id.textViewDateTextHint);
        dateTextHint.setText("Click to Select Ending Date in Range");
        dateText.setText(originDate + " - " + endDate);
        dateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getSupportFragmentManager();
                CalendarDialog dialog = new CalendarDialog();
                dialog.show(manager,getResources().getString(R.string.calendarModeDialog));
            }
        });
    }

    private void setupChart() {
        TextView chartTitle = (TextView) findViewById(R.id.textViewChartTitle);

        switch(dateType) {
            case SINGLE: {
                originDate = currentDate;
                setupSingleDateText();
                if (graphType.equals(GraphType.ROUTE)) {
                    setupRoutePieChart();
                    chartTitle.setText("CO2 Emissions for:\n" + chartDateFormat.format(currentDate)
                            + ", By Route (kg)");
                }
                else {
                    setupVehiclePieChart();
                    chartTitle.setText("CO2 Emissions for:\n" + chartDateFormat.format(currentDate)
                            + ", By Vehicle (kg)");
                }
                break;
            }
            case MONTH: {
                originDate = DateHandler.getDateLastMonth(currentDate);
                setupDateRangeText();
                if (graphType.equals(GraphType.ROUTE)) {
                    setupRoutePieChart();
                    chartTitle.setText("CO2 Emissions for:\n" + chartDateFormat.format(originDate) + " - "
                            + chartDateFormat.format(currentDate) + ", By Route (kg)");
                }
                else {
                    setupVehiclePieChart();
                    chartTitle.setText("CO2 Emissions for:\n" + chartDateFormat.format(originDate) + " - "
                            + chartDateFormat.format(currentDate) + ", By Vehicle (kg)");
                }
                break;
            }

            case YEAR: {
                originDate = DateHandler.getDateLastYear(currentDate);
                setupDateRangeText();
                if (graphType.equals(GraphType.ROUTE)) {
                    setupRoutePieChart();
                    chartTitle.setText("CO2 Emissions for:\n" + chartDateFormat.format(originDate) + " - "
                            + chartDateFormat.format(currentDate) + ", By Route (kg)");
                }
                else {
                    setupVehiclePieChart();
                    chartTitle.setText("CO2 Emissions for:\n" + chartDateFormat.format(originDate) + " - "
                            + chartDateFormat.format(currentDate) + ", By Vehicle (kg)");
                }
                break;
            }
        }
    }

    private float addEntryToList(List<PieEntry> pieEntries, ArrayList<String> labelList, String label, float emissions, float totalEmissions) {
        if (emissions > 0) {
            pieEntries.add(new PieEntry(emissions / gPerKG));
            labelList.add(label +  ": " + String.format("%.2f", emissions / gPerKG));
            totalEmissions += emissions;
        }
        return totalEmissions;
    }


    private void setupRoutePieChart() {
        PieChart chart = (PieChart) findViewById(R.id.mainMenu_pieChart);
        float totalEmissions = 0;

        List<PieEntry> pieEntries = new ArrayList<>();

        ArrayList<DayData> dayDataList = DayData.getDayDataWithinInterval(originDate, currentDate);
        ArrayList<String> labelList = new ArrayList<>();

        for (Route route : model.getRouteCollection().getRouteCollection()) {
            totalEmissions = addEntryToList(pieEntries, labelList, route.getName(), DayData.getTotalRouteEmissions(dayDataList, route), totalEmissions);
        }

        setupChartUI(chart, totalEmissions, pieEntries, labelList);



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
    }

    private void setupVehiclePieChart() {
        PieChart chart = (PieChart) findViewById(R.id.mainMenu_pieChart);
        float totalEmissions = 0;

        List<PieEntry> pieEntries = new ArrayList<>();

        ArrayList<DayData> dayDataList = DayData.getDayDataWithinInterval(originDate, currentDate);
        ArrayList<String> labelList = new ArrayList<>();

        totalEmissions = addEntryToList(pieEntries, labelList, "Electricity", DayData.getTotalElectricityEmissions(dayDataList), totalEmissions);
        totalEmissions = addEntryToList(pieEntries, labelList, "Natural Gas", DayData.getTotalGasEmissions(dayDataList), totalEmissions);
        totalEmissions = addEntryToList(pieEntries, labelList, "Bus", DayData.getTotalBusEmissions(dayDataList), totalEmissions);
        totalEmissions = addEntryToList(pieEntries, labelList, "Skytrain", DayData.getTotalSkytrainEmissions(dayDataList), totalEmissions);

        for (Car car : model.getCarCollection().getCarCollection()) {
            totalEmissions = addEntryToList(pieEntries, labelList, car.getNickname(), DayData.getTotalCarEmissions(dayDataList, car), totalEmissions);
        }

        setupChartUI(chart, totalEmissions, pieEntries, labelList);

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
    }

    private void setupChartUI(PieChart chart, float totalEmissions, List<PieEntry> pieEntries, ArrayList<String> labelList) {
        ArrayList<LegendEntry> legendList = new ArrayList<>();
        LegendEntry[] legendEntryList = new LegendEntry[labelList.size()];
        float[] intervals = new float[]{2, 2};

        ArrayList<Integer> colorList = new ArrayList<>();
        for (int i = 0; i < ColorTemplate.VORDIPLOM_COLORS.length; i++) {
            colorList.add(ColorTemplate.VORDIPLOM_COLORS[i]);
        }

        int colorIndex = 0;
        for (int i = 0; i < legendEntryList.length; i++) {
            legendEntryList[i] = new LegendEntry(labelList.get(i), Legend.LegendForm.CIRCLE, 10, 10, new DashPathEffect(intervals, 1), colorList.get(colorIndex));
            legendList.add(new LegendEntry(labelList.get(i), Legend.LegendForm.CIRCLE, 10, 10, new DashPathEffect(intervals, 1), colorList.get(colorIndex)));
            colorIndex++;
            if (colorIndex >= colorList.size()) {
                colorIndex = 0;
            }
        }

        PieDataSet dataSet = new PieDataSet(pieEntries, tableLabel);
        dataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
        dataSet.setSliceSpace(2);
        dataSet.setValueTextSize(10);
        PieData data = new PieData(dataSet);

        chart.setData(data);
        chart.setHoleRadius(25f);
        chart.setDescription(null);
        chart.setTransparentCircleAlpha(0);
        chart.setRotationEnabled(false);
        chart.setDrawEntryLabels(false);
        chart.setCenterText("" + String.format("%.0f", (totalEmissions / gPerKG)) + "kg");

        chart.setDrawSliceText(false);
//        chart.animateY(1500);

        Legend legend = chart.getLegend();
        legend.setWordWrapEnabled(true);
        legend.setTextSize(10);
        legend.setXEntrySpace(30);
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
        legend.setFormSize(14);

        legend.setCustom(legendList);

        /*LinearLayout.LayoutParams params = new LinearLayout.LayoutParams
                (chart.getLayoutParams().width, chart.getLayoutParams().height + (30 * (labelList.size() - 3)));
        chart.setLayoutParams(params);*/

        chart.notifyDataSetChanged();
        chart.invalidate();
    }

    private void registerAsObserver() {
        CalendarDialog.addObserver(new CalendarObserver() {
            @Override
            public void updateGraphs() {
                currentDate = CalendarDialog.selectedDate;
                if (dateType.equals(DateType.SINGLE)) {
                    setupSingleDateText();
                }
                else setupDateRangeText();
                setupChart();
            }
        });
    }
}

