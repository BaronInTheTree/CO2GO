package ca.cmpt276.carbonTracker;

import android.content.Context;
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

public class EditUtilityActivity extends AppCompatActivity {
    private static final int INVALID_INPUT = 0;
    private int startYear;
    private int startMonth;
    private int startDay;
    private int endYear;
    private int endMonth;
    private int endDay;

    private CarbonModel model = CarbonModel.getInstance();;
    private String nickname;
    private boolean naturalGas;
    private boolean electricity;
    private String startingDate;
    private String endingDate;
    private int usage;
    private int numPeople;
    int selectedUtilityIndex;
    private Utility selectedUtility;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_utility);
        Intent callingIntent = getIntent();
        selectedUtilityIndex = callingIntent.getIntExtra("UtilityIndex", 0);
        selectedUtility = model.getUtilityCollection().getUtilityAtIndex(selectedUtilityIndex);

        setupUtilityNicknameText();

        // Spinner requesting fuel
        selectFuelSpinner();

        setupUsageText();

        // Spinners for getting starting date
        setupStartYearSpinner();
        setupStartMonthSpinner();
        setupStartDaySpinner();

        // Spinners for getting ending date
        setupEndYearSpinner();
        setupEndMonthSpinner();
        setupEndDaySpinner();

        setupNumberPeopleText();

        // Save and cancel buttons
        setupEditBillButton();
        setupCancelButton();
    }

    private void setupUtilityNicknameText() {
        EditText nickname = (EditText) findViewById(R.id.editUtilityNickname);
        nickname.setText(selectedUtility.getNickname());
    }

    //todo: modify for editing/saving

    private void selectFuelSpinner() {
        final Spinner selectResource = (Spinner) findViewById(R.id.spinnerSelectResource);

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this,
                R.layout.support_simple_spinner_dropdown_item,
                model.getUtilityFuel());

        spinnerArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        selectResource.setAdapter(spinnerArrayAdapter);
        if (selectedUtility.isNaturalGas()) {
            selectResource.setSelection(0);
        }
        else {
            selectResource.setSelection(1);
        }

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

    private void setupUsageText() {
        EditText usage = (EditText) findViewById(R.id.editTextUtilityUsage_EditUtility);
        String usageString = "" + selectedUtility.getUsage();
        usage.setText(usageString);
    }

    private void getNickName() {
        final EditText editText = (EditText) findViewById(R.id.editUtilityNickname);
        String text = editText.getText().toString();

        // Check if editText isn't empty
        if (!text.equals("")) {
            nickname = text;
        } else {
            nickname = null;
        }
    }

    private void getUsage() {
        final EditText editText = (EditText) findViewById(R.id.editTextUtilityUsage_EditUtility);
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
        final Spinner yearSpinner = (Spinner) findViewById(R.id.spinnerEditUtilityStartYear);

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,
                R.layout.support_simple_spinner_dropdown_item,
                model.getDateHandler().getYearList());
        spinnerArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        yearSpinner.setAdapter(spinnerArrayAdapter);
        yearSpinner.setSelection(model.getDateHandler().MAX_YEAR - selectedUtility.getStartYear());

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
        final Spinner monthSpinner = (Spinner) findViewById(R.id.spinnerEditUtilityStartMonth);

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,
                R.layout.support_simple_spinner_dropdown_item,
                model.getDateHandler().getMonthList());
        spinnerArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        monthSpinner.setAdapter(spinnerArrayAdapter);
        monthSpinner.setSelection(selectedUtility.getStartMonth() - 1);

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
        final Spinner daySpinner = (Spinner) findViewById(R.id.spinnerEditUtilityStartDay);
        model.getDateHandler().initializeDayList(startYear, startMonth);

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,
                R.layout.support_simple_spinner_dropdown_item,
                model.getDateHandler().getDayList());
        spinnerArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        daySpinner.setAdapter(spinnerArrayAdapter);
        daySpinner.setSelection(selectedUtility.getStartDay() - 1);

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
        final Spinner yearSpinner = (Spinner) findViewById(R.id.spinnerEditUtilityEndYear);

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,
                R.layout.support_simple_spinner_dropdown_item,
                model.getDateHandler().getYearList());
        spinnerArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        yearSpinner.setAdapter(spinnerArrayAdapter);
        yearSpinner.setSelection(model.getDateHandler().MAX_YEAR - selectedUtility.getEndYear());

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
        final Spinner monthSpinner = (Spinner) findViewById(R.id.spinnerEditUtilityEndMonth);

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,
                R.layout.support_simple_spinner_dropdown_item,
                model.getDateHandler().getMonthList());
        spinnerArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        monthSpinner.setAdapter(spinnerArrayAdapter);
        monthSpinner.setSelection(selectedUtility.getEndMonth() - 1);

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
        final Spinner daySpinner = (Spinner) findViewById(R.id.spinnerEditUtilityEndDay);
        model.getDateHandler().initializeDayList(startYear, startMonth);

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,
                R.layout.support_simple_spinner_dropdown_item,
                model.getDateHandler().getDayList());
        spinnerArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        daySpinner.setAdapter(spinnerArrayAdapter);
        daySpinner.setSelection(selectedUtility.getEndDay() - 1);

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

    private void setupNumberPeopleText() {
        EditText numPeople = (EditText) findViewById(R.id.editText_Utility_Num_People);
        String numPeopleString = selectedUtility.getNumPeople() + "";
        numPeople.setText(numPeopleString);
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

    private void setupEditBillButton() {
        Button editBtn = (Button) findViewById(R.id.buttonEditBillSave);
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getNickName();
                getUsage();
                getNumPeople();

                // If empty input display toast message
                if (invalidInput()) {
                    Toast.makeText(
                            EditUtilityActivity.this,
                            "Please fill out the form completely.",
                            Toast.LENGTH_SHORT).show();
                } else if (identicalDate() || negativeDates()) {
                    Toast.makeText(
                            EditUtilityActivity.this,
                            "Please check the dates. \n" +
                                    "Start date must be before end date.",
                            Toast.LENGTH_LONG).show();

                } else {
                    // Create a Utility
                    Utility utility = new Utility(nickname, naturalGas, electricity,
                            startingDate, endingDate, usage, numPeople);
                    selectedUtility.setNickname(nickname);
                    selectedUtility.setNaturalGas(naturalGas);
                    selectedUtility.setElectricity(electricity);
                    selectedUtility.setStartDateString(startingDate);
                    selectedUtility.setEndDateString(endingDate);
                    selectedUtility.setUsage(usage, naturalGas, electricity);
                    selectedUtility.setNumPeople(numPeople);

                    // Return to utility list and close activity
                    startActivity(new Intent(EditUtilityActivity.this, UtilityListActivity.class));
                    SaveData.saveUtilities(EditUtilityActivity.this);
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

    private void setupCancelButton() {
        Button cancelBtn = (Button) findViewById(R.id.buttonCancelEditBill);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Return to utility list and close activity
                finish();
            }
        });
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, EditUtilityActivity.class);
    }
}
