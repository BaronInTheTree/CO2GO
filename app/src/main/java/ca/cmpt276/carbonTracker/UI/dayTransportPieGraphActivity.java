package ca.cmpt276.carbonTracker.UI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.sasha.carbontracker.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import ca.cmpt276.carbonTracker.Internal_Logic.DayData;

public class dayTransportPieGraphActivity extends AppCompatActivity {



    int year;
    int month;
    int day;

    Date today = new GregorianCalendar(year,month,day).getTime();
    DayData todayData = new DayData(today);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_transport_pie_graph);

        setupPieChart();
    }

    private void setupPieChart() {

    }
}
