package ca.cmpt276.carbonTracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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

    private void populateListOfUtilities() {

    }

    private void addUtilityBtn() {

    }

    private void backBtn() {

    }
}