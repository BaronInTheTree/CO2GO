package ca.cmpt276.carbonTracker.AlternateUI;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import ca.cmpt276.carbonTracker.Internal_Logic.Journey;
import ca.cmpt276.carbonTracker.Internal_Logic.Route;
import ca.cmpt276.carbonTracker.Internal_Logic.SaveData;
import ca.cmpt276.carbonTracker.UI.CalendarDialogMainMenu;
import ca.cmpt276.carbonTracker.UI.JourneyListActivity;
import ca.cmpt276.carbonTracker.UI.SelectRouteActivity;
import ca.cmpt276.carbonTracker.UI.SelectTransportationActivity;
import ca.cmpt276.carbonTracker.UI.UtilityListActivity;

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
        setupTipsText();
        setupAddViewButtons();
        setupLineGraphButton();

        for (Journey journey : model.getJourneyCollection().getJourneys()) {
            System.out.println("TST 30.0: Journey Date = " + journey.getDateTime());
        }
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

                setupLineGraphButton();
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

                setupLineGraphButton();
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

                setupLineGraphButton();
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
                CalendarDialogMainMenu dialog = new CalendarDialogMainMenu();
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
                CalendarDialogMainMenu dialog = new CalendarDialogMainMenu();
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
                            + ", By Route (" + model.getTreeUnit().getUnitTypeList() + ")");
                }
                else {
                    setupVehiclePieChart();
                    chartTitle.setText("CO2 Emissions for:\n" + chartDateFormat.format(currentDate)
                            + ", By Vehicle (" + model.getTreeUnit().getUnitTypeList() + ")");
                }
                break;
            }
            case MONTH: {
                originDate = DateHandler.getDateLastMonth(currentDate);
                setupDateRangeText();
                if (graphType.equals(GraphType.ROUTE)) {
                    setupRoutePieChart();
                    chartTitle.setText("CO2 Emissions for:\n" + chartDateFormat.format(originDate) + " - "
                            + chartDateFormat.format(currentDate) + "\nLast 28 Days, By Route (" + model.getTreeUnit().getUnitTypeList() + ")");
                }
                else {
                    setupVehiclePieChart();
                    chartTitle.setText("CO2 Emissions for:\n" + chartDateFormat.format(originDate) + " - "
                            + chartDateFormat.format(currentDate) + "\nLast 28 Days, By Vehicle (" + model.getTreeUnit().getUnitTypeList() + ")");
                }
                break;
            }

            case YEAR: {
                originDate = DateHandler.getDateLastYear(currentDate);
                setupDateRangeText();
                if (graphType.equals(GraphType.ROUTE)) {
                    setupRoutePieChart();
                    chartTitle.setText("CO2 Emissions for:\n" + chartDateFormat.format(originDate) + " - "
                            + chartDateFormat.format(currentDate) + "\nLast 365 Days, By Route (" + model.getTreeUnit().getUnitTypeList() + ")");
                }
                else {
                    setupVehiclePieChart();
                    chartTitle.setText("CO2 Emissions for:\n" + chartDateFormat.format(originDate) + " - "
                            + chartDateFormat.format(currentDate) + "\nLast 365 Days, By Vehicle (" + model.getTreeUnit().getUnitTypeList() + ")");
                }
                break;
            }
        }
    }

    private float addEntryToList(List<PieEntry> pieEntries, ArrayList<String> labelList, String label, float emissions, float totalEmissions) {
        if (emissions > 0) {
            pieEntries.add(new PieEntry(emissions));
            labelList.add(label +  ": " + String.format("%.2f", emissions));
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
            totalEmissions = addEntryToList(pieEntries, labelList, route.getName(),
                    model.getTreeUnit().getUnitValueGraphs(DayData.getTotalRouteEmissions(dayDataList, route)),
                    totalEmissions);
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

        totalEmissions = addEntryToList(pieEntries, labelList, "Electricity",
                model.getTreeUnit().getUnitValueGraphs(DayData.getTotalElectricityEmissions(dayDataList)),
                totalEmissions);
        totalEmissions = addEntryToList(pieEntries, labelList, "Natural Gas",
                model.getTreeUnit().getUnitValueGraphs(DayData.getTotalGasEmissions(dayDataList)),
                totalEmissions);
        totalEmissions = addEntryToList(pieEntries, labelList, "Bus",
                model.getTreeUnit().getUnitValueGraphs(DayData.getTotalBusEmissions(dayDataList)),
                totalEmissions);
        totalEmissions = addEntryToList(pieEntries, labelList, "Skytrain",
                model.getTreeUnit().getUnitValueGraphs(DayData.getTotalSkytrainEmissions(dayDataList)),
                totalEmissions);

        for (Car car : model.getCarCollection().getCarCollection()) {
            totalEmissions = addEntryToList(pieEntries, labelList, car.getNickname(),
                    model.getTreeUnit().getUnitValueGraphs(DayData.getTotalCarEmissions(dayDataList, car)),
                    totalEmissions);
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
                if ((value / totalEmissions) < 0.05) {
                    return "";
                }
                else return "" + String.format("%.0f",value);
            }
        });

        PieData data = new PieData(dataSet);

        chart.setData(data);

        String str = new Float(totalEmissions).toString();
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
        chart.setCenterText("" + String.format("%.0f", totalEmissions)
                + model.getTreeUnit().getUnitTypeList());
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

    private void setupLineGraphButton() {
        Button lineGraph = (Button) findViewById(R.id.buttonLineGraph);
        lineGraph.setVisibility(View.INVISIBLE);
        lineGraph.setClickable(false);

        if (!dateType.equals(DateType.SINGLE)) {
            lineGraph.setVisibility(View.VISIBLE);
            lineGraph.setClickable(true);
        }

        lineGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuActivity_Alternate.this, LineGraphActivity.class);
                intent.putExtra("OriginDate", DateHandler.convertDateToString(originDate));
                intent.putExtra("CurrentDate", DateHandler.convertDateToString(currentDate));
                if (graphType.equals(GraphType.ROUTE)) {
                    intent.putExtra("Type", "Route");
                }
                else if (graphType.equals(GraphType.VEHICLE)) {
                    intent.putExtra("Type", "Vehicle");
                }
                switch(dateType) {
                    case MONTH: {
                        intent.putExtra("DateRange", 28);
                        break;
                    }
                    case YEAR: {
                        intent.putExtra("DateRange", 365);
                        break;
                    }
                }
                startActivity(intent);
                finish();
            }
        });
    }

    private void registerAsObserver() {
        CalendarDialogMainMenu.addObserver(new CalendarObserver() {
            @Override
            public void updateGraphs() {
                currentDate = CalendarDialogMainMenu.selectedDate;
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
        getMenuInflater().inflate(R.menu.toolbar_actions_mainmenu, amvMenu.getMenu());
        MenuItem itemSwitch = amvMenu.getMenu().findItem(R.id.toolbar_switch);
        itemSwitch.setActionView(R.layout.switch_layout);

        final SwitchCompat unitToggle = (SwitchCompat) amvMenu.getMenu().findItem(R.id.toolbar_switch).
                getActionView().findViewById(R.id.switchForActionBar);
        if (CarbonModel.getInstance().getTreeUnit().getTreeUnitStatus()) {
            unitToggle.setChecked(true);
        }
        unitToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    CarbonModel.getInstance().getTreeUnit().setTreeUnitStatus(true);
                    SaveData.saveTreeUnit(MainMenuActivity_Alternate.this);
                } else {
                    CarbonModel.getInstance().getTreeUnit().setTreeUnitStatus(false);
                    SaveData.saveTreeUnit(MainMenuActivity_Alternate.this);
                }
                setupChart();
            }
        });
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
                Intent intent = new Intent(MainMenuActivity_Alternate.this, AddCarActivity.class);
                intent.putExtra("Caller", "MainMenu");
                startActivity(intent);
                finish();
                return true;
            }
            case R.id.action_addRoute: {
                Intent intent = new Intent(MainMenuActivity_Alternate.this, AddRouteActivity.class);
                intent.putExtra("Caller", "MainMenu");
                startActivity(intent);
                finish();
                return true;
            }
            case R.id.action_addUtility: {
                Intent intent = new Intent(MainMenuActivity_Alternate.this, AddUtilityActivity.class);
                intent.putExtra("Caller", "MainMenu");
                startActivity(intent);
                finish();
                return true;
            }
            case R.id.action_addJourney: {
                Intent intent = new Intent(MainMenuActivity_Alternate.this, AddJourneyActivity_Alternate.class);
                intent.putExtra("Caller", "MainMenu");
                startActivity(intent);
                finish();
                return true;
            }
            case android.R.id.home: {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainMenuActivity_Alternate.this);
                builder.setTitle("Warning");
                builder.setMessage("Are you sure you want to quit the app?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                builder.setNegativeButton("No", null);
                builder.show();
                return true;
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

    private void setupAddViewButtons() {
        Button addJourney = (Button) findViewById(R.id.buttonAddJourney_MainMenu);
        Button viewJourney = (Button) findViewById(R.id.buttonViewJourneys_MainMenu);
        Button addUtility = (Button) findViewById(R.id.buttonAddUtility_MainMenu);
        Button viewUtility = (Button) findViewById(R.id.buttonViewUtilities_MainMenu);
        Button addVehicle = (Button) findViewById(R.id.buttonAddVehicle_MainMenu);
        Button viewVehicle = (Button) findViewById(R.id.buttonViewVehicles_MainMenu);
        Button addRoute = (Button) findViewById(R.id.buttonAddRoute_MainMenu);
        Button viewRoute = (Button) findViewById(R.id.buttonViewRoutes_MainMenu);

        setupButtonListener(addJourney, new Intent
                (MainMenuActivity_Alternate.this, AddJourneyActivity_Alternate.class));
        setupButtonListener(viewJourney, new Intent
                (MainMenuActivity_Alternate.this, JourneyListActivity.class));
        setupButtonListener(addUtility, new Intent
                (MainMenuActivity_Alternate.this, AddUtilityActivity.class));
        setupButtonListener(viewUtility, new Intent
                (MainMenuActivity_Alternate.this, UtilityListActivity.class));
        setupButtonListener(addVehicle, new Intent
                (MainMenuActivity_Alternate.this, AddCarActivity.class));
        setupButtonListener(viewVehicle, new Intent
                (MainMenuActivity_Alternate.this, SelectTransportationActivity.class));
        setupButtonListener(addRoute, new Intent
                (MainMenuActivity_Alternate.this, AddRouteActivity.class));
        setupButtonListener(viewRoute, new Intent
                (MainMenuActivity_Alternate.this, SelectRouteActivity.class));
    }

    private void setupButtonListener(Button button, final Intent intent) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("Caller", "MainMenu");
                startActivity(intent);
                finish();
            }
        });
    }

    private void setupTipsText() {
        String tip = "Tip:\n" + model.getTips().getGeneralTip(MainMenuActivity_Alternate.this, model.getSummary());
        SpannableString spannableString = new SpannableString(tip);
        spannableString.setSpan(new RelativeSizeSpan(1.5f), 0,5, 0); // set size
        spannableString.setSpan(new ForegroundColorSpan(Color.WHITE), 0, 5, 0);// set color

        Button tips = (Button) findViewById(R.id.buttonTipsMainMenu);
        tips.setText(spannableString);
    }

    @Override
    public void onBackPressed() {
        int backStackEntryCount = getSupportFragmentManager().getBackStackEntryCount();
        if (backStackEntryCount == 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainMenuActivity_Alternate.this);
            builder.setTitle("Warning");
            builder.setMessage("Are you sure you want to quit the app?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            builder.setNegativeButton("No", null);
            builder.show();
        } else {
            finish();
        }
    }
}

