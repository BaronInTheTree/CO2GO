package ca.cmpt276.carbonTracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.sasha.carbontracker.R;

import java.util.List;

public class EditJourneyActivity extends AppCompatActivity {

    CarbonModel modelInstance = CarbonModel.getInstance();
    int selectedJourneyIndex;
    Transportation selectedTransport;
    Route selectedRoute;
    int selectedYear;
    int selectedMonth;
    int selectedDay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_journey);
        Intent callingIntent = getIntent();
        selectedJourneyIndex = callingIntent.getIntExtra("Index", 0);
        selectedYear = 1970;
        selectedMonth = 1;

        setupSelectTransportSpinner();
        setupSelectRouteSpinner();
        setupSelectYearSpinner();
        setupSelectMonthSpinner();
        setupSelectDaySpinner();
        setupEditJourneyButton();
        setupBackButton();
    }

    private void setupSelectTransportSpinner() {
        final Spinner transportSpinner = (Spinner) findViewById(R.id.spinnerSelectTransport);
        final List<String> transportOptions = modelInstance.getTransportationOptions();

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,
                R.layout.support_simple_spinner_dropdown_item,
                transportOptions);
        spinnerArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        transportSpinner.setAdapter(spinnerArrayAdapter);

        transportSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (transportOptions.get(position).equals("Walk/Bike")) {
                    selectedTransport = new WalkBike();
                }
                else if (transportOptions.get(position).equals("Bus")) {
                    selectedTransport = new Bus();
                }
                else if (transportOptions.get(position).equals("Skytrain")) {
                    selectedTransport = new Skytrain();
                }
                else {
                    selectedTransport = modelInstance.getCarCollection().getCarAtIndex(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setupSelectRouteSpinner() {
        final Spinner routeSpinner = (Spinner) findViewById(R.id.spinnerSelectRoute);

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,
                R.layout.support_simple_spinner_dropdown_item,
                modelInstance.getRouteOptions());
        spinnerArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        routeSpinner.setAdapter(spinnerArrayAdapter);

        routeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedRoute = modelInstance.getRouteCollection().getRouteAtIndex(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setupSelectYearSpinner() {
        final Spinner yearSpinner = (Spinner) findViewById(R.id.spinnerSelectYear);

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,
                R.layout.support_simple_spinner_dropdown_item,
                modelInstance.getDateHandler().getYearList());
        spinnerArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        yearSpinner.setAdapter(spinnerArrayAdapter);

        yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedYear = Integer.parseInt
                        (modelInstance.getDateHandler().getYearList().get(position));
                setupSelectDaySpinner();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setupSelectMonthSpinner() {
        final Spinner monthSpinner = (Spinner) findViewById(R.id.spinnerSelectMonth);

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,
                R.layout.support_simple_spinner_dropdown_item,
                modelInstance.getDateHandler().getMonthList());
        spinnerArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        monthSpinner.setAdapter(spinnerArrayAdapter);

        monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedMonth = Integer.parseInt
                        (modelInstance.getDateHandler().getMonthList().get(position));
                setupSelectDaySpinner();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setupSelectDaySpinner() {
        final Spinner daySpinner = (Spinner) findViewById(R.id.spinnerSelectDay);
        modelInstance.getDateHandler().initializeDayList(selectedYear, selectedMonth);

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,
                R.layout.support_simple_spinner_dropdown_item,
                modelInstance.getDateHandler().getDayList());
        spinnerArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        daySpinner.setAdapter(spinnerArrayAdapter);

        daySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedDay = Integer.parseInt
                        (modelInstance.getDateHandler().getDayList().get(position));
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setupEditJourneyButton() {
        Button editJourney = (Button) findViewById(R.id.buttonEditJourney);
        editJourney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modelInstance.getJourneyCollection().getJourneyAtIndex(selectedJourneyIndex).
                        setTransport(selectedTransport);
                modelInstance.getJourneyCollection().getJourneyAtIndex(selectedJourneyIndex).
                        setRoute(selectedRoute);
                modelInstance.getJourneyCollection().getJourneyAtIndex(selectedJourneyIndex).
                        setDate(selectedYear, selectedMonth, selectedDay);
                startActivity(new Intent(EditJourneyActivity.this, JourneyListActivity.class));
                finish();

            }
        });
    }

    private void setupBackButton() {
        Button back = (Button) findViewById(R.id.buttonBack_EditJourney);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditJourneyActivity.this, JourneyListActivity.class));
                finish();
            }
        });
    }


}
