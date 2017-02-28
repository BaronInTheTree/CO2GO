package ca.cmpt276.carbonTracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.sasha.carbontracker.R;

public class FootprintTableActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_footprint_table);

        populateListView();

        setUpButtons();
    }

    private void setUpButtons() {
        Button toGraph_btn = (Button) findViewById(R.id.toGraph_btn);
        toGraph_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FootprintTableActivity.this, FootprintGraphActivity.class));
            }
        });

        Button back_btn = (Button) findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private void populateListView() {
        String[] allJourneys = {"Route 1      1000km        myVehicle      589.75"
                , "Route 2      200.34km     myVehicle      123.75"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.table_layout,allJourneys);
        ListView list = (ListView)findViewById(R.id.tableFP);
        list.setAdapter(adapter);

    }
}
