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

/*
 * UtilityListActivity class lists all created utilities to screen displaying the nickname of
 * utility and its fuel type.
 * Activity allows the user to add a new utility or return to main menu.
 */
public class UtilityListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_utility_list);

        populateListOfUtilities();
        addUtilityBtn();
        backBtn();
    }

    // TODO: Add long press functionality
    private void populateListOfUtilities() {
        // Create list of items
        final CarbonModel model = CarbonModel.getInstance();
        List<String> utilities = model.getUtilityCollection().displayUtilityList();

        // Build adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,                       // Context for activity
                R.layout.listview_format,   // Layout to use (create)
                utilities);                 // Items to be displayed

        // config list view
        ListView list = (ListView) findViewById(R.id.listOfUtilities);
        list.setAdapter(adapter);

        // Allows to click on list's items
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int i, long l) {
                model.getUtilityCollection().getUtility(i);

                Intent intent = new Intent(UtilityListActivity.this, UtilitySummaryActivity.class);
                intent.putExtra("Index", i);
                startActivity(intent);
                finish();
            }
        });
    }

    private void addUtilityBtn() {
        Button btn = (Button) findViewById(R.id.addUtility_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UtilityListActivity.this, AddUtilityActivity.class));
                finish();
            }
        });
    }

    private void backBtn() {
        Button btn = (Button) findViewById(R.id.backButtonUtilityList);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UtilityListActivity.this, MainMenuActivity.class));
                finish();
            }
        });
    }
}