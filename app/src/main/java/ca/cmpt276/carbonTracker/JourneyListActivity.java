package ca.cmpt276.carbonTracker;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.sasha.carbontracker.R;

import java.util.List;

public class JourneyListActivity extends AppCompatActivity {

    CarbonModel modelInstance = CarbonModel.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journey_list);
        SaveData.loadJourneys(JourneyListActivity.this);
        populateJourneyList();
        setupBackButton();
    }

    private void populateJourneyList() {
        // Create list of items
        final List<String> journeyList = modelInstance.getJourneyCollection().getJourneyList();
        // Build adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,           // Context for activity
                R.layout.journey_list,  // Layout to use (create)
                journeyList);       // Items to be displayed
        // config list view
        final ListView list = (ListView) findViewById(R.id.listViewJourneys);
        list.setAdapter(adapter);
        // Allows to click on list's items
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int i, long l) {
                Intent intent = new Intent(JourneyListActivity.this, EditJourneyActivity.class);
                intent.putExtra("Index", i);
                System.out.println("Index: " + i);
                startActivity(intent);
                finish();
            }

        });
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final int index = position;
                modelInstance.getJourneyCollection().deleteJourney(index);
                SaveData.saveJourneys(JourneyListActivity.this);
                populateJourneyList();
                return false;
            }
        });
    }

    private void setupBackButton() {
        Button back = (Button) findViewById(R.id.buttonBack_journeyList);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(JourneyListActivity.this, MainMenuActivity.class));
                finish();
            }
        });
    }
}
