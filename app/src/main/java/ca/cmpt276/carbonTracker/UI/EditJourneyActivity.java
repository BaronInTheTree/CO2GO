package ca.cmpt276.carbonTracker.UI;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sasha.carbontracker.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ca.cmpt276.carbonTracker.AlternateUI.AddCarActivity;
import ca.cmpt276.carbonTracker.AlternateUI.AddRouteActivity;
import ca.cmpt276.carbonTracker.AlternateUI.AddUtilityActivity;
import ca.cmpt276.carbonTracker.Internal_Logic.*;

/**
 * EditJourney enables the user to change the Transportation, Route, and Date of any selected
 * Journey.
 */

public class EditJourneyActivity extends AppCompatActivity {
    int selectedJourneyIndex;
    private CarbonModel model = CarbonModel.getInstance();
    private Date currentDate;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    private Route selectedRoute;
    private Transportation selectedTransportation;
    private Journey selectedJourney;
    private Journey newJourney;
    private enum TransportType{WALKBIKE, CAR, BUS, SKYTRAIN};
    private TransportType transportType;
    private SkytrainDB skytrainDB = new SkytrainDB();
    private ActionMenuView amvMenu;
    private List<String> emptyList;
    private int departureStationIndex;
    private int arrivalStationIndex;
    private List<Double> lineDistances;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_journey);

        Intent callingIntent = getIntent();
        selectedJourneyIndex = callingIntent.getIntExtra("Index", 0);

        System.out.println("TST 20.1: Index = " + selectedJourneyIndex);

        selectedJourney = model.getJourneyCollection().getJourneyAtIndex(selectedJourneyIndex);
        transportType = getTransportType();
        currentDate = selectedJourney.getDateTime();

        isCarCollectionEmpty();
        isRouteCollectionEmpty();

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
        setupConfirmButtons();
        setupCancelButton();
    }

    private TransportType getTransportType() {
        Journey journey = model.getJourneyCollection().getJourneyAtIndex(selectedJourneyIndex);
        switch(journey.getType()) {
            case WALK_BIKE: {
                return TransportType.WALKBIKE;
            }
            case CAR: {
                return TransportType.CAR;
            }
            case BUS: {
                return TransportType.BUS;
            }
            case SKYTRAIN: {
                return TransportType.SKYTRAIN;
            }
        }
        return null;
    }


    private void registerAsObserver() {
        CalendarDialogAddEditJourney.addObserver(new CalendarObserver() {
            @Override
            public void updateGraphs() {
                currentDate = CalendarDialogAddEditJourney.selectedDate;
                setupDateText();
            }
        });
    }

    private boolean isCarCollectionEmpty() {
        if (model.getCarCollection().getListSize() <= 0) {
            selectedTransportation = new WalkBike();
            selectedTransportation.setNickname("No Vehicles Available");
            return true;
        }
        else {
            selectedTransportation = selectedJourney.getTransportation();
            return false;
        }
    }

    private boolean isRouteCollectionEmpty() {
        if (model.getRouteCollection().getListSize() <= 0) {
            selectedRoute = new Route("No Routes Available", 0, 0);
            return true;
        }
        else {
            selectedRoute = selectedJourney.getRoute();
            return false;
        }
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
                CalendarDialogAddEditJourney dialog = new CalendarDialogAddEditJourney();
                dialog.show(manager,getResources().getString(R.string.calendarModeDialog));
            }
        });
    }

    private void setupWalkButton() {
        final Button selected = (Button) findViewById(R.id.buttonWalkMode);
        if (transportType.equals(TransportType.WALKBIKE)) {
            selected.setBackgroundResource(R.drawable.button_selected_red_round);
        }
        selected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected.setBackgroundResource(R.drawable.button_selected_red_round);

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
        if (transportType.equals(TransportType.WALKBIKE)) {
            selected.setBackgroundResource(R.drawable.button_selected_red_round);
        }
        selected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected.setBackgroundResource(R.drawable.button_selected_red_round);

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
        if (transportType.equals(TransportType.CAR)) {
            selected.setBackgroundResource(R.drawable.button_selected_red_round);
        }
        selected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected.setBackgroundResource(R.drawable.button_selected_red_round);

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
        if (transportType.equals(TransportType.BUS)) {
            selected.setBackgroundResource(R.drawable.button_selected_red_round);
        }
        selected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected.setBackgroundResource(R.drawable.button_selected_red_round);

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
        if (transportType.equals(TransportType.SKYTRAIN)) {
            selected.setBackgroundResource(R.drawable.button_selected_red_round);
        }
        selected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected.setBackgroundResource(R.drawable.button_selected_red_round);

                transportType = TransportType.SKYTRAIN;
                setupTransportModeText("Skytrain");

                Button btn1 = (Button) findViewById(R.id.buttonWalkMode);
                btn1.setBackgroundResource(R.drawable.button_default_brown_round);
                Button btn2 = (Button) findViewById(R.id.buttonBikeMode);
                btn2.setBackgroundResource(R.drawable.button_default_brown_round);
                Button btn3 = (Button) findViewById(R.id.buttonBusMode);
                btn3.setBackgroundResource(R.drawable.button_default_brown_round);
                Button btn4 = (Button) findViewById(R.id.buttonCarMode);
                btn4.setBackgroundResource(R.drawable.button_default_brown_round);

                setupSpinnerOne(skytrainDB.getLineList(), "Select Skytrain Line");
                setupSpinnerTwo(skytrainDB.getExpoLineStations(), "Select Departure Station", true);
                setupSpinnerThree(skytrainDB.getExpoLineStations(), "Select Arrival Station", true, 1);

                setupSummaryText();
            }
        });
    }

    private class MyArrayAdapter<T> extends ArrayAdapter<T>
    {
        List<Integer> iconIDs = IconCollection.iconIDs;
        public MyArrayAdapter(Context context, int resource, int textViewResourceId, List<T> objects) {
            super(context, resource, textViewResourceId, objects);
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = super.getView(position, convertView, parent);
            ImageView imageView = (ImageView) itemView.findViewById(R.id.vehicleIconEntryAddJourney);
//            imageView.setBackgroundResource(vehicleIcons.get(position));
            imageView.setImageResource(model.getCarCollection().getCarCollection().get(position).getIconID());
            System.out.println("TST 10.1: ID = " + iconIDs.get(position));
            return itemView;
        }
    }

    private void setupSpinnerOne(final List<String> list, String title) {
        final Spinner spinner = (Spinner) findViewById(R.id.spinnerOne);
        TextView textView = (TextView) findViewById(R.id.textViewOne);
        spinner.setVisibility(View.INVISIBLE);
        spinner.setClickable(false);

        if (isCarCollectionEmpty() && transportType.equals(TransportType.CAR)) {
            textView.setText("Add Vehicle");
            textView.setTextSize(18);
            textView.setBackgroundResource(R.drawable.button_default_green);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(EditJourneyActivity.this, AddCarActivity.class));
                    finish();
                }
            });
        }
        else if (isRouteCollectionEmpty() && !transportType.equals(TransportType.CAR)
                && !transportType.equals(TransportType.SKYTRAIN)) {
            textView.setText("Add Route");
            textView.setTextSize(18);
            textView.setBackgroundResource(R.drawable.button_default_green);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(EditJourneyActivity.this, AddRouteActivity.class));
                    finish();
                }
            });
        }
        else {
            spinner.setVisibility(View.VISIBLE);
            spinner.setClickable(true);
            textView.setText(title);
            textView.setTextSize(14);
            textView.setBackgroundResource(R.drawable.button_transparent);
            textView.setOnClickListener(null);
        }

        if (transportType.equals(TransportType.CAR)) {
            ArrayAdapter<String> spinnerArrayAdapter = new MyArrayAdapter<String>(this,
                    R.layout.icon_spinner_row_addjourney_layout,
                    R.id.vehicleIconNameAddJourney,
                    list);
            spinnerArrayAdapter.setDropDownViewResource(R.layout.icon_spinner_row_addjourney_layout);
            spinner.setAdapter(spinnerArrayAdapter);
            // if nickname == nickname of car in collection, get index
            // set index to that
            int index = 0;
            for (int i = 0; i < model.getCarCollection().getCarCollection().size(); i++) {
                if (model.getCarCollection().getCarAtIndex(i).getNickname().equals(selectedTransportation.getNickname())) {
                    index = i;
                }
            }
            System.out.println("TST 20.2: Car Index = " + index);
            spinner.setSelection(index);
        }
        else {
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,
                    R.layout.support_simple_spinner_dropdown_item,
                    list);
            spinnerArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
            spinner.setAdapter(spinnerArrayAdapter);

            int index = 0;
            for (int i = 0; i < model.getRouteCollection().getRouteCollection().size(); i++) {
                if (selectedRoute.getName().equals
                        (model.getRouteCollection().getRouteAtIndex(i).getName())) {
                    index = i;
                }
            }
            spinner.setSelection(index);
        }


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
                    if (!isCarCollectionEmpty()){
                        selectedTransportation = model.getCarCollection().getCarAtIndex(position);
                    }
                    setupSummaryText();
                }
                else {
                    if (!isRouteCollectionEmpty()) {
                        selectedRoute = model.getRouteCollection().getRouteAtIndex(position);
                    }
                    setupSummaryText();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
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

            if (isRouteCollectionEmpty() && transportType.equals(TransportType.CAR)) {
                spinner.setVisibility(View.INVISIBLE);
                spinner.setClickable(false);
                textView.setText("Add Route");
                textView.setTextSize(18);
                textView.setBackgroundResource(R.drawable.button_default_green);
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(EditJourneyActivity.this, AddRouteActivity.class));
                        finish();
                    }
                });
            }
            else {
                int index = 0;
                for (int i = 0; i < model.getRouteCollection().getRouteCollection().size(); i++) {
                    if (selectedRoute.getName().equals
                            (model.getRouteCollection().getRouteAtIndex(i).getName())) {
                        index = i;
                    }
                }
                spinner.setSelection(index);
                spinner.setVisibility(View.VISIBLE);
                spinner.setClickable(true);
                textView.setText(title);
                textView.setTextSize(14);
                textView.setBackgroundResource(R.drawable.button_transparent);
                textView.setOnClickListener(null);
            }
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
                    if (!isRouteCollectionEmpty()) {
                        selectedRoute = model.getRouteCollection().getRouteAtIndex(position);
                    }
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
        getMenuInflater().inflate(R.menu.toolbar_actions_addjourney, amvMenu.getMenu());
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
                    SaveData.saveTreeUnit(EditJourneyActivity.this);
                    setupSummaryText();
                } else {
                    CarbonModel.getInstance().getTreeUnit().setTreeUnitStatus(false);
                    SaveData.saveTreeUnit(EditJourneyActivity.this);
                    setupSummaryText();
                }
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
                Intent intent = new Intent(EditJourneyActivity.this, AddCarActivity.class);
                intent.putExtra("Caller", "AddJourney");
                startActivity(intent);
                finish();
                return true;
            }
            case R.id.action_addRoute: {
                Intent intent = new Intent(EditJourneyActivity.this, AddRouteActivity.class);
                intent.putExtra("Caller", "AddJourney");
                startActivity(intent);
                finish();
                return true;
            }
            case R.id.action_addUtility: {
                Intent intent = new Intent(EditJourneyActivity.this, AddUtilityActivity.class);
                intent.putExtra("Caller", "AddJourney");
                startActivity(intent);
                finish();
                return true;
            }
            case android.R.id.home: {
                Intent intent = new Intent(EditJourneyActivity.this, JourneyListActivity.class);
                startActivity(intent);
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupSummaryText() {
        newJourney = createJourney();

        TextView distance = (TextView) findViewById(R.id.textViewSummaryDistance);
        distance.setText("Distance:   " + selectedRoute.getTotalDistanceKM() + " km");

        TextView emissions = (TextView) findViewById(R.id.textViewSummaryEmissions);
        emissions.setText(("Emissions: " + String.format("%.2f",
                (model.getTreeUnit().getUnitValue(newJourney.getEmissionsKM())))
                + " " + model.getTreeUnit().getUnitTypeList()));
    }

    private void setupConfirmButtons() {
        Button confirm = (Button) findViewById(R.id.buttonConfirm);
        setupConfirmListeners(confirm, false);
        Button confirmFav = (Button) findViewById(R.id.buttonConfirmAddFavorites);
        setupConfirmListeners(confirmFav, true);
    }

    private void setupConfirmListeners(Button button, final boolean favorite) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean validInput = false;
                switch(transportType) {
                    case WALKBIKE: {
                        if (!isRouteCollectionEmpty()) {
                            validInput = true;
                        }
                        break;
                    }
                    case CAR: {
                        if (!isCarCollectionEmpty() && !isRouteCollectionEmpty()) {
                            validInput = true;
                        }
                        break;
                    }
                    case BUS: {
                        if (!isRouteCollectionEmpty()) {
                            validInput = true;
                        }
                        break;
                    }
                    case SKYTRAIN: {
                        validInput = true;
                        break;
                    }
                }
                if (validInput) {
                    selectedJourney.setTransport(newJourney.getTransportation());
                    selectedJourney.setRoute(newJourney.getRoute());
                    selectedJourney.setDate(newJourney.getDateTime());
                    if (favorite) {
                        model.getFavouriteJourneyCollection().addJourney(selectedJourney);
                    }
                    SaveData.saveJourneys(EditJourneyActivity.this);
                    startActivity(new Intent(EditJourneyActivity.this, JourneyListActivity.class));
                }
                else {
                    if (isCarCollectionEmpty()) {
                        Toast.makeText(EditJourneyActivity.this,
                                "Please add and select a Vehicle", Toast.LENGTH_LONG).show();
                    }
                    else if (isRouteCollectionEmpty()) {
                        Toast.makeText(EditJourneyActivity.this,
                                "Please add and select a Route", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    private void setupCancelButton() {
        Button cancel = (Button) findViewById(R.id.buttonCancel_AddJourney);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditJourneyActivity.this, JourneyListActivity.class));
                finish();
            }
        });
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

    @Override
    public void onBackPressed() {
        startActivity(new Intent(EditJourneyActivity.this, JourneyListActivity.class));
        finish();
    }

}
