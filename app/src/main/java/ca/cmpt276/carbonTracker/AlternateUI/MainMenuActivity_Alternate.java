package ca.cmpt276.carbonTracker.AlternateUI;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.provider.Contacts;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.lang.reflect.Array;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ca.cmpt276.carbonTracker.Internal_Logic.Car;
import ca.cmpt276.carbonTracker.Internal_Logic.CarbonModel;
import ca.cmpt276.carbonTracker.Internal_Logic.DateHandler;
import ca.cmpt276.carbonTracker.Internal_Logic.DayData;
import ca.cmpt276.carbonTracker.Internal_Logic.Journey;
import ca.cmpt276.carbonTracker.UI.CalendarDialog;
import ca.cmpt276.carbonTracker.UI.MainMenuActivity;

public class MainMenuActivity_Alternate extends AppCompatActivity {

    private Date currentDate = new Date();
    CarbonModel model = CarbonModel.getInstance();
    private final String tableLabel = "CO2 Emissions for " + DateHandler.getMonthOfDate(currentDate)
            + "/" + DateHandler.getDayOfDate(currentDate)
            + "/" + DateHandler.getYearOfDate(currentDate);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu__alternate);
        setupVehiclePieChart();
        setupDateText();
    }

    private void setupDateText() {
        TextView dateText = (TextView) findViewById(R.id.textViewSelectedDateRange);
        String date = new SimpleDateFormat("MM/dd/yyyy").format(CalendarDialog.selectedDate);
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

    private void setupVehiclePieChart() {
        PieChart chart = (PieChart) findViewById(R.id.mainMenu_pieChart);

        List<PieEntry> pieEntries = new ArrayList<>();

        // if single day selected
        DayData dayData = DayData.getDayDataAtDate(CalendarDialog.selectedDate);
        ArrayList<String> labelList = new ArrayList<>();

        pieEntries.add(new PieEntry(dayData.getElectricityEmissions()));
        labelList.add("Electricity: " + String.format("%.2f", (dayData.getElectricityEmissions() / 1000)) + "kg\n");
        pieEntries.add(new PieEntry(dayData.getNaturalGasEmissions()));
        labelList.add("Natural Gas: " + String.format("%.2f", (dayData.getNaturalGasEmissions() / 1000)) + "kg");
        pieEntries.add(new PieEntry(dayData.getTransportTypeEmissions_KM(Journey.Type.BUS)));
        labelList.add("Bus: " + String.format("%.2f", (dayData.getTransportTypeEmissions_KM(Journey.Type.BUS)) / 1000) + "kg\n");
        pieEntries.add(new PieEntry(dayData.getTransportTypeEmissions_KM(Journey.Type.SKYTRAIN)));
        labelList.add("Skytrain: " + String.format("%.2f", (dayData.getTransportTypeEmissions_KM(Journey.Type.SKYTRAIN) / 1000)) + "kg");

        for (Car car : model.getCarCollection().getCarCollection()) {
            pieEntries.add(new PieEntry(dayData.getCarEmissions_KM(car)));
            labelList.add(car.getNickname() + ": " + String.format("%.2f", (dayData.getCarEmissions_KM(car) / 1000)) + "kg");
        }

        LegendEntry[] legendEntryList = new LegendEntry[labelList.size()];
        String[] labels = new String[labelList.size()];
        float[] intervals = new float[]{2, 2};

        ArrayList<Integer> colorList = new ArrayList<>();
        for (int i = 0; i < ColorTemplate.VORDIPLOM_COLORS.length; i++) {
            colorList.add(ColorTemplate.VORDIPLOM_COLORS[i]);
        }

        int colorIndex = 0;
        for (int i = 0; i < legendEntryList.length; i++) {
            legendEntryList[i] = new LegendEntry(labelList.get(i), Legend.LegendForm.CIRCLE, 10, 10, new DashPathEffect(intervals, 1), colorList.get(colorIndex));
            colorIndex++;
            if (colorIndex >= colorList.size()) {
                colorIndex = 0;
            }
        }

        PieDataSet dataSet = new PieDataSet(pieEntries, tableLabel);
        dataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
        dataSet.setSliceSpace(2);
        dataSet.setValueTextSize(12);
        PieData data = new PieData(dataSet);

        chart.setData(data);
        chart.setDescription(null);
        chart.setHoleRadius(25f);
        chart.setTransparentCircleAlpha(0);
        chart.setRotationEnabled(false);
//        chart.animateY(1500);

        Legend legend = chart.getLegend();
        legend.setWordWrapEnabled(true);
        legend.setXEntrySpace(10f);
        legend.setTextSize(14);
        legend.setForm(Legend.LegendForm.CIRCLE);

        legend.setCustom(legendEntryList);

        System.out.println("TST 6.0: chart height = " + chart.getLayoutParams().height);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams
                (chart.getLayoutParams().width, chart.getLayoutParams().height + (100 * (labelList.size() - 4)));
        chart.setLayoutParams(params);

        System.out.println("TST 6.1: chart height = " + chart.getLayoutParams().height);

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

