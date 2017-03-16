package ca.cmpt276.carbonTracker;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.sasha.carbontracker.R;

public class MainMenuActivity extends AppCompatActivity {

    private CarbonModel modelInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        modelInstance = CarbonModel.getInstance();

        setUpButtons();

    }

    private void setUpButtons() {
        Button journey_btn = (Button) findViewById(R.id.newJourney_btn);
        journey_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainMenuActivity.this, SelectTransportationActivity.class));
            }
        });

        Button footprint_btn = (Button) findViewById(R.id.footprint_btn);
        footprint_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainMenuActivity.this, FootprintTableActivity.class));
            }
        });

        Button tip_btn = (Button) findViewById(R.id.tip_btn);
        tip_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = modelInstance.getTips().getGeneralTip();
                Toast.makeText(MainMenuActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    public void onBackPressed() {
        int backStackEntryCount = getSupportFragmentManager().getBackStackEntryCount();
        if (backStackEntryCount == 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainMenuActivity.this);
            builder.setTitle("Warning");
            builder.setMessage("Are you sure you want to quit the app?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            builder.setNegativeButton("No", null);
            builder.show();
        } else {
            finish();
        }
    }
}