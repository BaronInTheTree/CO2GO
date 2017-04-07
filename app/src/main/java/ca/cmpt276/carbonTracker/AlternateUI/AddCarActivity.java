package ca.cmpt276.carbonTracker.AlternateUI;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.sasha.carbontracker.R;

import ca.cmpt276.carbonTracker.Internal_Logic.*;

/**
 The AddCarActivity is accessed when the user picks their mode of transportation and they can
 select from multiple spinners the type of car that they want to take on their journey.

 @author Team Teal
 */

public class AddCarActivity extends AppCompatActivity {

    CarbonModel model;
    String selectedMake;
    String selectedModel;
    String selectedYear;
    int selectedVariation;
    String selectedNickname;
    private ActionMenuView amvMenu;
    private String callingActivity = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);
        model = CarbonModel.getInstance();

        Intent callingIntent = getIntent();
        callingActivity = callingIntent.getStringExtra("Caller");

        setupSelectMakeSpinner();
        setupAddCarButton();
        setupEnterNicknameEditText();
        setupCancelButton();
        setupActionBar();
    }

    private void setupSelectMakeSpinner() {
        final Spinner selectMake = (Spinner) findViewById(R.id.spinnerSelectMake);

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,
                R.layout.support_simple_spinner_dropdown_item,
                model.getCarData().getMakeList());
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

        model.getCarData().updateModelList(selectedMake);

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,
                R.layout.support_simple_spinner_dropdown_item,
                model.getCarData().getModelList());
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

        model.getCarData().updateYearList(selectedMake, selectedModel);

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,
                R.layout.support_simple_spinner_dropdown_item,
                model.getCarData().getYearList());
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

        model.getCarData().updateSpecsList(selectedMake, selectedModel, selectedYear);

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,
                R.layout.support_simple_spinner_dropdown_item,
                model.getCarData().getSpecsList());
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
                    Car car = model.getCarData().findCar(selectedMake, selectedModel,
                            Integer.parseInt(selectedYear), selectedVariation);
                    car.setNickname(selectedNickname);

                    model.getCarCollection().addCar(car);
                    SaveData.saveCars(AddCarActivity.this);

                    if (callingActivity.equals("AddJourney")) {
                        startActivity(new Intent(AddCarActivity.this, AddJourneyActivity_Alternate.class));
                    }
                    else {
                        startActivity(new Intent(AddCarActivity.this, MainMenuActivity_Alternate.class));
                    }
                    finish();
                }
            }
        });
    }

    private void setupCancelButton() {
        Button cancel = (Button) findViewById(R.id.buttonCancel_AddCar);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callingActivity.equals("AddJourney")) {
                    startActivity(new Intent(AddCarActivity.this, AddJourneyActivity_Alternate.class));
                }
                else {
                    startActivity(new Intent(AddCarActivity.this, MainMenuActivity_Alternate.class));
                }
                finish();
            }
        });
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, AddCarActivity.class);
    }

    private void setupActionBar() {
        // Inflate your custom layout
        Toolbar toolBar = (Toolbar) findViewById(R.id.addCar_toolBar);
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
        getMenuInflater().inflate(R.menu.toolbar_actions_addvehicle, amvMenu.getMenu());
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
                    SaveData.saveTreeUnit(AddCarActivity.this);
                } else {
                    CarbonModel.getInstance().getTreeUnit().setTreeUnitStatus(false);
                    SaveData.saveTreeUnit(AddCarActivity.this);
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
            case R.id.action_addRoute: {
                Intent intent = new Intent(AddCarActivity.this, AddRouteActivity.class);
                intent.putExtra("Caller", "AddCar");
                startActivity(intent);
                finish();
                return true;
            }
            case R.id.action_addUtility: {
                Intent intent = new Intent(AddCarActivity.this, AddUtilityActivity.class);
                intent.putExtra("Caller", "AddCar");
                startActivity(intent);
                finish();
                return true;
            }
            case R.id.action_addJourney: {
                Intent intent = new Intent(AddCarActivity.this, AddJourneyActivity_Alternate.class);
                intent.putExtra("Caller", "AddCar");
                startActivity(intent);
                finish();
                return true;
            }
            case android.R.id.home: {
                Intent intent = new Intent(AddCarActivity.this, MainMenuActivity_Alternate.class);
                startActivity(intent);
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}