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
import android.widget.TextView;
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

    private int index;
    private Utility utility;

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
        setContentView(R.layout.activity_edit_utility);

        model = CarbonModel.getInstance();
        utility = model.getUtilityCollection().getUtility(index);

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

        // Set utility's values for all fields
        displayUtilityValues();

        // Save and cancel buttons
        setupEditBillButton();
        setupCancelButton();
    }

    private void displayUtilityValues() {
        // Sets nickname
        EditText nicknameText = (EditText) findViewById(R.id.editUtilityNickname);
        nicknameText.setText(utility.getNickname(), TextView.BufferType.EDITABLE);

        // Sets usage
        EditText usageText = (EditText) findViewById(R.id.editText_Utility_Usage);
        usageText.setText(utility.getUsage() + "", TextView.BufferType.EDITABLE);

        // Sets num of people
        EditText numPeopleText = (EditText) findViewById(R.id.editText_Utility_Num_People);
        numPeopleText.setText(utility.getNumPeople() + "", TextView.BufferType.EDITABLE);

        // Set fuel spinner

        // Set start year spinner
        Spinner startYearSpinner = (Spinner) findViewById(R.id.spinnerEditUtilityStartYear);
        ArrayAdapter<String> startYearSpinnerArrayAdapter = new ArrayAdapter<String>(this,
                R.layout.support_simple_spinner_dropdown_item,
                model.getDateHandler().getYearList());
        startYearSpinnerArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        startYearSpinner.setAdapter(startYearSpinnerArrayAdapter);

        String startYear = utility.getStartDate();
        String[] strStartYear = startYear.split("-");
        int startYearSpinnerPosition = startYearSpinnerArrayAdapter.getPosition(strStartYear[0]);
        startYearSpinner.setSelection(startYearSpinnerPosition);

        // Set start month spinner
        Spinner startMonthSpinner = (Spinner) findViewById(R.id.spinnerEditUtilityStartMonth);
        ArrayAdapter<String> startMonthSpinnerArrayAdapter = new ArrayAdapter<String>(this,
                R.layout.support_simple_spinner_dropdown_item,
                model.getDateHandler().getMonthList());
        startMonthSpinnerArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        startMonthSpinner.setAdapter(startMonthSpinnerArrayAdapter);

        String[] strStartMonth = utility.getStartDate().split("-");
        String startMonth = strStartMonth[1];
        int startMonthSpinnerPosition = startMonthSpinnerArrayAdapter.getPosition(startMonth);
        startMonthSpinner.setSelection(startMonthSpinnerPosition);

        // doesn't work
        // Set start day
        Spinner startDaySpinner = (Spinner) findViewById(R.id.spinnerEditUtilityStartDay);
        ArrayAdapter<String> startDaySpinnerArrayAdapter = new ArrayAdapter<String>(this,
                R.layout.support_simple_spinner_dropdown_item,
                model.getDateHandler().getDayList());
        startDaySpinnerArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        startDaySpinner.setAdapter(startDaySpinnerArrayAdapter);

        String startDay = utility.getStartDate();
        String[] strStartDay = startDay.split("-");
        int startDaySpinnerPosition = startDaySpinnerArrayAdapter.getPosition(strStartDay[2]);
        startDaySpinner.setSelection(startDaySpinnerPosition+2);

        // doesnt work
        // Set end year spinner
        Spinner endYearSpinner = (Spinner) findViewById(R.id.spinnerEditUtilityEndYear);
        ArrayAdapter<String> endYearSpinnerArrayAdapter = new ArrayAdapter<String>(this,
                R.layout.support_simple_spinner_dropdown_item,
                model.getDateHandler().getYearList());
        endYearSpinnerArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        endYearSpinner.setAdapter(endYearSpinnerArrayAdapter);

        String endYear = utility.getEndDate();
        String[] strEndYear = endYear.split("-");
        int endYearSpinnerPosition = startYearSpinnerArrayAdapter.getPosition(strEndYear[0]);
        startYearSpinner.setSelection(endYearSpinnerPosition+3);

        // Set end month spinner
        Spinner endMonthSpinner = (Spinner) findViewById(R.id.spinnerEditUtilityEndMonth);
        ArrayAdapter<String> endMonthSpinnerArrayAdapter = new ArrayAdapter<String>(this,
                R.layout.support_simple_spinner_dropdown_item,
                model.getDateHandler().getMonthList());
        endMonthSpinnerArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        endMonthSpinner.setAdapter(endMonthSpinnerArrayAdapter);

        String endMonth = utility.getEndDate();
        String[] strEndMonth = endMonth.split("-");
        int endMonthSpinnerPosition = endMonthSpinnerArrayAdapter.getPosition(strEndMonth[1]);
        endMonthSpinner.setSelection(endMonthSpinnerPosition);

        // Set end day
        Spinner endDaySpinner = (Spinner) findViewById(R.id.spinnerEditUtilityEndDay);
        ArrayAdapter<String> endDaySpinnerArrayAdapter = new ArrayAdapter<String>(this,
                R.layout.support_simple_spinner_dropdown_item,
                model.getDateHandler().getDayList());
        endDaySpinnerArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        endDaySpinner.setAdapter(endDaySpinnerArrayAdapter);

        String endDay = utility.getEndDate();
        String[] strEndDay = endDay.split("-");
        int endDaySpinnerPosition = endDaySpinnerArrayAdapter.getPosition(strEndDay[2]);
        endDaySpinner.setSelection(endDaySpinnerPosition);  // day list starts from index 0
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
        final Spinner yearSpinner = (Spinner) findViewById(R.id.spinnerEditUtilityStartYear);
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
        final Spinner monthSpinner = (Spinner) findViewById(R.id.spinnerEditUtilityStartMonth);
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
        final Spinner daySpinner = (Spinner) findViewById(R.id.spinnerEditUtilityStartDay);
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

    // Following 3 methods get ending date
    private void setupEndYearSpinner() {
        final Spinner yearSpinner = (Spinner) findViewById(R.id.spinnerEditUtilityEndYear);
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
        final Spinner monthSpinner = (Spinner) findViewById(R.id.spinnerEditUtilityEndMonth);
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
        final Spinner daySpinner = (Spinner) findViewById(R.id.spinnerEditUtilityEndDay);
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

                    // Add to utility collection
                    UtilityCollection collection = model.getUtilityCollection();
                    collection.addUtility(utility);

                    model.setUtilityCollection(collection);

                    // Return to utility list and close activity
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
