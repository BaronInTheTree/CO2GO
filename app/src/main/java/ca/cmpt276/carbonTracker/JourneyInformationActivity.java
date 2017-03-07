package ca.cmpt276.carbonTracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.sasha.carbontracker.R;

public class JourneyInformationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journey_information);

        // The following lines are commented out until real instance is created.
        /*
        CarbonModel currentInstance = CarbonModel.getInstance();
        Car newCar = currentInstance.getCurrentCar();
        Route newRoute = currentInstance.getCurrentRoute();
        Journey currentJourney = new Journey (newCar, newRoute);
        */

        setupInfo();
        setupButtons();
    }

    private void setupInfo() {
        //use testing data (the following three lines) now:
        Car test_car = new Car("Toyota", "Camry", 2000, 40.0, 60.0, "Manual", 4, 5.0, "Regular Gasoline");
        test_car.setNickname("test_car");
        Route test_route = new Route("test_route", 100.0, 50.0);
        Journey currentJourney = new Journey(test_car, test_route);

        TextView date = (TextView) findViewById(R.id.date_entry);
        date.setText(currentJourney.getDate());

        TextView vehicle = (TextView) findViewById(R.id.vehicle_entry);
        String vehicleName = currentJourney.getCar().getNickname();
        if (vehicleName.length() > 18) vehicleName = vehicleName.substring(0, 18);
        vehicle.setText(vehicleName);

        TextView route = (TextView) findViewById(R.id.route_entry);
        String routeName = currentJourney.getRoute().getName();
        if (routeName.length() > 18) routeName = routeName.substring(0, 18);
        route.setText(routeName);

        TextView distance = (TextView) findViewById(R.id.distance_entry);
        String distanceInfo = "" + currentJourney.getRoute().getTotalDistanceKM();
        if (distanceInfo.length() > 10) distanceInfo = distanceInfo.substring(0, 10);
        distance.setText(distanceInfo);

        TextView emission = (TextView) findViewById(R.id.emission_entry);
        String emissionInfo = "" + currentJourney.getEmissionsKM();
        if (emissionInfo.length() > 8) emissionInfo = emissionInfo.substring(0, 8);
        emission.setText(emissionInfo);
    }

    private void setupButtons() {
        Button confirm_btn = (Button) findViewById(R.id.confirm);

        confirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //currentInstance.addNewJourney(currentJourney); //uncomment until real data loaded.
                finish();
                startActivity(new Intent(JourneyInformationActivity.this, MainMenuActivity.class));
            }
        });

        Button cancel_btn = (Button) findViewById(R.id.cancel);
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(JourneyInformationActivity.this, MainMenuActivity.class));
            }
        });
    }
}