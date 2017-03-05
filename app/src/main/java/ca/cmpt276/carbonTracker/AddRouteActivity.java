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

    private void setupSaveRouteButton() {
        Button saveRoute_btn = (Button) findViewById(R.id.saveRoute_btn);
        saveRoute_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                carbonModel.getRouteCollection().addRoute(route);
                Intent intent = new Intent(AddRouteActivity.this, SelectRouteActivity.class);
                startActivity(intent);
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

    private void setupTotalText() {
        EditText nameInput = (EditText) findViewById(R.id.routeName);
        EditText cityInput = (EditText) findViewById(R.id.cityDistance);
        EditText highwayInput = (EditText) findViewById(R.id.highwayDistance);

        String routeName;
        try {
            routeName = nameInput.getText().toString();
        } catch (NullPointerException e) {
            return;
        }

        int city;
        try {
            city = Integer.parseInt(cityInput.getText().toString());
        } catch (NumberFormatException e) {
            nameInput.setText("" + 0);
            return;
        }

        int highway;
        try {
            highway = Integer.parseInt(highwayInput.getText().toString());
        } catch (NumberFormatException e) {
            nameInput.setText("" + 0);
            return;
        }

        int totalDistance = city + highway;

        nameInput.setText("" + totalDistance);
        route = new Route(routeName, highway, city);
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
