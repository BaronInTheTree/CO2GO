package ca.cmpt276.carbonTracker.UI;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.sasha.carbontracker.R;

import ca.cmpt276.carbonTracker.AlternateUI.AddCarActivity;
import ca.cmpt276.carbonTracker.AlternateUI.AddJourneyActivity_Alternate;
import ca.cmpt276.carbonTracker.AlternateUI.AddRouteActivity;
import ca.cmpt276.carbonTracker.AlternateUI.AddUtilityActivity;
import ca.cmpt276.carbonTracker.AlternateUI.MainMenuActivity_Alternate;
import ca.cmpt276.carbonTracker.Internal_Logic.CarbonModel;
import ca.cmpt276.carbonTracker.Internal_Logic.SaveData;

/**
 The ModifyCarActivity allows the user to view the statistics of the car they chose and be able
 to delete or edit it by pressing the appropriate buttons. They can also navigate to the
 SelectRouteActivity from here to continue adding to their journey, or go back and select another
 car.

 @author Team Teal
 */
public class ModifyCarActivity extends AppCompatActivity {

    int selectedCarIndex;
    CarbonModel modelInstance;
    private ActionMenuView amvMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_car);

        Intent callingIntent = getIntent();
        selectedCarIndex = callingIntent.getIntExtra("Index", 0);
        modelInstance = CarbonModel.getInstance();

        editCarButton();
        deleteCarButton();
        setupDisplayCarDetails();
        setupBackButton();
        setupActionBar();
    }

    private void editCarButton() {
        Button btn = (Button) findViewById(R.id.editCarBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = EditCarActivity.makeIntent(ModifyCarActivity.this);
                intent.putExtra("Index", selectedCarIndex);
                startActivity(intent);
                finish();
            }
        });
    }

    private void setupBackButton(){
        Button backButton = (Button) findViewById(R.id.buttonBackModCar);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = SelectTransportationActivity.makeIntent(ModifyCarActivity.this);
                startActivity(intent);
                finish();
            }
        });
    }

    private void deleteCarButton() {
        Button btn = (Button) findViewById(R.id.deleteCarBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modelInstance.getCarCollection().hideCar(modelInstance.getSelectedCar());
                modelInstance.getCarCollection().removeCar(selectedCarIndex);
                SaveData.saveCars(ModifyCarActivity.this);
                SaveData.saveHiddenCars(ModifyCarActivity.this);
                Intent intent = SelectTransportationActivity.makeIntent(ModifyCarActivity.this);
                startActivity(intent);
                finish();
            }
        });
    }

    private void setupDisplayCarDetails(){
        TextView displayNickname = (TextView) findViewById(R.id.textViewDisplayNickname);
        displayNickname.setText("" + modelInstance.getSelectedCar().getNickname());

        TextView displayMake = (TextView) findViewById(R.id.textViewDisplayMake);
        displayMake.setText("" + modelInstance.getSelectedCar().getMake());

        TextView displayModel = (TextView) findViewById(R.id.textViewDisplayModel);
        displayModel.setText("" + modelInstance.getSelectedCar().getModel());

        TextView displayYear = (TextView) findViewById(R.id.textViewDisplayYear);
        displayYear.setText("" + modelInstance.getSelectedCar().getYear());

        TextView displayTransmission = (TextView) findViewById(R.id.textViewDisplayTransmission);
        displayTransmission.setText("" + modelInstance.getSelectedCar().getTrany());

        TextView displayCylinders = (TextView) findViewById(R.id.textViewDisplayCylinders);
        displayCylinders.setText("" + modelInstance.getSelectedCar().getCylinders());

        TextView displayDisplacement = (TextView) findViewById(R.id.textViewDisplayDisplacement);
        displayDisplacement.setText("" + modelInstance.getSelectedCar().getDisplacement() + "L");

        TextView displayFuelType = (TextView) findViewById(R.id.textViewDisplayFuelType);
        displayFuelType.setText("" + modelInstance.getSelectedCar().getFuelType());

        TextView displayFuelEconomy = (TextView) findViewById(R.id.textViewDisplayFuelEconomy);
        displayFuelEconomy.setText("" + modelInstance.getSelectedCar().getAvgLitersPer100KM());
    }

    private void setupActionBar() {
        // Inflate your custom layout
        Toolbar toolBar = (Toolbar) findViewById(R.id.mainMenu_toolBar);
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
        getMenuInflater().inflate(R.menu.toolbar_actions_mainmenu, amvMenu.getMenu());
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
                    SaveData.saveTreeUnit(ModifyCarActivity.this);
                } else {
                    CarbonModel.getInstance().getTreeUnit().setTreeUnitStatus(false);
                    SaveData.saveTreeUnit(ModifyCarActivity.this);
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
                Intent intent = new Intent(ModifyCarActivity.this, AddCarActivity.class);
                intent.putExtra("Caller", "VehicleList");
                startActivity(intent);
                finish();
                return true;
            }
            case R.id.action_addRoute: {
                Intent intent = new Intent(ModifyCarActivity.this, AddRouteActivity.class);
                intent.putExtra("Caller", "VehicleList");
                startActivity(intent);
                finish();
                return true;
            }
            case R.id.action_addUtility: {
                Intent intent = new Intent(ModifyCarActivity.this, AddUtilityActivity.class);
                intent.putExtra("Caller", "VehicleList");
                startActivity(intent);
                finish();
                return true;
            }
            case R.id.action_addJourney: {
                Intent intent = new Intent(ModifyCarActivity.this, AddJourneyActivity_Alternate.class);
                intent.putExtra("Caller", "VehicleList");
                startActivity(intent);
                finish();
                return true;
            }
            case android.R.id.home: {
                Intent intent = new Intent(ModifyCarActivity.this, MainMenuActivity_Alternate.class);
                startActivity(intent);
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, ModifyCarActivity.class);
    }
}