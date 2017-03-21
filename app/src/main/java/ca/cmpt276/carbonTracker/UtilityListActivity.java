package ca.cmpt276.carbonTracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.sasha.carbontracker.R;

public class UtilityListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_utility_list);

        populateListOfUtilities();
        addUtilityBtn();
        backBtn();
    }

    // TODO: complete this
    private void populateListOfUtilities() {

    }

    private void addUtilityBtn() {
        Button btn = (Button) findViewById(R.id.addUtility_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UtilityListActivity.this, AddBillActivity.class));
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