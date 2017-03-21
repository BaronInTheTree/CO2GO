package ca.cmpt276.carbonTracker;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
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

import java.util.Date;

public class AddBillActivity extends AppCompatActivity {

    private static final int INVALID_INPUT = 0;
    private CarbonModel model;
    private boolean naturalGas;
    private boolean electricity;
    private Date startingDate;
    private Date endingDate;
    private int usage;
    private int numPeople;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bill);

        model = CarbonModel.getInstance();

        selectFuelSpinner();
        selectStartDateSpinner();
        selectEndDateSpinner();
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

    // TODO: create spinners which display yyyy-mm-dd then get data for starting/ending dates
    private void selectStartDateSpinner() {
        final Spinner selectStartYear = (Spinner) findViewById(R.id.spinnerSelectResource);

    }

    private void selectEndDateSpinner() {
        final Spinner selectEndYear = (Spinner) findViewById(R.id.spinnerSelectResource);

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
                getUsage();
                getNumPeople();

                // If empty input display toast message
                if (emptyInput()) {
                    Toast.makeText(
                            AddBillActivity.this,
                            "Please fill out the form completely.",
                            Toast.LENGTH_SHORT).show();
                } else {
                    // Create a Utility
                    Utility utility = new Utility(naturalGas, electricity,
                            startingDate, endingDate, usage, numPeople);

                    // Add to utility collection
                    UtilityCollection collection = model.getUtilityCollection();
                    collection.addUtility(utility);

                    model.setUtilityCollection(collection);

                    // Return to utility list and close activity
                    Intent intent = new Intent(AddBillActivity.this, UtilityListActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    // Until dates are not done this will crash the activity upon clicking save button
    private boolean emptyInput() {
        boolean emptyDates = startingDate.equals(null) || endingDate.equals(null);
        boolean emptyInput = usage == INVALID_INPUT || numPeople == INVALID_INPUT;

        if (emptyDates || emptyInput) {
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
                Intent intent = new Intent(AddBillActivity.this, UtilityListActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}