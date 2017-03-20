package ca.cmpt276.carbonTracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.sasha.carbontracker.R;


public class AddBillActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bill);

        CarbonModel model = CarbonModel.getInstance();

        selectFuelSpinner(model);
        saveBillButton();
        cancelButton();
    }

    private void selectFuelSpinner(CarbonModel modelInstance) {
        final Spinner selectMake = (Spinner) findViewById(R.id.spinnerSelectResource);

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this,
                R.layout.support_simple_spinner_dropdown_item,
                modelInstance.getUtilityData());

        spinnerArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        selectMake.setAdapter(spinnerArrayAdapter);

        selectMake.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // TODO: Set selected resource as true while opposite as false
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void saveBillButton() {
        Button saveBtn = (Button) findViewById(R.id.buttonAddBillSave);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a Utility, jump to Utility list and finish activity
            }
        });
    }

    private void cancelButton() {
        Button cancelBtn = (Button) findViewById(R.id.buttonAddBillCancel);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // go to utility list and finish activity
            }
        });
    }
}