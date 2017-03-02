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

        setupButtons();
    }

    private void setupButtons() {
        Button toGraph_btn = (Button) findViewById(R.id.toGraph_btn);
        toGraph_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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

        /*
         Need a getJourneyDescriptionTableFormat() function: returns an array of strings.
         Each string contains: Trip date, routeName, Distance, Car NickName, Emission.
         Each section of the string has a specific length and 4 spaces in between :
         Number of characters of corresponding section: 10, 18, 10, 18, 8). This implies the length requirement:
         Route: max 18 chars, Distance: max 9999999.99, carName: max 18 chars, emission: 99999.99 (emission TBD)
          */
        String[] allJourneys = {"2017-01-01    Home_to_Work_Route     1234567890     my_Vehicle_Informa    12345678"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.table_layout,allJourneys);
        ListView list = (ListView)findViewById(R.id.tableFP);
        list.setAdapter(adapter);

    }
}
