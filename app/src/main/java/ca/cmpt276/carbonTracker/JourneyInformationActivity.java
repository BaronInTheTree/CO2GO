package ca.cmpt276.carbonTracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.sasha.carbontracker.R;

public class JourneyInformationActivity extends AppCompatActivity {

    CarbonModel currentInstance = CarbonModel.getInstance();
    Car newCar = currentInstance.getSelectedCar();
    Route newRoute = currentInstance.getSelectedRoute();
    Journey currentJourney = currentInstance.createJourney();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journey_information);

        setupInfo();
        setupButtons();
    }

    private void setupInfo() {

        TextView date = (TextView) findViewById(R.id.date_entry);
        date.setText(currentJourney.getDate());

        TextView vehicle = (TextView) findViewById(R.id.vehicle_entry);
        String vehicleName = currentJourney.getTransportation().getNickname();
        if (vehicleName.length() > 18) vehicleName = vehicleName.substring(0, 18);
        vehicle.setText(vehicleName);

        TextView route = (TextView) findViewById(R.id.route_entry);
        String routeName = currentJourney.getRoute().getName();
        if (routeName.length() > 18) routeName = routeName.substring(0, 18);
        route.setText(routeName);

        TextView distance = (TextView) findViewById(R.id.distance_entry);
        String distanceInfo = "" + currentJourney.getRoute().getTotalDistanceKM();
        if (distanceInfo.length() > 5) distanceInfo = distanceInfo.substring(0, 5);
        distance.setText(distanceInfo);

        TextView emission = (TextView) findViewById(R.id.emission_entry);
        String emissionInfo = "" + currentJourney.getEmissionsKM();
        if (emissionInfo.length() > 10) emissionInfo = emissionInfo.substring(0, 10);
        emission.setText(emissionInfo);
    }

    private void setupButtons() {
        Button confirm_btn = (Button) findViewById(R.id.confirm);
        confirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentInstance.addNewJourney(currentJourney);
                finish();
            }
        });

        Button cancel_btn = (Button) findViewById(R.id.cancel);
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(JourneyInformationActivity.this, SelectRouteActivity.class));
                finish();
            }
        });
    }
}