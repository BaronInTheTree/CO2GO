package ca.cmpt276.carbonTracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.sasha.carbontracker.R;

public class JourneyInfoActicity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journey_info_acticity);

        setupInfo();

        setupButtons();
    }


    private void setupInfo() {
        // The following two lines are commented out until real instance is created.
        /*
        CarbonModel currentInstance = CarbonModel.getInstance();
        Journey currentJourney = currentInstance.getCurrentJourney();
        */

        //use testing data (the following three lines) now:
        Car test_car = new Car("Toyota","Camry", 2000, 40.0, 60.0, "Manual", 4, 5.0, "Regular Gasoline");
        test_car.setNickname("test_car");
        Route test_route = new Route("test_route", 100.0, 50.0);
        Journey currentJourney = new Journey(test_car,test_route);

        TextView date = (TextView)findViewById(R.id.date_entry);
        date.setText(currentJourney.getDate());

        TextView vehicle = (TextView)findViewById(R.id.vehicle_entry);
        vehicle.setText(currentJourney.getCar().getNickname());

        TextView route = (TextView)findViewById(R.id.route_entry);
        route.setText(currentJourney.getRoute().getName());

        TextView distance = (TextView)findViewById(R.id.distance_entry);
        distance.setText(""+currentJourney.getRoute().getTotalDistanceKM());

        TextView emission = (TextView)findViewById(R.id.emission_entry);
        emission.setText(""+currentJourney.getEmissionsKM());

    }

    private void setupButtons() {
        Button confirm_btn = (Button) findViewById(R.id.confirm);

        confirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(JourneyInfoActicity.this,MainMenuActivity.class));
            }
        });
    }
}
