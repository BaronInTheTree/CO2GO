package ca.cmpt276.carbonTracker.UI;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
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
import ca.cmpt276.carbonTracker.Internal_Logic.Route;
import ca.cmpt276.carbonTracker.Internal_Logic.SaveData;

import static ca.cmpt276.carbonTracker.Internal_Logic.SaveData.*;

/**
 The SelectRouteActivity displays the current list of visible routes that the user has saved
 and can use for their journey. It is opened when the user selects a mode of transportation
 and they can go back to that page using the back button on the bottom left.

 If there are any routes the user wishes to edit or delete, they can open a context menu by
 long pressing on the route they desire to edit.

 @author Team Teal
 */

public class SelectRouteActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_ADD_ROUTE = 1000;
    private static final int REQUEST_CODE_EDIT_ROUTE = 2000;
    CarbonModel modelInstance;
    private ActionMenuView amvMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_route);
        loadAllRoutes(this);
        modelInstance = CarbonModel.getInstance();

        setupBackButton();
        addRouteButton();
        updateListView();
        setupActionBar();
    }

    private void setupBackButton() {
        Button backButton = (Button) findViewById(R.id.backButtonRoute);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SelectRouteActivity.this, MainMenuActivity_Alternate.class));
                finish();
            }
        });
    }

    private void addRouteButton() {
        Button btn = (Button) findViewById(R.id.addRoute_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(SelectRouteActivity.this, AddRouteActivity.class), REQUEST_CODE_ADD_ROUTE);
                finish();
            }
        });
    }

    public void updateListView() {
        CarbonModel carbonModel = CarbonModel.getInstance();
        ArrayAdapter<Route> adapter = carbonModel.getRouteCollection().getArrayAdapter(SelectRouteActivity.this);
        ListView list = (ListView) findViewById(R.id.listOfRoutes);
        list.clearChoices();
        list.setAdapter(adapter);
        registerClickCallback();
        registerForContextMenu(list);
        saveRoutes(this);
    }

    private void registerClickCallback() {
        final CarbonModel model = CarbonModel.getInstance();
        ListView list = (ListView) findViewById(R.id.listOfRoutes);

        /*list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int i, long l) {
                final Route routeToUse = model.getRouteCollection().getRouteAtIndex(i);
                AlertDialog.Builder builder = new AlertDialog.Builder(SelectRouteActivity.this);
                builder.setTitle("Confirm");
                builder.setMessage("Are you sure you want to use Route " +
                        routeToUse.getName() + "?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        model.setSelectedRoute(routeToUse);
                        startActivity(new Intent(SelectRouteActivity.this, JourneyInformationActivity.class));
                        finish();
                    }
                });
                builder.setNegativeButton("No", null);
                builder.show();
            }
        });*/
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (R.id.listOfRoutes == v.getId()) {
            menu.setHeaderTitle("Route Options:");
            menu.add("Edit");
            menu.add("Delete");
            menu.add("Cancel");
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        CarbonModel cm = CarbonModel.getInstance();
        AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int selectedRoutePosition = acmi.position;
        cm.setSelectedRoute(cm.getRouteCollection().getRouteAtIndex(selectedRoutePosition));
        if (item.toString().equals("Edit")) {
            Intent editRouteIntent = EditRouteActivity.makeIntent(SelectRouteActivity.this);
            editRouteIntent.putExtra("RouteName", cm.getSelectedRoute().getName());
            editRouteIntent.putExtra("RouteCityKM", cm.getSelectedRoute().getCityDistanceKM());
            editRouteIntent.putExtra("RouteHighwayKM", cm.getSelectedRoute().getHighwayDistanceKM());
            editRouteIntent.putExtra("RouteIndex", selectedRoutePosition);
            startActivityForResult(editRouteIntent, REQUEST_CODE_EDIT_ROUTE);
        } else if (item.toString().equals("Delete")) {
            cm.getRouteCollection().hideRoute(cm.getSelectedRoute());
            cm.getRouteCollection().removeRoute(selectedRoutePosition);
            saveRoutes(SelectRouteActivity.this);
            saveHiddenRoutes(SelectRouteActivity.this);
        }
        updateListView();
        return true;
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, SelectRouteActivity.class);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_ADD_ROUTE || requestCode == REQUEST_CODE_EDIT_ROUTE) {
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
                    SaveData.saveTreeUnit(SelectRouteActivity.this);
                } else {
                    CarbonModel.getInstance().getTreeUnit().setTreeUnitStatus(false);
                    SaveData.saveTreeUnit(SelectRouteActivity.this);
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
                Intent intent = new Intent(SelectRouteActivity.this, AddCarActivity.class);
                intent.putExtra("Caller", "RouteList");
                startActivity(intent);
                finish();
                return true;
            }
            case R.id.action_addRoute: {
                Intent intent = new Intent(SelectRouteActivity.this, AddRouteActivity.class);
                intent.putExtra("Caller", "RouteList");
                startActivity(intent);
                finish();
                return true;
            }
            case R.id.action_addUtility: {
                Intent intent = new Intent(SelectRouteActivity.this, AddUtilityActivity.class);
                intent.putExtra("Caller", "RouteList");
                startActivity(intent);
                finish();
                return true;
            }
            case R.id.action_addJourney: {
                Intent intent = new Intent(SelectRouteActivity.this, AddJourneyActivity_Alternate.class);
                intent.putExtra("Caller", "RouteList");
                startActivity(intent);
                finish();
                return true;
            }
            case android.R.id.home: {
                Intent intent = new Intent(SelectRouteActivity.this, MainMenuActivity_Alternate.class);
                startActivity(intent);
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(SelectRouteActivity.this, MainMenuActivity_Alternate.class));
        finish();
    }
}