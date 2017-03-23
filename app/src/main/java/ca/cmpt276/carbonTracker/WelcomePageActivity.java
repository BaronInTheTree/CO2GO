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

/**
 * The WelcomePageActivity displays an image for the user at startup and allows them to proceed
 * to the main menu of the application. It is automatically closed when the user does so.
 *
 * @author Team Teal
 */

public class WelcomePageActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);

        enterButton();
        SaveData.loadAllRoutes(this);
        SaveData.loadAllCars(this);
        SaveData.loadJourneys(this);
        //SaveData.loadUtilities(this);
        SaveData.loadTips(this);
        new Thread(new Runnable() {
            @Override
            public void run() {
                readVehicleDate();
            }
        }).start();
    }

    private void enterButton() {
        Button btn = (Button) findViewById(R.id.enter_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WelcomePageActivity.this, MainMenuActivity.class));
                finish();
            }
        });
    }

    private void readVehicleDate() {
        CarbonModel modelInstance = CarbonModel.getInstance();

        InputStream is = getResources().openRawResource(R.raw.vehiclesnew); // CHANGED
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8"))
        );

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
                String trany = tokens[5];
                int cylinders = Integer.parseInt(tokens[6]);
                double displacement = Double.parseDouble(tokens[7]);
                String fuelType = tokens[8];
                Car car = new Car(make, model, year, highwayMPG, cityMPG, trany, cylinders, displacement, fuelType);
                modelInstance.getCarData().getCarDataList().add(car);
            }
        } catch (IOException e) {
            Log.i("MyActivity", "Error reading data from file on line" + line, e);
            e.printStackTrace();
        }
        modelInstance.getCarData().initializeMakeList();
        modelInstance.getCarData().updateModelList("Toyota");
        modelInstance.getCarData().updateYearList("Toyota", "Yaris");
        System.out.println("carList size: " + modelInstance.getCarData().getCarDataList().size());
    }
}