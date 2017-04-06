package ca.cmpt276.carbonTracker.UI;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

import com.example.sasha.carbontracker.R;

import ca.cmpt276.carbonTracker.AlternateUI.AddCarActivity;
import ca.cmpt276.carbonTracker.Internal_Logic.CarbonModel;
import ca.cmpt276.carbonTracker.Internal_Logic.SaveData;

/**
 * SelectTransportationActivity lists the different types of cars that user has created
 * in a ListView and also the options to use Walking/Biking, Skytrain, or Bus. They are also
 * able to add a car into the carCollection in the Carbon Model here as well.
 *
 * @author Team Teal
 */

public class SelectTransportationActivity extends AppCompatActivity {

    CarbonModel modelInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_transportation);
        modelInstance = CarbonModel.getInstance();

        SaveData.loadAllCars(SelectTransportationActivity.this);
        addBackButton();
        populateListView();
        addCarButton();
        addWalkBikeButton();
        addBusButton();
        addSkytrainButton();
    }

    private void addBackButton() {
        Button btn = (Button) findViewById(R.id.buttonBack_journeyList);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void populateListView() {
        // Create list of items
        final CarbonModel model = CarbonModel.getInstance();
        List<String> cars = model.getCarCollection().getUICollection();
        // Build adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,           // Context for activity
                R.layout.listview_format,  // Layout to use (create)
                cars);       // Items to be displayed
        // config list view
        ListView list = (ListView) findViewById(R.id.listOfCars);
        list.setAdapter(adapter);
        // Allows to click on list's items
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int i, long l) {
                model.setSelectedCar(model.getCarCollection().getCarAtIndex(i));
                Intent intent = ModifyCarActivity.makeIntent(SelectTransportationActivity.this);
                intent.putExtra("Index", i);
                modelInstance.setSelectedTransportType("Car");
                startActivity(intent);
                finish();
            }
        });
    }

    private void addCarButton() {
        Button btn = (Button) findViewById(R.id.addCar_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = AddCarActivity.makeIntent(SelectTransportationActivity.this);
                modelInstance.setSelectedTransportType("Car");
                startActivity(intent);
                finish();
            }
        });
    }

    private void addWalkBikeButton() {
        Button walkBike = (Button) findViewById(R.id.buttonWalkBikeMode);
        walkBike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = SelectRouteActivity.makeIntent(SelectTransportationActivity.this);
                modelInstance.setSelectedTransportType("WalkBike");
                startActivity(intent);
                finish();
            }
        });
    }

    private void addBusButton() {
        Button bus = (Button) findViewById(R.id.buttonBus);
        bus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = SelectRouteActivity.makeIntent(SelectTransportationActivity.this);
                modelInstance.setSelectedTransportType("Bus");
                startActivity(intent);
                finish();
            }
        });
    }

    private void addSkytrainButton() {
        Button skytrain = (Button) findViewById(R.id.buttonSkyTrain);
        skytrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = SelectRouteActivity.makeIntent(SelectTransportationActivity.this);
                modelInstance.setSelectedTransportType("Skytrain");
                startActivity(intent);
                finish();
            }
        });
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, SelectTransportationActivity.class);
    }
}