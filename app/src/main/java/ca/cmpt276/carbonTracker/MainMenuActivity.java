package ca.cmpt276.carbonTracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.sasha.carbontracker.R;

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        newJourneyButton();

        footPrintButton();
    }

    private void newJourneyButton() {
        Button journey_btn = (Button) findViewById(R.id.newJourney_btn);
        journey_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainMenuActivity.this, SelectTransportationActivity.class));
            }
        });
    }

    private void footPrintButton() {
        Button footprint_btn = (Button) findViewById(R.id.footprint_btn);
        footprint_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainMenuActivity.this, FootprintTableActivity.class));
            }
        });
    }
}