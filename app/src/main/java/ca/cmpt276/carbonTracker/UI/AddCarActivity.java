package ca.cmpt276.carbonTracker.UI;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.sasha.carbontracker.R;

import ca.cmpt276.carbonTracker.Internal_Logic.Car;
import ca.cmpt276.carbonTracker.Internal_Logic.CarbonModel;

/**
 The AddCarActivity is accessed when the user picks their mode of transportation and they can
 select from multiple spinners the type of car that they want to take on their journey.

 @author Team Teal
 */

public class AddCarActivity extends AppCompatActivity {

    CarbonModel modelInstance;
    String selectedMake;
    String selectedModel;
    String selectedYear;
    int selectedVariation;
    String selectedNickname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);
        modelInstance = CarbonModel.getInstance();

        setupSelectMakeSpinner();
        setupAddCarButton();
        setupEnterNicknameEditText();
        setupCancelButton();
        setupSaveUseButton();
    }

    private void setupSelectMakeSpinner() {
        final Spinner selectMake = (Spinner) findViewById(R.id.spinnerSelectMake);

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,
                R.layout.support_simple_spinner_dropdown_item,
                modelInstance.getCarData().getMakeList());
        spinnerArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        selectMake.setAdapter(spinnerArrayAdapter);
        selectMake.setSelection(0);

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

    private void setupSelectModelSpinner() {
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

    private void setupSelectYearSpinner() {
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

    private void setupSelectSpecsSpinner() {
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
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setupEnterNicknameEditText() {
        final EditText enterNickname = (EditText) findViewById(R.id.editTextEnterNickname);
        enterNickname.setImeOptions(EditorInfo.IME_ACTION_DONE);
        enterNickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    private void setupAddCarButton() {
        Button addCar = (Button) findViewById(R.id.buttonAddCar);
        final EditText enterNickname = (EditText) findViewById(R.id.editTextEnterNickname);
        addCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedNickname = enterNickname.getText().toString();
                if (selectedNickname.equals("")) {
                    Toast.makeText(AddCarActivity.this,
                            "Please enter a nickname for your car.",
                            Toast.LENGTH_LONG).show();
                } else {
                    Car car = modelInstance.getCarData().findCar(selectedMake, selectedModel,
                            Integer.parseInt(selectedYear), selectedVariation);
                    car.setNickname(selectedNickname);

                    modelInstance.getCarCollection().addCar(car);

                    Intent intent = SelectTransportationActivity.makeIntent(AddCarActivity.this);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private void setupSaveUseButton() {
        Button saveUseCar = (Button) findViewById(R.id.buttonSaveAndUseCar);
        final EditText enterNickname = (EditText) findViewById(R.id.editTextEnterNickname);
        saveUseCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedNickname = enterNickname.getText().toString();
                if (selectedNickname.equals("")) {
                    Toast.makeText(AddCarActivity.this,
                            "Please enter a nickname for your car.",
                            Toast.LENGTH_LONG).show();
                } else {
                    Car car = modelInstance.getCarData().findCar(selectedMake, selectedModel,
                            Integer.parseInt(selectedYear), selectedVariation);
                    car.setNickname(selectedNickname);

                    modelInstance.getCarCollection().addCar(car);
                    modelInstance.setSelectedCar(modelInstance.getCarCollection().getLatestCar());

                    Intent intent = ModifyCarActivity.makeIntent(AddCarActivity.this);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private void setupCancelButton() {
        Button cancel = (Button) findViewById(R.id.buttonCancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = SelectTransportationActivity.makeIntent(AddCarActivity.this);
                startActivity(intent);
                finish();
            }
        });
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, AddCarActivity.class);
    }
}