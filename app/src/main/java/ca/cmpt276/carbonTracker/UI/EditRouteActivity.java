package ca.cmpt276.carbonTracker.UI;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sasha.carbontracker.R;

import ca.cmpt276.carbonTracker.AlternateUI.AddCarActivity;
import ca.cmpt276.carbonTracker.AlternateUI.MainMenuActivity_Alternate;
import ca.cmpt276.carbonTracker.Internal_Logic.*;

/**
 * Executed when clicking "Edit" from the context menu in SelectRouteActivity, the user is able to
 * modify an existing route and then return back to the list of routes that the can select (which
 * is SelectRouteActivity).
 *
 * @author Team Teal
 */

public class EditRouteActivity extends AppCompatActivity implements TextWatcher {
    private int routeIndex;
    private Route route;
    private CarbonModel carbonModel = CarbonModel.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_route);
        setupTextListeners();
        getExtraFromIntent();
        setupTotalText();
        setupEditRouteButton();
        setupBackButton();
    }

    private void getExtraFromIntent() {
        Intent intent = getIntent();
        String routeString = intent.getStringExtra("RouteName");
        int cityKM = intent.getIntExtra("RouteCityKM", 1);
        String cityString = Integer.valueOf(cityKM).toString();
        int highwayKM = intent.getIntExtra("RouteHighwayKM", 0);
        String highwayString = Integer.valueOf(highwayKM).toString();
        routeIndex = intent.getIntExtra("RouteIndex", 1);

        EditText nameInput = (EditText) findViewById(R.id.routeName);
        EditText cityInput = (EditText) findViewById(R.id.cityDistance);
        EditText highwayInput = (EditText) findViewById(R.id.highwayDistance);

        nameInput.setText(routeString, TextView.BufferType.EDITABLE);
        cityInput.setText(cityString, TextView.BufferType.EDITABLE);
        highwayInput.setText(highwayString, TextView.BufferType.EDITABLE);
    }

    private void setupEditRouteButton() {
        Button editButton = (Button) findViewById(R.id.editRouteButton);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (setupTotalText()) {
                    if (route.getCityDistanceKM() + route.getHighwayDistanceKM() == 0) {
                        Toast.makeText(EditRouteActivity.this, "Total distance cannot be 0.",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                    carbonModel.setSelectedRoute(route);
                    carbonModel.getRouteCollection().editRoute(route, routeIndex);
                    SaveData.saveRoutes(EditRouteActivity.this);
                    setResult(Activity.RESULT_OK);
                    startActivity(new Intent(EditRouteActivity.this, SelectRouteActivity.class));
                    finish();
                } else {
                    Toast.makeText(EditRouteActivity.this, "Please fill out the form completely.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setupBackButton() {
        Button cancel = (Button) findViewById(R.id.backButtonEditRoute);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditRouteActivity.this, SelectRouteActivity.class));
                finish();
            }
        });
    }

    public boolean setupTotalText() {
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
                throw new NullPointerException("Must contain nickname");
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
        return new Intent(context, EditRouteActivity.class);
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

    @Override
    public void onBackPressed() {
        startActivity(new Intent(EditRouteActivity.this, SelectRouteActivity.class));
        finish();
    }
}

