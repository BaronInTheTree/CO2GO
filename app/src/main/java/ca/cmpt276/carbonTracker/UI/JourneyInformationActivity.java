package ca.cmpt276.carbonTracker.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sasha.carbontracker.R;

import ca.cmpt276.carbonTracker.Internal_Logic.CarbonModel;
import ca.cmpt276.carbonTracker.Internal_Logic.Journey;

import static ca.cmpt276.carbonTracker.Internal_Logic.JourneyCollection.maxEmission;

/**
 * JourneyInformationActivity displays a quick summary of the users current journey that they
 * have created.
 *
 * @author Team Teal
 */
public class JourneyInformationActivity extends AppCompatActivity {

    CarbonModel currentInstance = CarbonModel.getInstance();
    Journey currentJourney = currentInstance.createJourney();
    MonthYearSummary summary = currentInstance.getSummary();
    int selectedYear = currentJourney.getYearInt();
    int selectedMonth = currentJourney.getMonthInt();
    int selectedDay = currentJourney.getDayInt();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journey_information);

        setupInfo();
        setupButtons();
        setupSelectYearSpinner();
        setupSelectMonthSpinner();
        setupSelectDaySpinner();
    }

    private void setupInfo() {

        //TextView date = (TextView) findViewById(R.id.date_entry);
        //date.setText(currentJourney.getDateString());

        TextView vehicle = (TextView) findViewById(R.id.vehicle_entry);
        String vehicleName = currentJourney.getTransportation().getNickname();
        vehicle.setText(vehicleName);

        TextView route = (TextView) findViewById(R.id.route_entry);
        String routeName = currentJourney.getRoute().getName();
        route.setText(routeName);

        TextView distance = (TextView) findViewById(R.id.distance_entry);
        String distanceInfo = "" + currentJourney.getRoute().getTotalDistanceKM();
        distance.setText(distanceInfo);

        TextView emission = (TextView) findViewById(R.id.emission_entry);
        String emissionInfo = "" + currentJourney.getEmissionsKM();
        if (emissionInfo.length() > maxEmission) emissionInfo = emissionInfo.substring(0, maxEmission);
        emission.setText(emissionInfo);
    }

    private void setupButtons() {
        Button confirm_btn = (Button) findViewById(R.id.confirm);
        confirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentJourney.setDate(selectedYear, selectedMonth, selectedDay);
                currentInstance.addNewJourney(currentJourney);
                int type = currentJourney.getTransportType();
                summary.updateJourneys(currentInstance.getJourneyCollection());
                // create tip based on the type of transportation and the information within current Journey.
                String message = currentInstance.getTips().getJourneyTip(type, currentJourney,summary);
                Toast.makeText(JourneyInformationActivity.this, message, Toast.LENGTH_LONG).show();
                finish();
            }
        });

        Button cancel_btn = (Button) findViewById(R.id.cancel);
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(JourneyInformationActivity.this, SelectRouteActivity.class));
                finish();
            }
        });
    }

    private void setupSelectYearSpinner() {
        final Spinner yearSpinner = (Spinner) findViewById(R.id.spinnerYear);

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,
                R.layout.support_simple_spinner_dropdown_item,
                currentInstance.getDateHandler().getYearList());
        spinnerArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        yearSpinner.setAdapter(spinnerArrayAdapter);
        yearSpinner.setSelection(currentInstance.getDateHandler().
                MAX_YEAR - currentJourney.getYearInt());

        yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedYear = Integer.parseInt
                        (currentInstance.getDateHandler().getYearList().get(position));
                setupSelectDaySpinner();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setupSelectMonthSpinner() {
        final Spinner monthSpinner = (Spinner) findViewById(R.id.spinnerMonth);

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,
                R.layout.support_simple_spinner_dropdown_item,
                currentInstance.getDateHandler().getMonthList());
        spinnerArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        monthSpinner.setAdapter(spinnerArrayAdapter);
        monthSpinner.setSelection(currentJourney.getMonthInt() - 1);

        monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedMonth = Integer.parseInt
                        (currentInstance.getDateHandler().getMonthList().get(position));
                setupSelectDaySpinner();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setupSelectDaySpinner() {
        final Spinner daySpinner = (Spinner) findViewById(R.id.spinnerDay);
        currentInstance.getDateHandler().initializeDayList(selectedYear, selectedMonth);

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,
                R.layout.support_simple_spinner_dropdown_item,
                currentInstance.getDateHandler().getDayList());
        spinnerArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        daySpinner.setAdapter(spinnerArrayAdapter);
        daySpinner.setSelection(currentJourney.getDayInt() - 1);

        daySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedDay = Integer.parseInt
                        (currentInstance.getDateHandler().getDayList().get(position));
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

}