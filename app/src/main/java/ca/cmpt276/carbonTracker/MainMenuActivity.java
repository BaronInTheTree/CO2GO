package ca.cmpt276.carbonTracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.sasha.carbontracker.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        readVehicleDate();

        newJourneyButton();

        footPrintButton();

    }

    public void readVehicleDate() {
        InputStream is = getResources().openRawResource(R.raw.vehicles);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8"))
        );

        List<Car> carList = new ArrayList<>();
        String line = "";
        try {
            // Step over headers
            line = reader.readLine();

            // Read in file until empty
            while ((line = reader.readLine()) != null) {
                // Split by ','
                String[] tokens = line.split(",");

                // Read and store data
                String make = tokens[0];
                String model = tokens[1];
                int year = Integer.parseInt(tokens[2]);
                int highwayMPG = Integer.parseInt(tokens[3]);
                int cityMPG = Integer.parseInt(tokens[4]);
                Car car = new Car(make, model, year, highwayMPG, cityMPG);
                carList.add(car);
            }
        } catch (IOException e) {
            Log.i("MyActivity", "Error reading data from file on line" + line, e);
            e.printStackTrace();
        }
        System.out.println("carList size: " + carList.size());
    }

    public void newJourneyButton() {
        Button journey_btn = (Button) findViewById(R.id.newJourney_btn);
        journey_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainMenuActivity.this, SelectTransportationActivity.class));
            }
        });
    }

    public void footPrintButton() {
        Button footprint_btn = (Button) findViewById(R.id.footprint_btn);
        footprint_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainMenuActivity.this, FootprintTableActivity.class));
            }
        });
    }
}