package ca.cmpt276.carbonTracker.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sasha.carbontracker.R;

import ca.cmpt276.carbonTracker.Internal_Logic.CarbonModel;
import ca.cmpt276.carbonTracker.Internal_Logic.SaveData;

/**
 * FootprintTableActivity displays the journey collection in a side-by-side comparison so that the
 * user and see and compare how much CO2 they have used when doing the journeys they have created.
 * The user is also able to see a graph view of this by clicking a button to open a new activity.
 *
 * @author Team Teal
 */
public class FootprintTableActivity extends AppCompatActivity {
    private static String userNotes = "In portrait view, scroll sideways to read full content.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_footprint_table);
        SaveData.loadJourneys(FootprintTableActivity.this);
        populateListView();
        setupButtons();
        setupNote();
    }

    private void setupNote() {
        TextView note = (TextView) findViewById(R.id.note);
        note.setText(userNotes);
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
        CarbonModel currentInstance = CarbonModel.getInstance();
        String[] allJourneys = currentInstance.getJourneyCollection().getJourneyDescriptionsTableFormat();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.table_layout, allJourneys);
        ListView list = (ListView) findViewById(R.id.tableFP);
        list.setAdapter(adapter);
    }
}