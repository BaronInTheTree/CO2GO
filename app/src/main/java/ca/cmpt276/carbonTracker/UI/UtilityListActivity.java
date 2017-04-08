package ca.cmpt276.carbonTracker.UI;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sasha.carbontracker.R;

import java.util.List;

import ca.cmpt276.carbonTracker.AlternateUI.AddCarActivity;
import ca.cmpt276.carbonTracker.AlternateUI.AddJourneyActivity_Alternate;
import ca.cmpt276.carbonTracker.AlternateUI.AddRouteActivity;
import ca.cmpt276.carbonTracker.AlternateUI.AddUtilityActivity;
import ca.cmpt276.carbonTracker.AlternateUI.MainMenuActivity_Alternate;
import ca.cmpt276.carbonTracker.Internal_Logic.*;

import static ca.cmpt276.carbonTracker.Internal_Logic.SaveData.saveRoutes;
import static ca.cmpt276.carbonTracker.Internal_Logic.SaveData.saveUtilities;

/*
 * UtilityListActivity class lists all created utilities to screen displaying the nickname of
 * utility and its fuel type.
 * Activity allows the user to add a new utility or return to main menu.
 */
public class UtilityListActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_EDIT_UTILITY = 1000;
    private ActionMenuView amvMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_utility_list);

        SaveData.loadUtilities(this);
        populateListOfUtilities();
        addUtilityBtn();
        backBtn();
        updateListView();
        setupActionBar();
    }

    private void populateListOfUtilities() {
        // Create list of items
        final CarbonModel model = CarbonModel.getInstance();
        List<String> utilities = model.getUtilityCollection().displayUtilityList();

        // Build adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,                       // Context for activity
                R.layout.route_list,
                R.id.routeListViewText, // Layout to use (create)
                utilities);                 // Items to be displayed

        // config list view
        ListView list = (ListView) findViewById(R.id.listOfUtilities);
        list.clearChoices();
        list.setAdapter(adapter);
        registerForContextMenu(list);
        saveUtilities(this);

        // Allows to click on list's items
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int i, long l) {
                model.getUtilityCollection().getUtility(i);

                Intent intent = new Intent(UtilityListActivity.this, UtilitySummaryActivity.class);
                intent.putExtra("Index", i);
                startActivity(intent);
                finish();
            }
        });
    }

    private void addUtilityBtn() {
        Button btn = (Button) findViewById(R.id.addUtility_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UtilityListActivity.this, AddUtilityActivity.class));
                finish();
            }
        });
    }

    private void backBtn() {
        Button btn = (Button) findViewById(R.id.backButtonUtilityList);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UtilityListActivity.this, MainMenuActivity_Alternate.class));
                finish();
            }
        });
    }

    public void updateListView() {
        CarbonModel carbonModel = CarbonModel.getInstance();
        ArrayAdapter<Utility> adapter = carbonModel.getUtilityCollection().getArrayAdapter(UtilityListActivity.this);
        ListView list = (ListView) findViewById(R.id.listOfUtilities);
        list.clearChoices();
        list.setAdapter(adapter);
        registerForContextMenu(list);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (R.id.listOfUtilities == v.getId()) {
            menu.setHeaderTitle("Do what with utility?");
            menu.add("Edit");
            menu.add("Delete");
            menu.add("Cancel");
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        CarbonModel cm = CarbonModel.getInstance();
        AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int selectedUtilityPosition = acmi.position;
        if (item.toString().equals("Edit")) {
            Intent editUtilityIntent = EditUtilityActivity.makeIntent(UtilityListActivity.this);
            editUtilityIntent.putExtra("UtilityIndex", selectedUtilityPosition);
            startActivityForResult(editUtilityIntent, REQUEST_CODE_EDIT_UTILITY);
            finish();
        } else if (item.toString().equals("Delete")) {
            cm.getUtilityCollection().deleteUtility(selectedUtilityPosition);
            SaveData.saveUtilities(this);
        }
        updateListView();
        return true;
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
                    SaveData.saveTreeUnit(UtilityListActivity.this);
                } else {
                    CarbonModel.getInstance().getTreeUnit().setTreeUnitStatus(false);
                    SaveData.saveTreeUnit(UtilityListActivity.this);
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
                Intent intent = new Intent(UtilityListActivity.this, AddCarActivity.class);
                intent.putExtra("Caller", "VehicleList");
                startActivity(intent);
                finish();
                return true;
            }
            case R.id.action_addRoute: {
                Intent intent = new Intent(UtilityListActivity.this, AddRouteActivity.class);
                intent.putExtra("Caller", "VehicleList");
                startActivity(intent);
                finish();
                return true;
            }
            case R.id.action_addUtility: {
                Intent intent = new Intent(UtilityListActivity.this, AddUtilityActivity.class);
                intent.putExtra("Caller", "VehicleList");
                startActivity(intent);
                finish();
                return true;
            }
            case R.id.action_addJourney: {
                Intent intent = new Intent(UtilityListActivity.this, AddJourneyActivity_Alternate.class);
                intent.putExtra("Caller", "VehicleList");
                startActivity(intent);
                finish();
                return true;
            }
            case android.R.id.home: {
                Intent intent = new Intent(UtilityListActivity.this, MainMenuActivity_Alternate.class);
                startActivity(intent);
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(UtilityListActivity.this, MainMenuActivity_Alternate.class));
        finish();
    }
}