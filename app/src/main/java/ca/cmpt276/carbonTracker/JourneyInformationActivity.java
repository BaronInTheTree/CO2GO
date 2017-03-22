package ca.cmpt276.carbonTracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sasha.carbontracker.R;

import static ca.cmpt276.carbonTracker.JourneyCollection.maxEmission;

/**
 * JourneyInformationActivity displays a quick summary of the users current journey that they
 * have created.
 *
 * @author Team Teal
 */
public class JourneyInformationActivity extends AppCompatActivity {

    CarbonModel currentInstance = CarbonModel.getInstance();
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
        date.setText(currentJourney.getDateString());

        TextView vehicle = (TextView) findViewById(R.id.vehicle_entry);
        String vehicleName = currentJourney.getTransportation().getNickname();
        vehicle.setText(vehicleName);

        TextView route = (TextView) findViewById(R.id.route_entry);
        String routeName = currentJourney.getRoute().getName();
        route.setText(routeName);

        TextView distance = (TextView) findViewById(R.id.distance_entry);
        String distanceInfo = "" + currentJourney.getRoute().getTotalDistanceKM();
        distance.setText(distanceInfo);

        TextView emission = (TextView) findViewById(R.id.emission_entry);
        String emissionInfo = "" + currentJourney.getEmissionsKM();
        if (emissionInfo.length() > maxEmission) emissionInfo = emissionInfo.substring(0, maxEmission);
        emission.setText(emissionInfo);
    }

    private void setupButtons() {
        Button confirm_btn = (Button) findViewById(R.id.confirm);
        confirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentInstance.addNewJourney(currentJourney);
                SaveData.saveJourneys(JourneyInformationActivity.this);
                int type = currentJourney.getTransportType();
                // create tip based on the type of transportation and the information within current Journey.
                String message = currentInstance.getTips().getJourneyTip(type, currentJourney);
                Toast.makeText(JourneyInformationActivity.this, message, Toast.LENGTH_LONG).show();
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