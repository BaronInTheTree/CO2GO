package ca.cmpt276.carbonTracker.AlternateUI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.sasha.carbontracker.R;

import ca.cmpt276.carbonTracker.Internal_Logic.*;

/*
 * AddUtilityActivity allows the user to create a new utility by filling in all the data
 * from spinners and editTexts.
 * Stores data about the starting/ending dates and all necessary fields for an utility.
 */
public class AddUtilityActivity extends AppCompatActivity {

    private static final int INVALID_INPUT = 0;
    private int startYear;
    private int startMonth;
    private int startDay;
    private int endYear;
    private int endMonth;
    private int endDay;

    private CarbonModel model;
    private String nickname;
    private boolean naturalGas;
    private boolean electricity;
    private String startingDate;
    private String endingDate;
    private int usage;
    private int numPeople;

    private ActionMenuView amvMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_utility);

        model = CarbonModel.getInstance();

        // Spinner requesting fuel
        selectFuelSpinner();

        // Spinners for getting starting date
        setupStartYearSpinner();
        setupStartMonthSpinner();
        setupStartDaySpinner();

        // Spinners for getting ending date
        setupEndYearSpinner();
        setupEndMonthSpinner();
        setupEndDaySpinner();

        // Save and cancel buttons
        saveBillButton();
        cancelButton();

        setupActionBar();
    }


    private void selectFuelSpinner() {
        final Spinner selectResource = (Spinner) findViewById(R.id.spinnerSelectResource);

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this,
                R.layout.support_simple_spinner_dropdown_item,
                model.getUtilityFuel());

        spinnerArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        selectResource.setAdapter(spinnerArrayAdapter);

        selectResource.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String input = selectResource.getSelectedItem().toString();
                if (input.equals("Natural Gas")) {
                    naturalGas = true;
                    electricity = false;
                } else {
                    naturalGas = false;
                    electricity = true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void getNickName() {
        final EditText editText = (EditText) findViewById(R.id.addUtilityNickname);
        String text = editText.getText().toString();

        // Check if editText isn't empty
        if (!text.equals("")) {
            nickname = text;
        } else {
            nickname = null;
        }
    }

    private void getUsage() {
        final EditText editText = (EditText) findViewById(R.id.editText_Utility_Usage);
        String value = editText.getText().toString();

        // Check if editText is not empty
        if (!value.equals("")) {
            usage = Integer.parseInt(value);
        } else {
            usage = INVALID_INPUT;
        }
    }

    // Following 3 methods get starting date
    private void setupStartYearSpinner() {
        final Spinner yearSpinner = (Spinner) findViewById(R.id.spinnerAddBillSelectStartYear);

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,
                R.layout.support_simple_spinner_dropdown_item,
                model.getDateHandler().getYearList());
        spinnerArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        yearSpinner.setAdapter(spinnerArrayAdapter);

        yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                startYear = Integer.parseInt
                        (model.getDateHandler().getYearList().get(position));
                setupStartDaySpinner();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setupStartMonthSpinner() {
        final Spinner monthSpinner = (Spinner) findViewById(R.id.spinnerAddBillSelectStartMonth);

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,
                R.layout.support_simple_spinner_dropdown_item,
                model.getDateHandler().getMonthList());
        spinnerArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        monthSpinner.setAdapter(spinnerArrayAdapter);
        monthSpinner.setSelection(model.getDateHandler().getCurrentMonth() - 1);

        monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                startMonth = Integer.parseInt
                        (model.getDateHandler().getMonthList().get(position));
                setupStartDaySpinner();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setupStartDaySpinner() {
        final Spinner daySpinner = (Spinner) findViewById(R.id.spinnerAddBillSelectStartDay);
        model.getDateHandler().initializeDayList(startYear, startMonth);

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,
                R.layout.support_simple_spinner_dropdown_item,
                model.getDateHandler().getDayList());
        spinnerArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        daySpinner.setAdapter(spinnerArrayAdapter);
        daySpinner.setSelection(model.getDateHandler().getCurrentDay() - 1);

        daySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                startDay = Integer.parseInt
                        (model.getDateHandler().getDayList().get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    // Following 3 methods get ending date
    private void setupEndYearSpinner() {
        final Spinner yearSpinner = (Spinner) findViewById(R.id.spinnerAddBillSelectEndYear);

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,
                R.layout.support_simple_spinner_dropdown_item,
                model.getDateHandler().getYearList());
        spinnerArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        yearSpinner.setAdapter(spinnerArrayAdapter);

        yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                endYear = Integer.parseInt
                        (model.getDateHandler().getYearList().get(position));
                setupStartDaySpinner();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setupEndMonthSpinner() {
        final Spinner monthSpinner = (Spinner) findViewById(R.id.spinnerAddBillSelectEndMonth);

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,
                R.layout.support_simple_spinner_dropdown_item,
                model.getDateHandler().getMonthList());
        spinnerArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        monthSpinner.setAdapter(spinnerArrayAdapter);
        monthSpinner.setSelection(model.getDateHandler().getCurrentMonth() - 1);

        monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                endMonth = Integer.parseInt
                        (model.getDateHandler().getMonthList().get(position));
                setupStartDaySpinner();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setupEndDaySpinner() {
        final Spinner daySpinner = (Spinner) findViewById(R.id.spinnerAddBillSelectEndDay);
        model.getDateHandler().initializeDayList(startYear, startMonth);

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,
                R.layout.support_simple_spinner_dropdown_item,
                model.getDateHandler().getDayList());
        spinnerArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        daySpinner.setAdapter(spinnerArrayAdapter);
        daySpinner.setSelection(model.getDateHandler().getCurrentDay() - 1);

        daySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                endDay = Integer.parseInt
                        (model.getDateHandler().getDayList().get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    // Gets number of people in home
    private void getNumPeople() {
        final EditText editText = (EditText) findViewById(R.id.editText_Utility_Num_People);
        String value = editText.getText().toString();

        // Check if editText is not empty
        if (!value.equals("")) {
            numPeople = Integer.parseInt(value);
        } else {
            numPeople = INVALID_INPUT;
        }
    }

    private void saveBillButton() {
        Button saveBtn = (Button) findViewById(R.id.buttonAddBillSave);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getNickName();
                getUsage();
                getNumPeople();

                // If empty input display toast message
                if (invalidInput()) {
                    Toast.makeText(
                            AddUtilityActivity.this,
                            "Please fill out the form completely.",
                            Toast.LENGTH_SHORT).show();
                } else if (identicalDate() || negativeDates()) {
                    Toast.makeText(
                            AddUtilityActivity.this,
                            "Please check the dates. \n" +
                                    "Start date must be before end date.",
                            Toast.LENGTH_LONG).show();
                } else {
                    // Create a Utility
                    Utility utility = new Utility(nickname, naturalGas, electricity,
                            startingDate, endingDate, usage, numPeople);

                    // Add to utility collection
                    UtilityCollection collection = model.getUtilityCollection();
                    collection.addUtility(utility);

                    model.setUtilityCollection(collection);

                    // Return to utility list and close activity
                    Intent intent = new Intent(AddUtilityActivity.this, MainMenuActivity_Alternate.class);
                    SaveData.saveUtilities(AddUtilityActivity.this);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    // Checks if some fields are left blank
    private boolean invalidInput() {
        startingDate = startYear + "-" + startMonth + "-" + startDay;
        endingDate = endYear + "-" + endMonth + "-" + endDay;

        boolean emptyNickname = (nickname == null);
        boolean emptyDates = (startingDate.equals(null) || endingDate.equals(null));
        boolean emptyInput = (usage == INVALID_INPUT || numPeople == INVALID_INPUT);

        if (emptyNickname || emptyDates || emptyInput) {
            return true;
        } else {
            return false;
        }
    }

    // Checks if starting date is the same as ending date
    private boolean identicalDate() {
        if (startYear == endYear &&
                startMonth == endMonth &&
                startDay == endDay) {
            return true;
        } else {
            return false;
        }
    }

    // Checks if starting date is later than ending date
    private boolean negativeDates() {
        boolean negativeYear = (startYear > endYear);
        boolean negativeMonth = (startYear == endYear && startMonth > endMonth);
        boolean negativeDay = (startYear == endYear &&
                startMonth == endMonth &&
                startDay > endDay);

        if (negativeYear || negativeMonth || negativeDay) {
            return true;
        } else {
            return false;
        }
    }

    private void cancelButton() {
        Button cancelBtn = (Button) findViewById(R.id.buttonAddBillCancel);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Return to utility list and close activity
                Intent intent = new Intent(AddUtilityActivity.this, MainMenuActivity_Alternate.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void setupActionBar() {
        // Inflate your custom layout
        Toolbar toolBar = (Toolbar) findViewById(R.id.addUtility_toolBar);
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
        getMenuInflater().inflate(R.menu.toolbar_actions_addutility, amvMenu.getMenu());
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
                    SaveData.saveTreeUnit(AddUtilityActivity.this);
                } else {
                    CarbonModel.getInstance().getTreeUnit().setTreeUnitStatus(false);
                    SaveData.saveTreeUnit(AddUtilityActivity.this);
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
                Intent intent = new Intent(AddUtilityActivity.this, AddCarActivity.class);
                intent.putExtra("Caller", "AddUtility");
                startActivity(intent);
                finish();
                return true;
            }
            case R.id.action_addRoute: {
                Intent intent = new Intent(AddUtilityActivity.this, AddRouteActivity.class);
                intent.putExtra("Caller", "AddUtility");
                startActivity(intent);
                finish();
                return true;
            }
            case R.id.action_addJourney: {
                Intent intent = new Intent(AddUtilityActivity.this, AddJourneyActivity_Alternate.class);
                intent.putExtra("Caller", "AddUtility");
                startActivity(intent);
                finish();
                return true;
            }
            case android.R.id.home: {
                Intent intent = new Intent(AddUtilityActivity.this, MainMenuActivity_Alternate.class);
                intent.putExtra("Caller", "AddUtility");
                startActivity(intent);
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(AddUtilityActivity.this, MainMenuActivity_Alternate.class));
        finish();
    }
}