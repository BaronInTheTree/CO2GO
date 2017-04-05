package ca.cmpt276.carbonTracker.AlternateUI;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.sasha.carbontracker.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ca.cmpt276.carbonTracker.Internal_Logic.Bus;
import ca.cmpt276.carbonTracker.Internal_Logic.CalendarObserver;
import ca.cmpt276.carbonTracker.Internal_Logic.CarbonModel;
import ca.cmpt276.carbonTracker.Internal_Logic.Journey;
import ca.cmpt276.carbonTracker.Internal_Logic.Route;
import ca.cmpt276.carbonTracker.Internal_Logic.Skytrain;
import ca.cmpt276.carbonTracker.Internal_Logic.SkytrainDB;
import ca.cmpt276.carbonTracker.Internal_Logic.Transportation;
import ca.cmpt276.carbonTracker.Internal_Logic.WalkBike;
import ca.cmpt276.carbonTracker.UI.AddCarActivity;
import ca.cmpt276.carbonTracker.UI.AddRouteActivity;
import ca.cmpt276.carbonTracker.UI.AddUtilityActivity;
import ca.cmpt276.carbonTracker.UI.CalendarDialog;

import static ca.cmpt276.carbonTracker.AlternateUI.MainMenuActivity_Alternate.gPerKG;

public class AddJourneyActivity_Alternate extends AppCompatActivity {

    private CarbonModel model = CarbonModel.getInstance();
    private Date currentDate = new Date();
    private SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    private Route selectedRoute = model.getRouteCollection().getRouteAtIndex(0);
    private Transportation selectedTransportation = model.getCarCollection().getCarAtIndex(0);
    private Journey selectedJourney;
    private enum TransportType{WALKBIKE, CAR, BUS, SKYTRAIN};
    private TransportType transportType = TransportType.CAR;
    private SkytrainDB skytrainDB = new SkytrainDB();
    private ActionMenuView amvMenu;
    private List<String> emptyList;
    private int departureStationIndex;
    private int arrivalStationIndex;
    private List<Double> lineDistances;
    private String unit = "kg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_journey_alternate);
        setupWalkButton();
        setupBikeButton();
        setupCarButton();
        setupBusButton();
        setupSkytrainButton();
        registerAsObserver();
        setupDateText();
        setupTransportModeText("Vehicle");

        emptyList = new ArrayList<>();
        emptyList.add("");

        setupSpinnerOne(model.getCarCollection().getUICollection(), "Select Vehicle");
        setupSpinnerTwo(model.getRouteCollection().getUICollection(), "Select Route", true);
        setupSpinnerThree(getEmptyList(), "", false, 0);
        setupActionBar();
        setupSummaryText();
    }

    private void registerAsObserver() {
        CalendarDialog.addObserver(new CalendarObserver() {
            @Override
            public void updateGraphs() {
                currentDate = CalendarDialog.selectedDate;
                setupDateText();
            }
        });
    }

    private void setupTransportModeText(String mode) {
        TextView modeText = (TextView) findViewById(R.id.textViewDisplayModeSelected);
        modeText.setText(mode);
    }

    private void setupDateText() {
        TextView dateText = (TextView) findViewById(R.id.textViewJourneyDate);
        String date = dateFormat.format(currentDate);
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

    private void setupWalkButton() {
        final Button walk = (Button) findViewById(R.id.buttonWalkMode);
        walk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                walk.setBackgroundResource(R.drawable.button_selected_red);

                transportType = TransportType.WALKBIKE;
                setupTransportModeText("Walking");
                selectedTransportation = new WalkBike();

                Button bike = (Button) findViewById(R.id.buttonBikeMode);
                bike.setBackgroundResource(R.drawable.button_default_brown_round);
                Button car = (Button) findViewById(R.id.buttonCarMode);
                car.setBackgroundResource(R.drawable.button_default_brown_round);
                Button bus = (Button) findViewById(R.id.buttonBusMode);
                bus.setBackgroundResource(R.drawable.button_default_brown_round);
                Button skytrain = (Button) findViewById(R.id.buttonSkytrainMode);
                skytrain.setBackgroundResource(R.drawable.button_default_brown_round);

                setupSpinnerOne(model.getRouteCollection().getUICollection(), "Select Route");
                setupSpinnerTwo(getEmptyList(), "", false);
                setupSpinnerThree(getEmptyList(), "", false, 0);

                setupSummaryText();
            }
        });
    }

    private void setupBikeButton() {
        final Button selected = (Button) findViewById(R.id.buttonBikeMode);
        selected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected.setBackgroundResource(R.drawable.button_selected_red);

                transportType = TransportType.WALKBIKE;
                setupTransportModeText("Biking");
                selectedTransportation = new WalkBike();

                Button btn1 = (Button) findViewById(R.id.buttonWalkMode);
                btn1.setBackgroundResource(R.drawable.button_default_brown_round);
                Button btn2 = (Button) findViewById(R.id.buttonCarMode);
                btn2.setBackgroundResource(R.drawable.button_default_brown_round);
                Button btn3 = (Button) findViewById(R.id.buttonBusMode);
                btn3.setBackgroundResource(R.drawable.button_default_brown_round);
                Button btn4 = (Button) findViewById(R.id.buttonSkytrainMode);
                btn4.setBackgroundResource(R.drawable.button_default_brown_round);

                setupSpinnerOne(model.getRouteCollection().getUICollection(), "Select Route");
                setupSpinnerTwo(getEmptyList(), "", false);
                setupSpinnerThree(getEmptyList(), "", false, 0);

                setupSummaryText();
            }
        });
    }

    private void setupCarButton() {
        final Button selected = (Button) findViewById(R.id.buttonCarMode);
        selected.setBackgroundResource(R.drawable.button_selected_red);
        selected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected.setBackgroundResource(R.drawable.button_selected_red);

                transportType = TransportType.CAR;
                setupTransportModeText("Vehicle");

                Button btn1 = (Button) findViewById(R.id.buttonWalkMode);
                btn1.setBackgroundResource(R.drawable.button_default_brown_round);
                Button btn2 = (Button) findViewById(R.id.buttonBikeMode);
                btn2.setBackgroundResource(R.drawable.button_default_brown_round);
                Button btn3 = (Button) findViewById(R.id.buttonBusMode);
                btn3.setBackgroundResource(R.drawable.button_default_brown_round);
                Button btn4 = (Button) findViewById(R.id.buttonSkytrainMode);
                btn4.setBackgroundResource(R.drawable.button_default_brown_round);

                setupSpinnerOne(model.getCarCollection().getUICollection(), "Select Vehicle");
                setupSpinnerTwo(model.getRouteCollection().getUICollection(), "Select Route", true);
                setupSpinnerThree(getEmptyList(), "", false, 0);

                setupSummaryText();
            }
        });
    }

    private void setupBusButton() {
        final Button selected = (Button) findViewById(R.id.buttonBusMode);
        selected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected.setBackgroundResource(R.drawable.button_selected_red);

                transportType = TransportType.BUS;
                setupTransportModeText("Bus");
                selectedTransportation = new Bus();

                Button btn1 = (Button) findViewById(R.id.buttonWalkMode);
                btn1.setBackgroundResource(R.drawable.button_default_brown_round);
                Button btn2 = (Button) findViewById(R.id.buttonBikeMode);
                btn2.setBackgroundResource(R.drawable.button_default_brown_round);
                Button btn3 = (Button) findViewById(R.id.buttonCarMode);
                btn3.setBackgroundResource(R.drawable.button_default_brown_round);
                Button btn4 = (Button) findViewById(R.id.buttonSkytrainMode);
                btn4.setBackgroundResource(R.drawable.button_default_brown_round);

                setupSpinnerOne(model.getRouteCollection().getUICollection(), "Select Route");
                setupSpinnerTwo(getEmptyList(), "", false);
                setupSpinnerThree(getEmptyList(), "", false, 0);

                setupSummaryText();
            }
        });
    }

    private void setupSkytrainButton() {
        final Button selected = (Button) findViewById(R.id.buttonSkytrainMode);
        selected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected.setBackgroundResource(R.drawable.button_selected_red);

                transportType = TransportType.SKYTRAIN;
                setupTransportModeText("Skytrain");

                Button btn1 = (Button) findViewById(R.id.buttonWalkMode);
                btn1.setBackgroundResource(R.drawable.button_default_brown);
                Button btn2 = (Button) findViewById(R.id.buttonBikeMode);
                btn2.setBackgroundResource(R.drawable.button_default_brown);
                Button btn3 = (Button) findViewById(R.id.buttonBusMode);
                btn3.setBackgroundResource(R.drawable.button_default_brown);
                Button btn4 = (Button) findViewById(R.id.buttonCarMode);
                btn4.setBackgroundResource(R.drawable.button_default_brown);

                setupSpinnerOne(skytrainDB.getLineList(), "Select Skytrain Line");
                setupSpinnerTwo(skytrainDB.getExpoLineStations(), "Select Departure Station", true);
                setupSpinnerThree(skytrainDB.getExpoLineStations(), "Select Arrival Station", true, 1);

                setupSummaryText();
            }
        });
    }

    private void setupSpinnerOne(final List<String> list, String title) {
        final Spinner spinner = (Spinner) findViewById(R.id.spinnerOne);

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,
                R.layout.support_simple_spinner_dropdown_item,
                list);
        spinnerArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerArrayAdapter);
        spinner.setSelection(0);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (transportType.equals(TransportType.SKYTRAIN)) {
                    if (list.get(position).equals("Expo Line")) {
                        setupSpinnerTwo(skytrainDB.getExpoLineStations(), "Select Departure Station", true);
                        setupSpinnerThree(skytrainDB.getExpoLineStations(), "Select Arrival Station", true, 1);
                        lineDistances = skytrainDB.getExpoLineDistances();
                    }
                    else if (list.get(position).equals("Millenium Line")) {
                        setupSpinnerTwo(skytrainDB.getMilleniumLineStations(), "Select Departure Station", true);
                        setupSpinnerThree(skytrainDB.getMilleniumLineStations(), "Select Arrival Station", true, 1);
                        lineDistances = skytrainDB.getMilleniumLineDistances();
                    }
                    else if (list.get(position).equals("Canada Line")) {
                        setupSpinnerTwo(skytrainDB.getCanadaLineStations(), "Select Departure Station", true);
                        setupSpinnerThree(skytrainDB.getCanadaLineStations(), "Select Arrival Station", true, 1);
                        lineDistances = skytrainDB.getCanadaLineDistances();
                    }
                    selectedTransportation = new Skytrain();
                }
                else if (transportType.equals(TransportType.CAR)) {
                    selectedTransportation = model.getCarCollection().getCarAtIndex(position);
                    setupSummaryText();
                }
                else {
                    selectedRoute = model.getRouteCollection().getRouteAtIndex(position);
                    setupSummaryText();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        TextView textView = (TextView) findViewById(R.id.textViewOne);
        textView.setText(title);
    }

    private void setupSpinnerTwo(final List<String> list, String title, boolean visible) {
        final Spinner spinner = (Spinner) findViewById(R.id.spinnerTwo);
        TextView textView = (TextView) findViewById(R.id.textViewTwo);

        if (visible) {
            spinner.setVisibility(View.VISIBLE);
            spinner.setClickable(true);
            textView.setVisibility(View.VISIBLE);
            textView.setClickable(true);

            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,
                    R.layout.support_simple_spinner_dropdown_item,
                    list);
            spinnerArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
            spinner.setAdapter(spinnerArrayAdapter);
            spinner.setSelection(0);

            textView.setText(title);
            departureStationIndex = 0;
        }

        else {
            spinner.setVisibility(View.INVISIBLE);
            spinner.setClickable(false);
            textView.setVisibility(View.INVISIBLE);
            textView.setClickable(false);

            textView.setText("");
        }

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (transportType.equals(TransportType.SKYTRAIN)) {
                    departureStationIndex = position;
                    selectedRoute = new Route("Skytrain", skytrainDB.getDistanceStations_KM
                            (departureStationIndex, arrivalStationIndex, lineDistances), 0);
                    setupSummaryText();
                }
                else if (transportType.equals(TransportType.CAR)) {
                    selectedRoute = model.getRouteCollection().getRouteAtIndex(position);
                    setupSummaryText();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setupSpinnerThree(List<String> list, String title, boolean visible, int index) {
        final Spinner spinner = (Spinner) findViewById(R.id.spinnerThree);
        TextView textView = (TextView) findViewById(R.id.textViewThree);

        if (visible) {
            spinner.setVisibility(View.VISIBLE);
            spinner.setClickable(true);
            textView.setVisibility(View.VISIBLE);
            textView.setClickable(true);

            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,
                    R.layout.support_simple_spinner_dropdown_item,
                    list);
            spinnerArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
            spinner.setAdapter(spinnerArrayAdapter);
            spinner.setSelection(index);

            textView.setText(title);
            arrivalStationIndex = index;
        }

        else {
            spinner.setVisibility(View.INVISIBLE);
            spinner.setClickable(false);
            textView.setVisibility(View.INVISIBLE);
            textView.setClickable(false);

            textView.setText("");
        }

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (transportType.equals(TransportType.SKYTRAIN)) {
                    arrivalStationIndex = position;
                    selectedRoute = new Route("Skytrain", skytrainDB.getDistanceStations_KM
                            (departureStationIndex, arrivalStationIndex, lineDistances), 0);
                    setupSummaryText();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setupActionBar() {
        // Inflate your custom layout
        Toolbar toolBar = (Toolbar) findViewById(R.id.addJourney_toolBar);
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
                startActivity(new Intent(AddJourneyActivity_Alternate.this, AddCarActivity.class));
                finish();
            }
            case R.id.action_addRoute: {
                startActivity(new Intent(AddJourneyActivity_Alternate.this, AddRouteActivity.class));
                finish();
            }
            case R.id.action_addUtility: {
                startActivity(new Intent(AddJourneyActivity_Alternate.this, AddUtilityActivity.class));
                finish();
            }
            case R.id.action_addJourney: {
                startActivity(new Intent(AddJourneyActivity_Alternate.this, AddJourneyActivity_Alternate.class));
                finish();
            }
            case android.R.id.home: {
                finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupSummaryText() {
        selectedJourney = createJourney();

        TextView distance = (TextView) findViewById(R.id.textViewSummaryDistance);
        distance.setText("Distance:  " + selectedRoute.getTotalDistanceKM() + " km");

        TextView emissions = (TextView) findViewById(R.id.textViewSummaryEmissions);
        emissions.setText(("Emissions: " + String.format("%.3f", (selectedJourney.getEmissionsKM() / gPerKG)) + " " + unit));
    }

    public Journey createJourney() {
        if (transportType.equals(TransportType.CAR)){
            Journey journey = new Journey(selectedTransportation, selectedRoute, currentDate,
                    Journey.Type.CAR);
            return journey;
        } else if (transportType.equals(TransportType.WALKBIKE)) {
            Journey journey = new Journey(selectedTransportation, selectedRoute, currentDate,
                    Journey.Type.WALK_BIKE);
            return journey;
        } else if (transportType.equals(TransportType.BUS)) {
            Journey journey = new Journey(selectedTransportation, selectedRoute, currentDate,
                    Journey.Type.BUS);
            return journey;
        } else {
            Journey journey = new Journey(selectedTransportation, selectedRoute, currentDate,
                    Journey.Type.SKYTRAIN);
            return journey;
        }
    }

    private List<String> getEmptyList() {
        return emptyList;
    }

}
