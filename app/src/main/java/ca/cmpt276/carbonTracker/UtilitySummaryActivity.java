package ca.cmpt276.carbonTracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.sasha.carbontracker.R;

/*
 * UtilitySummaryActivity class displays all information about a specific utility
 * to screen via text with an "ok" button allowing the user to return to previous page.
 */
public class UtilitySummaryActivity extends AppCompatActivity {

    private int index;
    private CarbonModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_utility_summary);

        model = CarbonModel.getInstance();

        // Get specific utility
        index = (Integer) getIntent().getSerializableExtra("Index");
        Utility utility = model.getUtilityCollection().getUtility(index);

        setupText(utility);
        okayButton();
    }

    private void setupText(Utility utility) {
        // Set nickname
        TextView nickname = (TextView) findViewById(R.id.utilityNickname);
        nickname.setText(utility.getNickname());

        // Set fuel
        TextView fuel = (TextView) findViewById(R.id.utilitySummaryFuel);
        if (utility.isNaturalGas()) {
            fuel.setText("Natural Gas");
        } else {
            fuel.setText("Electricity");
        }

        // Set usage
        TextView usage = (TextView) findViewById(R.id.utilitySummaryUsage);
        usage.setText("" + utility.getUsage());

        // Set starting date
        TextView startingDate = (TextView) findViewById(R.id.utilitySummaryStartingDate);
        startingDate.setText(utility.getStartDate());

        // Set ending date
        TextView endingDate = (TextView) findViewById(R.id.utilitySummaryEndingDate);
        endingDate.setText(utility.getEndDate());

        // Set num of people
        TextView numPeople = (TextView) findViewById(R.id.utilitySummaryNumPeople);
        numPeople.setText("" + utility.getNumPeople());
    }

    private void okayButton() {
        Button btn = (Button) findViewById(R.id.utilitySummaryOkayButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UtilitySummaryActivity.this, UtilityListActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}