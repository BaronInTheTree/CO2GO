package ca.cmpt276.carbonTracker;

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
    }

    private void refreshTotalDistance(int city, int highway) {
        TextView totalDistance = (TextView) findViewById(R.id.totalDistance);
        totalDistance.setText("" + (city + highway));
    }

    private void setupSaveRouteButton() {
        Button saveRoute_btn = (Button) findViewById(R.id.saveRoute_btn);
        saveRoute_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (setupTotalText()) {
                    carbonModel.getRouteCollection().addRoute(route);
                    Intent intent = new Intent(AddRouteActivity.this, SelectRouteActivity.class);
                    startActivity(intent);
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
                startActivity(new Intent(AddRouteActivity.this, JourneyInformationActivity.class));
            }
        });
    }

    private boolean setupTotalText() {
        EditText nameInput = (EditText) findViewById(R.id.routeName);
        EditText cityInput = (EditText) findViewById(R.id.cityDistance);
        EditText highwayInput = (EditText) findViewById(R.id.highwayDistance);

        String routeName;
        try {
            if (nameInput.getText().toString().length() == 0) {
                throw new NullPointerException("Route must be at least 1 character");
            }
        } catch (NullPointerException e) {
            return false;
        }

        int highway;
        try {
            if (highwayInput.getText().toString().length() == 0) {
                throw new NullPointerException("Highway distance must contain a value");
            };
        } catch (NullPointerException e) {
            return false;
        }

        int city;
        try {
            if (cityInput.getText().toString().length() == 0) {
                throw new NullPointerException("City distance must contain a value");
            };
        } catch (NullPointerException e) {
            return false;
        }

        routeName = nameInput.getText().toString();
        city = Integer.parseInt(cityInput.getText().toString());
        highway = Integer.parseInt(highwayInput.getText().toString());
        refreshTotalDistance(city, highway);
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

    public static Intent makeIntent(Context context) {
        return new Intent(context, AddRouteActivity.class);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        setupTotalText();
    }
}
