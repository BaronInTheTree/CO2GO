package ca.cmpt276.carbonTracker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import com.example.sasha.carbontracker.R;

public class SelectTransportationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_transportation);

        addBackButton();
        populateListView();
        addCarButton();
    }

    public void addBackButton() {
        Button btn = (Button) findViewById(R.id.buttonBack);
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
                Toast.makeText(SelectTransportationActivity.this,
                        "Index " + i,
                        Toast.LENGTH_LONG).show();

                startActivity(intent);
                finish();
            }
        });
    }

    public void addCarButton() {
        Button btn = (Button) findViewById(R.id.addCar_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = AddCarActivity.makeIntent(SelectTransportationActivity.this);
                startActivity(intent);
                finish();
            }
        });
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, SelectTransportationActivity.class);
    }
}