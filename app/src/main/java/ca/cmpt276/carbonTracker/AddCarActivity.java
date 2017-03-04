package ca.cmpt276.carbonTracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.sasha.carbontracker.R;

public class AddCarActivity extends AppCompatActivity {

    CarbonModel modelInstance;
    String selectedMake;
    String selectedModel;
    String selectedYear;
    int selectedVariation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);
        modelInstance = CarbonModel.getInstance();

        setupSelectMakeSpinner();

    }

    private void setupSelectMakeSpinner(){
        final Spinner selectMake = (Spinner) findViewById(R.id.spinnerSelectMake);

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,
                R.layout.support_simple_spinner_dropdown_item,
                modelInstance.getCarData().getMakeList());
        spinnerArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        selectMake.setAdapter(spinnerArrayAdapter);

        selectMake.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setupSelectModelSpinner();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setupSelectModelSpinner(){
        Spinner selectModel = (Spinner) findViewById(R.id.spinnerSelectModel);
        Spinner selectMake = (Spinner) findViewById(R.id.spinnerSelectMake);

        selectedMake = selectMake.getSelectedItem().toString();

        modelInstance.getCarData().updateModelList(selectedMake);

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,
                R.layout.support_simple_spinner_dropdown_item,
                modelInstance.getCarData().getModelList());
        spinnerArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        selectModel.setAdapter(spinnerArrayAdapter);

        selectModel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setupSelectYearSpinner();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void setupSelectYearSpinner(){
        Spinner selectModel = (Spinner) findViewById(R.id.spinnerSelectModel);
        Spinner selectYear = (Spinner) findViewById(R.id.spinnerSelectYear);

        selectedModel = selectModel.getSelectedItem().toString();

        modelInstance.getCarData().updateYearList(selectedMake, selectedModel);

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,
                R.layout.support_simple_spinner_dropdown_item,
                modelInstance.getCarData().getYearList());
        spinnerArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        selectYear.setAdapter(spinnerArrayAdapter);

        selectYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setupSelectSpecsSpinner();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void setupSelectSpecsSpinner(){
        Spinner selectYear = (Spinner) findViewById(R.id.spinnerSelectYear);
        Spinner selectSpecs = (Spinner) findViewById(R.id.spinnerSelectSpecs);

        selectedYear = selectYear.getSelectedItem().toString();

        modelInstance.getCarData().updateSpecsList(selectedMake, selectedModel, selectedYear);

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,
                R.layout.support_simple_spinner_dropdown_item,
                modelInstance.getCarData().getSpecsList());
        spinnerArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        selectSpecs.setAdapter(spinnerArrayAdapter);

        selectSpecs.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedVariation = position;
                Toast.makeText(AddCarActivity.this, "Position: " + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }



}
