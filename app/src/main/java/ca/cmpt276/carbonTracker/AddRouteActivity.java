package ca.cmpt276.carbonTracker;

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

public class AddRouteActivity extends AppCompatActivity implements TextWatcher {
    private Route route;
    private CarbonModel carbonModel = CarbonModel.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_route);

        setupSaveRouteButton();
        setupUseRouteButton();
        setupTextListeners();
        setupSaveUseButton();
        setupBackButton();
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
                        startActivity(new Intent(AddRouteActivity.this, SelectRouteActivity.class));
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

    private void setupSaveUseButton() {
        Button saveUseRoute = (Button) findViewById(R.id.buttonSaveUseRoute);
        saveUseRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (setupTotalText()) {
                    if (!route.getName().equals("")) {
                        carbonModel.getRouteCollection().addRoute(route);
                        carbonModel.setSelectedRoute(route);
                        setResult(Activity.RESULT_OK);
                        startActivity(new Intent(AddRouteActivity.this, JourneyInformationActivity.class));
                        SaveData.saveRoutes(AddRouteActivity.this);
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

    private void setupUseRouteButton() {
        Button useRoute_btn = (Button) findViewById(R.id.useRoute_btn);
        useRoute_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText cityInput = (EditText) findViewById(R.id.cityDistance);
                EditText highwayInput = (EditText) findViewById(R.id.highwayDistance);
                if (setupTotalText()) {
                    carbonModel.setSelectedRoute(route);
                    startActivity(new Intent(AddRouteActivity.this, JourneyInformationActivity.class));
                    finish();
                } else if ((isEmptyEditText(cityInput) || isEmptyEditText(highwayInput)) ||
                        (!isEmptyEditText(cityInput) && !isEmptyEditText(highwayInput))) {
                    Toast.makeText(AddRouteActivity.this, "Please enter both highway and city distance",
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
                Intent selectRouteIntent = SelectRouteActivity.makeIntent(AddRouteActivity.this);
                startActivity(selectRouteIntent);
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
}