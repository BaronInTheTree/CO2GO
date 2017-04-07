package ca.cmpt276.carbonTracker.UI;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import com.example.sasha.carbontracker.R;

import ca.cmpt276.carbonTracker.AlternateUI.AddCarActivity;
import ca.cmpt276.carbonTracker.AlternateUI.AddJourneyActivity_Alternate;
import ca.cmpt276.carbonTracker.AlternateUI.AddRouteActivity;
import ca.cmpt276.carbonTracker.AlternateUI.AddUtilityActivity;
import ca.cmpt276.carbonTracker.AlternateUI.MainMenuActivity_Alternate;
import ca.cmpt276.carbonTracker.Internal_Logic.Car;
import ca.cmpt276.carbonTracker.Internal_Logic.CarbonModel;
import ca.cmpt276.carbonTracker.Internal_Logic.SaveData;

/**
 * SelectTransportationActivity lists the different types of cars that user has created
 * in a ListView and also the options to use Walking/Biking, Skytrain, or Bus. They are also
 * able to add a car into the carCollection in the Carbon Model here as well.
 *
 * @author Team Teal
 */

public class SelectTransportationActivity extends AppCompatActivity {

    CarbonModel model;
    private static final int REQUEST_CODE_EDIT_CAR = 1000;
    private ActionMenuView amvMenu;
    private ArrayList<Integer> vehicleIcons = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_transportation);
        model = CarbonModel.getInstance();

        SaveData.loadAllCars(SelectTransportationActivity.this);
        populateVehicleIconList();
        addBackButton();
        updateListView();
        addCarButton();
        setupActionBar();
    }

    private void populateVehicleIconList() {
        for (Car car : model.getCarCollection().getCarCollection()) {
            vehicleIcons.add(car.getIconID());
        }
    }

    private void addBackButton() {
        Button btn = (Button) findViewById(R.id.buttonBack_carList);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SelectTransportationActivity.this, MainMenuActivity_Alternate.class));
                finish();
            }
        });
    }

    private class MyArrayAdapter<T> extends ArrayAdapter<T>
    {
        public MyArrayAdapter(Context context, int resource, int textViewResourceId, List<T> objects) {
            super(context, resource, textViewResourceId, objects);
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = super.getView(position, convertView, parent);
            ImageView imageView = (ImageView) itemView.findViewById(R.id.vehicleIcon);
//            imageView.setBackgroundResource(vehicleIcons.get(position));
            imageView.setImageResource(vehicleIcons.get(position));
            System.out.println("TST 9.1: ID = " + vehicleIcons.get(position));
            return itemView;
        }
    }

    private void updateListView() {
        // Create list of items
        final CarbonModel model = CarbonModel.getInstance();
        List<String> cars = model.getCarCollection().getUICollection();
        // Build adapter
        ArrayAdapter<String> adapter = new MyArrayAdapter<String>(
                this,           // Context for activity
                R.layout.vehicle_list_layout, // Layout to use (create)
                R.id.vehicleListEntry, // TextView in layout
                cars);       // Items to be displayed
        // config list view
        ListView list = (ListView) findViewById(R.id.listOfCars);
        list.setAdapter(adapter);
        // Allows to click on list's items
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int i, long l) {
                model.setSelectedCar(model.getCarCollection().getCarAtIndex(i));
                Intent intent = ModifyCarActivity.makeIntent(SelectTransportationActivity.this);
                intent.putExtra("Index", i);
                SelectTransportationActivity.this.model.setSelectedTransportType("Car");
                startActivity(intent);
                finish();
            }
        });
    }

    private void addCarButton() {
        Button btn = (Button) findViewById(R.id.addCar_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectTransportationActivity.this, AddCarActivity.class);
                intent.putExtra("Caller", "VehicleList");
                model.setSelectedTransportType("Car");
                startActivity(intent);
                finish();
            }
        });
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, SelectTransportationActivity.class);
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
                    SaveData.saveTreeUnit(SelectTransportationActivity.this);
                } else {
                    CarbonModel.getInstance().getTreeUnit().setTreeUnitStatus(false);
                    SaveData.saveTreeUnit(SelectTransportationActivity.this);
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
                Intent intent = new Intent(SelectTransportationActivity.this, AddCarActivity.class);
                intent.putExtra("Caller", "VehicleList");
                startActivity(intent);
                finish();
                return true;
            }
            case R.id.action_addRoute: {
                Intent intent = new Intent(SelectTransportationActivity.this, AddRouteActivity.class);
                intent.putExtra("Caller", "VehicleList");
                startActivity(intent);
                finish();
                return true;
            }
            case R.id.action_addUtility: {
                Intent intent = new Intent(SelectTransportationActivity.this, AddUtilityActivity.class);
                intent.putExtra("Caller", "VehicleList");
                startActivity(intent);
                finish();
                return true;
            }
            case R.id.action_addJourney: {
                Intent intent = new Intent(SelectTransportationActivity.this, AddJourneyActivity_Alternate.class);
                intent.putExtra("Caller", "VehicleList");
                startActivity(intent);
                finish();
                return true;
            }
            case android.R.id.home: {
                Intent intent = new Intent(SelectTransportationActivity.this, MainMenuActivity_Alternate.class);
                startActivity(intent);
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}