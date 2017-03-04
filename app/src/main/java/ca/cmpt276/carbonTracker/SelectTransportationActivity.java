package ca.cmpt276.carbonTracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.sasha.carbontracker.R;

import java.util.List;

public class SelectTransportationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_transportation);

        // Remove after getting list to work
        testButton();

        populateListView();

        addCarButton();
    }

    public void testButton() {
        Button btn = (Button) findViewById(R.id.testCarList_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            startActivity(new Intent(SelectTransportationActivity.this, ModifyCarActivity.class));
            }
        });
    }

    // TODO: implement proper function for current "outputCollectionToString"
    private void populateListView() {
        // Create list of items
        CarbonModel model = CarbonModel.getInstance();
        List<String> cars = model.outputCollectionToString();
        // Build adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,           // Context for activity
                R.layout.visible_cars,  // Layout to use (create)
                cars);       // Items to be displayed

        // config list view
        ListView list = (ListView) findViewById(R.id.listOfCars);
        list.setAdapter(adapter);

        // Allows to click on list's items
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int i, long l) {
                startActivity(new Intent(SelectTransportationActivity.this, ModifyCarActivity.class));
            }
        });
    }

    public void addCarButton() {
        Button btn = (Button) findViewById(R.id.addCar_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SelectTransportationActivity.this, AddCarActivity.class));
            }
        });
    }
}