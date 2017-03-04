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
    private CarbonModel carbonmodel = CarbonModel.getInstance();

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
                carbonmodel.getRouteCollection().addRoute(route);
                startActivity(new Intent(AddRouteActivity.this, JourneyInfoActivity.class));
            }
        });
    }

    private void setupUseRouteButton() {
        Button useRoute_btn = (Button) findViewById(R.id.useRoute_btn);
        useRoute_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddRouteActivity.this, JourneyInfoActivity.class));
            }
        });
    }

    private void setupTotalText() {
        TextView total = (TextView) findViewById(R.id.totalDistance);
        EditText cityInput = (EditText) findViewById(R.id.cityDistance);
        EditText highwayInput = (EditText) findViewById(R.id.highwayDistance);

        String routeName;
        try {
            routeName = total.getText().toString();
        } catch (NullPointerException e) {
            total.setText("Route"); // if no route input, set default name as "Route".
            return;
        }

        int city;
        try {
            city = Integer.parseInt(cityInput.getText().toString());
        } catch (NumberFormatException e) {
            total.setText("" + 0);
            return;
        }

        int highway;
        try {
            highway = Integer.parseInt(highwayInput.getText().toString());
        } catch (NumberFormatException e) {
            total.setText("" + 0);
            return;
        }

        int totalDistance = city + highway;

        total.setText("" + totalDistance);
        route = new Route(routeName, highway, city);
    }

    private void setupTextListeners() {
        EditText cityInput = (EditText) findViewById(R.id.cityDistance);
        EditText highwayInput = (EditText) findViewById(R.id.highwayDistance);

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
