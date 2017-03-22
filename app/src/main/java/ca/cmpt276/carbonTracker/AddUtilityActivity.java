package ca.cmpt276.carbonTracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.sasha.carbontracker.R;

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
    }

    private void selectFuelSpinner() {
        final Spinner selectResource = (Spinner) findViewById(R.id.spinnerSelectResource);

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this,
                R.layout.support_simple_spinner_dropdown_item,
                model.getUtilityData());

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

    // Gets utility usage
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
                // Set day spinner
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
        monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                startMonth = Integer.parseInt
                        (model.getDateHandler().getMonthList().get(position));
                // Set day spinner
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
                // Set day spinner
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
        monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                endMonth = Integer.parseInt
                        (model.getDateHandler().getMonthList().get(position));
                // Set day spinner
                setupStartDaySpinner();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setupEndDaySpinner() {
        final Spinner daySpinner = (Spinner) findViewById(R.id.spinnerAddBillSelectEndDay);
        model.getDateHandler().initializeDayList(endYear, endMonth);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,
                R.layout.support_simple_spinner_dropdown_item,
                model.getDateHandler().getDayList());
        spinnerArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        daySpinner.setAdapter(spinnerArrayAdapter);
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
                } else {
                    // Create a Utility
                    Utility utility = new Utility(nickname, naturalGas, electricity,
                            startingDate, endingDate, usage, numPeople);

                    // Add to utility collection
                    UtilityCollection collection = model.getUtilityCollection();
                    collection.addUtility(utility);

                    model.setUtilityCollection(collection);

                    // Return to utility list and close activity
                    Intent intent = new Intent(AddUtilityActivity.this, UtilityListActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    // Until dates are not done this will crash the activity upon clicking save button
    private boolean invalidInput() {
        startingDate = startYear + "-" + startMonth + "-" + startDay;
        endingDate = endYear + "-" + endMonth + "-" + endDay;

        boolean emptyNickname = (nickname == null);
        boolean emptyDates = (startingDate.equals(null) || endingDate.equals(null));
        boolean emptyInput = (usage == INVALID_INPUT || numPeople == INVALID_INPUT);

        if (emptyNickname || emptyDates || emptyInput || negativeDates()) {
            return true;
        } else {
            return false;
        }
    }

    private boolean negativeDates() {
        if (startYear > endYear || startMonth > endMonth ||
                startMonth == endMonth && startDay > endDay) {
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
                Intent intent = new Intent(AddUtilityActivity.this, UtilityListActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}