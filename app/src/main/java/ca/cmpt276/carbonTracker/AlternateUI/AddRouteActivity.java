package ca.cmpt276.carbonTracker.AlternateUI;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sasha.carbontracker.R;

import ca.cmpt276.carbonTracker.Internal_Logic.*;
import ca.cmpt276.carbonTracker.UI.SelectRouteActivity;

/**
    The AddRouteActivity is activated when the user click "Add" from SelectRouteActivity
    and this will allow the user to input their route and be able to use it instantaneously
    or save it to the route collection.

    @author Team Teal
 */
public class AddRouteActivity extends AppCompatActivity implements TextWatcher {
    private Route route;
    private CarbonModel carbonModel = CarbonModel.getInstance();
    private ActionMenuView amvMenu;
    private String callingActivity = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_route);

        setupSaveRouteButton();
        setupTextListeners();
        setupBackButton();
        setupActionBar();
    }

    private void setupSaveRouteButton() {
        Button saveRoute_btn = (Button) findViewById(R.id.saveRoute_btn);
        saveRoute_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (setupTotalText()) {
                    if (!route.getName().equals("")) {
                        carbonModel.getRouteCollection().addRoute(route);
                        setResult(Activity.RESULT_OK);
                        SaveData.saveRoutes(AddRouteActivity.this);
                        if (callingActivity.equals("AddJourney")) {
                            startActivity(new Intent(AddRouteActivity.this, AddJourneyActivity_Alternate.class));
                        }
                        else {
                            startActivity(new Intent(AddRouteActivity.this, MainMenuActivity_Alternate.class));
                        }
                        finish();
                    } else {
                        Toast.makeText(AddRouteActivity.this, "To save, enter a nickname.",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(AddRouteActivity.this, "Please fill out the form completely.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setupBackButton() {
        Button cancel = (Button) findViewById(R.id.backButtonRoute);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callingActivity.equals("AddJourney")) {
                    startActivity(new Intent(AddRouteActivity.this, AddJourneyActivity_Alternate.class));
                }
                else if (callingActivity.equals("RouteList")) {
                    startActivity(new Intent(AddRouteActivity.this, SelectRouteActivity.class));
                }
                else {
                    startActivity(new Intent(AddRouteActivity.this, MainMenuActivity_Alternate.class));
                }
                finish();
            }
        });
    }

    private boolean setupTotalText() {
        EditText nameInput = (EditText) findViewById(R.id.routeName);
        EditText cityInput = (EditText) findViewById(R.id.cityDistance);
        EditText highwayInput = (EditText) findViewById(R.id.highwayDistance);

        int highway;
        try {
            if (highwayInput.getText().toString().length() == 0) {
                throw new NullPointerException("Highway distance must contain a value");
            }
        } catch (NullPointerException e) {
            return false;
        }

        int city;
        try {
            if (cityInput.getText().toString().length() == 0) {
                throw new NullPointerException("City distance must contain a value");
            }
        } catch (NullPointerException e) {
            return false;
        }

        city = Integer.parseInt(cityInput.getText().toString());
        highway = Integer.parseInt(highwayInput.getText().toString());

        String routeName;
        try {
            if (nameInput.getText().toString().length() == 0) {
                routeName = "";
                route = new Route(routeName, highway, city);
                return true;
            }
        } catch (NullPointerException e) {
            return false;
        }
        routeName = nameInput.getText().toString();
        route = new Route(routeName, highway, city);
        return true;
    }

    private void setupTextListeners() {
        EditText nameInput = (EditText) findViewById(R.id.routeName);
        EditText cityInput = (EditText) findViewById(R.id.cityDistance);
        EditText highwayInput = (EditText) findViewById(R.id.highwayDistance);

        nameInput.addTextChangedListener(this);
        cityInput.addTextChangedListener(this);
        highwayInput.addTextChangedListener(this);
    }

    private void refreshTotalDistance() {
        EditText cityInput = (EditText) findViewById(R.id.cityDistance);
        EditText highwayInput = (EditText) findViewById(R.id.highwayDistance);

        int highway;
        try {
            if (isEmptyEditText(highwayInput)) {
                throw new NullPointerException("Highway distance must contain a value");
            }
        } catch (NullPointerException e) {
            return;
        }

        int city;
        try {
            if (isEmptyEditText(cityInput)) {
                throw new NullPointerException("City distance must contain a value");
            }
        } catch (NullPointerException e) {
            return;
        }

        city = Integer.parseInt(cityInput.getText().toString());
        highway = Integer.parseInt(highwayInput.getText().toString());

        TextView totalDistance = (TextView) findViewById(R.id.totalDistance);
        totalDistance.setText("" + (city + highway));
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, AddRouteActivity.class);
    }

    private boolean isEmptyEditText(EditText text) {
        return (text.getText().toString().length() == 0);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        refreshTotalDistance();
    }

    private void setupActionBar() {
        // Inflate your custom layout
        Toolbar toolBar = (Toolbar) findViewById(R.id.addRoute_toolBar);
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
        getMenuInflater().inflate(R.menu.toolbar_actions_addroute, amvMenu.getMenu());
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
                    SaveData.saveTreeUnit(AddRouteActivity.this);
                } else {
                    CarbonModel.getInstance().getTreeUnit().setTreeUnitStatus(false);
                    SaveData.saveTreeUnit(AddRouteActivity.this);
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
                Intent intent = new Intent(AddRouteActivity.this, AddCarActivity.class);
                intent.putExtra("Caller", "AddRoute");
                startActivity(intent);
                finish();
                return true;
            }
            case R.id.action_addUtility: {
                Intent intent = new Intent(AddRouteActivity.this, AddUtilityActivity.class);
                intent.putExtra("Caller", "AddRoute");
                startActivity(intent);
                finish();
                return true;
            }
            case R.id.action_addJourney: {
                Intent intent = new Intent(AddRouteActivity.this, AddJourneyActivity_Alternate.class);
                intent.putExtra("Caller", "AddRoute");
                startActivity(intent);
                finish();
                return true;
            }
            case android.R.id.home: {
                startActivity(new Intent(AddRouteActivity.this, MainMenuActivity_Alternate.class));
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(AddRouteActivity.this, MainMenuActivity_Alternate.class));
        finish();
    }

}