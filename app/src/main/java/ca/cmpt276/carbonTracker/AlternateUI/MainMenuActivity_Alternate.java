package ca.cmpt276.carbonTracker.AlternateUI;

import android.content.Intent;
import android.graphics.DashPathEffect;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sasha.carbontracker.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;

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
import ca.cmpt276.carbonTracker.UI.AddCarActivity;
import ca.cmpt276.carbonTracker.UI.AddRouteActivity;
import ca.cmpt276.carbonTracker.UI.AddUtilityActivity;
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
    private SimpleDateFormat chartDateFormat = new SimpleDateFormat("MM/dd/yyyy");
    PieChart chart;
    int chartHeight;
    private ActionMenuView amvMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu__alternate);
        graphType = GraphType.VEHICLE;
        dateType = DateType.SINGLE;
        chart = (PieChart) findViewById(R.id.mainMenu_pieChart);
        chartHeight = chart.getLayoutParams().height;
        registerAsObserver();
        setupChart();
        setupSingleDateText();
        setupSingleDayButton();
        setupMonthRangeButton();
        setupYearRangeButton();
        setupRouteFilterButton();
        setupVehicleFilterButton();
        setupActionBar();
    }

    private void setupSingleDayButton() {
        final Button singleDay = (Button) findViewById(R.id.buttonSingleDay);
        singleDay.setBackgroundResource(R.drawable.button_selected_red);
        singleDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateType = DateType.SINGLE;
                setupChart();
                singleDay.setBackgroundResource(R.drawable.button_selected_red);

                Button monthRange = (Button) findViewById(R.id.button28Days);
                monthRange.setBackgroundResource(R.drawable.button_default_brown);
                Button yearRange = (Button) findViewById(R.id.button365Days);
                yearRange.setBackgroundResource(R.drawable.button_default_brown);
            }
        });
    }

    private void setupMonthRangeButton() {
        final Button monthRange = (Button) findViewById(R.id.button28Days);
        monthRange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateType = DateType.MONTH;
                setupChart();
                monthRange.setBackgroundResource(R.drawable.button_selected_red);

                Button singleDay = (Button) findViewById(R.id.buttonSingleDay);
                singleDay.setBackgroundResource(R.drawable.button_default_brown);
                Button yearRange = (Button) findViewById(R.id.button365Days);
                yearRange.setBackgroundResource(R.drawable.button_default_brown);
            }
        });
    }

    private void setupYearRangeButton() {
        final Button yearRange = (Button) findViewById(R.id.button365Days);
        yearRange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateType = DateType.YEAR;
                setupChart();
                yearRange.setBackgroundResource(R.drawable.button_selected_red);

                Button singleDay = (Button) findViewById(R.id.buttonSingleDay);
                singleDay.setBackgroundResource(R.drawable.button_default_brown);
                Button monthRange = (Button) findViewById(R.id.button28Days);
                monthRange.setBackgroundResource(R.drawable.button_default_brown);
            }
        });
    }

    private void setupRouteFilterButton() {
        final Button routeFilter = (Button) findViewById(R.id.buttonFilterByRoute);
        routeFilter.setBackgroundResource(R.drawable.button_default_brown);
        routeFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                graphType = GraphType.ROUTE;
                setupChart();
                routeFilter.setBackgroundResource(R.drawable.button_selected_red);

                Button vehicleFilter = (Button) findViewById(R.id.buttonFilterByVehicle);
                vehicleFilter.setBackgroundResource(R.drawable.button_default_brown);
            }
        });
    }

    private void setupVehicleFilterButton() {
        final Button vehicleFilter = (Button) findViewById(R.id.buttonFilterByVehicle);
        vehicleFilter.setBackgroundResource(R.drawable.button_selected_red);
        vehicleFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                graphType = GraphType.VEHICLE;
                setupChart();
                vehicleFilter.setBackgroundResource(R.drawable.button_selected_red);

                Button routeFilter = (Button) findViewById(R.id.buttonFilterByRoute);
                routeFilter.setBackgroundResource(R.drawable.button_default_brown);
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
        dateText.setText(endDate);
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
                            + chartDateFormat.format(currentDate) + ",\nBy Route (kg)");
                }
                else {
                    setupVehiclePieChart();
                    chartTitle.setText("CO2 Emissions for:\n" + chartDateFormat.format(originDate) + " - "
                            + chartDateFormat.format(currentDate) + ",\nBy Vehicle (kg)");
                }
                break;
            }

            case YEAR: {
                originDate = DateHandler.getDateLastYear(currentDate);
                setupDateRangeText();
                if (graphType.equals(GraphType.ROUTE)) {
                    setupRoutePieChart();
                    chartTitle.setText("CO2 Emissions for:\n" + chartDateFormat.format(originDate) + " - "
                            + chartDateFormat.format(currentDate) + ",\nBy Route (kg)");
                }
                else {
                    setupVehiclePieChart();
                    chartTitle.setText("CO2 Emissions for:\n" + chartDateFormat.format(originDate) + " - "
                            + chartDateFormat.format(currentDate) + ",\nBy Vehicle (kg)");
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
        chart = (PieChart) findViewById(R.id.mainMenu_pieChart);
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
        chart = (PieChart) findViewById(R.id.mainMenu_pieChart);
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

    private void setupChartUI(PieChart chart, final float totalEmissions, List<PieEntry> pieEntries, ArrayList<String> labelList) {
        ArrayList<LegendEntry> legendList = new ArrayList<>();
        LegendEntry[] legendEntryList = new LegendEntry[labelList.size()];
        float[] intervals = new float[]{2, 2};

        ArrayList<Integer> colorList = new ArrayList<>();
        List<Integer> colorTemplate = ColorTemplate.createColors(setupColorList());
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
        dataSet.setSliceSpace(0);
        dataSet.setValueTextSize(12);
        dataSet.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                if (value / (totalEmissions / gPerKG) < 0.05) {
                    return "";
                }
                else return "" + String.format("%.0f",value);
            }
        });

        PieData data = new PieData(dataSet);

        chart.setData(data);

        String str = new Float(totalEmissions / gPerKG).toString();
        int numDigits = str.indexOf(".");
        int counter = 0;
        for (int i = numDigits; i > 4; i--) {
            counter++;
        }
        chart.setHoleRadius(25f + (2 * counter));
        chart.setDescription(null);
        chart.setTransparentCircleAlpha(0);
        chart.setRotationEnabled(false);
        chart.setDrawEntryLabels(false);
        chart.setCenterText("" + String.format("%.0f", (totalEmissions / gPerKG)) + "kg");
        chart.setCenterTextSize(10);

        chart.setDrawSliceText(false);
//        chart.animateY(1500);

        Legend legend = chart.getLegend();
        legend.setWordWrapEnabled(true);
        legend.setTextSize(12);
        legend.setXEntrySpace(40);
        legend.setMaxSizePercent(0.99f);
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
        legend.setFormSize(14);

        legend.setCustom(legendList);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams
                (chart.getLayoutParams().width, chartHeight + (10 * (labelList.size() - 2)));
        chart.setLayoutParams(params);

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

    private void setupActionBar() {
        // Inflate your custom layout
        Toolbar toolBar = (Toolbar) findViewById(R.id.mainMenu_toolBar);
        amvMenu = (ActionMenuView) toolBar.findViewById(R.id.amvMenu);
        amvMenu.setOnMenuItemClickListener(new ActionMenuView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                return onOptionsItemSelected(menuItem);
            }
        });
        setSupportActionBar(toolBar);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_actions, amvMenu.getMenu());
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch(id) {
            case R.id.action_settings: {
                return true;
            }
            case R.id.action_addVehicle: {
                startActivity(new Intent(MainMenuActivity_Alternate.this, AddCarActivity.class));
                finish();
            }
            case R.id.action_addRoute: {
                startActivity(new Intent(MainMenuActivity_Alternate.this, AddRouteActivity.class));
                finish();
            }
            case R.id.action_addUtility: {
                startActivity(new Intent(MainMenuActivity_Alternate.this, AddUtilityActivity.class));
                finish();
            }
            case R.id.action_addJourney: {
                startActivity(new Intent(MainMenuActivity_Alternate.this, AddJourneyActivity_Alternate.class));
                finish();
            }
            case android.R.id.home: {
                finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }


    private int[] setupColorList() {
        int[] colorList = new int[]{
                R.color.earthDarkGreen,
                R.color.earthDarkRed,
                R.color.earthGold,
                R.color.earthTeal,
                R.color.earthOrange,
                R.color.earthLime,
                R.color.earthBrown,
                R.color.earthDarkTeal,
                R.color.earthDarkOrange
        };
        for (int color : colorList) {
            System.out.println("TST 6.1: Color: " + color);
        }
        return colorList;
    }
}

