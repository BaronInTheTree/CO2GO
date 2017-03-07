package ca.cmpt276.carbonTracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sasha.carbontracker.R;

public class FootprintTableActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_footprint_table);

        populateListView();
        setupButtons();
        setupNote();
    }

    private void setupNote() {
        TextView note = (TextView) findViewById(R.id.note);
        note.setText("In portrait view, scroll sideways to read full content.");
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
        // String[] allJourneys = getJourneyDescriptionsTableFormat();
        String[] allJourneys = {"2017-01-01    Home_to_Work_Route     1234567890     my_Vehicle_Informa    12345678"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.table_layout, allJourneys);
        ListView list = (ListView) findViewById(R.id.tableFP);
        list.setAdapter(adapter);
    }
}