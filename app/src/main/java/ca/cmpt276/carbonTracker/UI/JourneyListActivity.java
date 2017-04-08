package ca.cmpt276.carbonTracker.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;

import com.example.sasha.carbontracker.R;

import ca.cmpt276.carbonTracker.AlternateUI.AddCarActivity;
import ca.cmpt276.carbonTracker.AlternateUI.AddJourneyActivity_Alternate;
import ca.cmpt276.carbonTracker.AlternateUI.AddRouteActivity;
import ca.cmpt276.carbonTracker.AlternateUI.AddUtilityActivity;
import ca.cmpt276.carbonTracker.AlternateUI.MainMenuActivity_Alternate;
import ca.cmpt276.carbonTracker.Internal_Logic.CarbonModel;
import ca.cmpt276.carbonTracker.Internal_Logic.Journey;
import ca.cmpt276.carbonTracker.Internal_Logic.SaveData;

import static ca.cmpt276.carbonTracker.Internal_Logic.SaveData.saveHiddenRoutes;
import static ca.cmpt276.carbonTracker.Internal_Logic.SaveData.saveJourneys;
import static ca.cmpt276.carbonTracker.Internal_Logic.SaveData.saveRoutes;

public class JourneyListActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_EDIT_JOURNEY = 2000;
    CarbonModel modelInstance = CarbonModel.getInstance();
    private ActionMenuView amvMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journey_list);
        SaveData.loadJourneys(JourneyListActivity.this);
        updateListView();
        setupBackButton();
        setupActionBar();
        setupAddJourneyButton();
    }

    public void updateListView() {
        CarbonModel carbonModel = CarbonModel.getInstance();
        ArrayAdapter<Journey> adapter = carbonModel.getJourneyCollection().getArrayAdapter(JourneyListActivity.this);
        ListView list = (ListView) findViewById(R.id.listViewJourneys);
        list.clearChoices();
        list.setAdapter(adapter);
        registerForContextMenu(list);
        saveJourneys(this);
    }

    /*private void populateJourneyList() {
        // Create list of items
        List<String> journeyList = new ArrayList<>();
        if (modelInstance.getTreeUnit().getTreeUnitStatus()) {
            journeyList = modelInstance.getJourneyCollection().getJourneyListTrees();
        } else {
            journeyList = modelInstance.getJourneyCollection().getJourneyList();
        }
        // Build adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,           // Context for activity
                R.layout.journey_list,  // Layout to use (create)
                journeyList);       // Items to be displayed
        // config list view
        final ListView list = (ListView) findViewById(R.id.listViewJourneys);
        list.setAdapter(adapter);
        // Allows to click on list's items
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int i, long l) {
                Intent intent = new Intent(JourneyListActivity.this, EditJourneyActivity.class);
                intent.putExtra("Index", i);
                System.out.println("Index: " + i);
                startActivity(intent);
                finish();
            }

        });
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final int index = position;
                modelInstance.getJourneyCollection().deleteJourney(index);
                saveJourneys(JourneyListActivity.this);

                populateJourneyList();
                return false;
            }
        });
    }*/

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (R.id.listViewJourneys == v.getId()) {
            menu.setHeaderTitle("Route Options:");
            menu.add("Edit");
            menu.add("Delete");
            menu.add("Cancel");
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int selectedJourneyPosition = acmi.position;
        if (item.toString().equals("Edit")) {
            Intent intent = new Intent(JourneyListActivity.this, EditJourneyActivity.class);
            intent.putExtra("Index", selectedJourneyPosition);
            startActivityForResult(intent, REQUEST_CODE_EDIT_JOURNEY);
        } else if (item.toString().equals("Delete")) {
            modelInstance.getJourneyCollection().deleteJourney(selectedJourneyPosition);
            SaveData.saveJourneys(JourneyListActivity.this);
            saveRoutes(JourneyListActivity.this);
            saveHiddenRoutes(JourneyListActivity.this);
        }
        updateListView();
        return true;
    }

    private void setupBackButton() {
        Button back = (Button) findViewById(R.id.buttonBack_journeyList);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(JourneyListActivity.this, MainMenuActivity_Alternate.class));
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_EDIT_JOURNEY) {
            updateListView();
        }
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
                    SaveData.saveTreeUnit(JourneyListActivity.this);

                } else {
                    CarbonModel.getInstance().getTreeUnit().setTreeUnitStatus(false);
                    SaveData.saveTreeUnit(JourneyListActivity.this);
                }
                updateListView();
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
                Intent intent = new Intent(JourneyListActivity.this, AddCarActivity.class);
                intent.putExtra("Caller", "JourneyList");
                startActivity(intent);
                finish();
                return true;
            }
            case R.id.action_addRoute: {
                Intent intent = new Intent(JourneyListActivity.this, AddRouteActivity.class);
                intent.putExtra("Caller", "JourneyList");
                startActivity(intent);
                finish();
                return true;
            }
            case R.id.action_addUtility: {
                Intent intent = new Intent(JourneyListActivity.this, AddUtilityActivity.class);
                intent.putExtra("Caller", "JourneyList");
                startActivity(intent);
                finish();
                return true;
            }
            case R.id.action_addJourney: {
                Intent intent = new Intent(JourneyListActivity.this, AddJourneyActivity_Alternate.class);
                intent.putExtra("Caller", "JourneyList");
                startActivity(intent);
                finish();
                return true;
            }
            case android.R.id.home: {
                Intent intent = new Intent(JourneyListActivity.this, MainMenuActivity_Alternate.class);
                startActivity(intent);
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupAddJourneyButton() {
        Button addJourney = (Button) findViewById(R.id.journeyList_addJourney);
        addJourney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(JourneyListActivity.this, AddJourneyActivity_Alternate.class));
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(JourneyListActivity.this, MainMenuActivity_Alternate.class));
        finish();
    }
}
