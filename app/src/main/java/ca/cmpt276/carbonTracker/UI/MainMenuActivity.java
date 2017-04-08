package ca.cmpt276.carbonTracker.UI;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.example.sasha.carbontracker.R;

import ca.cmpt276.carbonTracker.AlternateUI.MainMenuActivity_Alternate;
import ca.cmpt276.carbonTracker.Internal_Logic.CarbonModel;
import ca.cmpt276.carbonTracker.Internal_Logic.MonthYearSummary;
import ca.cmpt276.carbonTracker.Internal_Logic.SaveData;

/**
 * From the MainMenuActivity, the user is able to view tips, start a new journey, or view a collection
 * of journeys that they created onto a graph.
 *
 * @author Team Teal
 */
public class MainMenuActivity extends AppCompatActivity {

    CarbonModel modelInstance = CarbonModel.getInstance();
    MonthYearSummary summary = modelInstance.getSummary();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        setUpButtons();
        setUpToggle();
        SaveData.loadTips(this);
    }

    private void setUpToggle() {
        Switch unitToggle = (Switch) findViewById(R.id.treeUnitToggle);
        if (CarbonModel.getInstance().getTreeUnit().getTreeUnitStatus() == true) {
            unitToggle.setChecked(true);
        }
        unitToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    CarbonModel.getInstance().getTreeUnit().setTreeUnitStatus(true);
                    Toast.makeText(MainMenuActivity.this, "on", Toast.LENGTH_SHORT).show();
                    SaveData.saveTreeUnit(MainMenuActivity.this);
                } else {
                    CarbonModel.getInstance().getTreeUnit().setTreeUnitStatus(false);
                    Toast.makeText(MainMenuActivity.this, "off", Toast.LENGTH_SHORT).show();
                    SaveData.saveTreeUnit(MainMenuActivity.this);
                }
            }
        });
    }

    private void setUpButtons() {
        Button journey_btn = (Button) findViewById(R.id.newJourney_btn);
        journey_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainMenuActivity.this, SelectTransportationActivity.class));
            }
        });

        Button utilities_btn = (Button) findViewById(R.id.button_UtilityList);
        utilities_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainMenuActivity.this, UtilityListActivity.class));
            }
        });

        Button footprint_btn = (Button) findViewById(R.id.footprint_btn);
        footprint_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainMenuActivity.this, GraphMenuActivity.class));
            }
        });

        Button tip_btn = (Button) findViewById(R.id.tip_btn);
        tip_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = modelInstance.getTips().getGeneralTip(getApplicationContext(),summary);
                Toast.makeText(MainMenuActivity.this, message, Toast.LENGTH_LONG).show();
                SaveData.saveTips(MainMenuActivity.this);
//                startActivity(new Intent(MainMenuActivity.this, MonthlyEmissionGraphActivity.class));
//                startActivity(new Intent(MainMenuActivity.this, YearlyEmissionLineGraphActivity.class));
                startActivity(new Intent(MainMenuActivity.this, MainMenuActivity_Alternate.class));
            }
        });

        Button journeyList_btn = (Button) findViewById(R.id.viewJourneys_btn);
        journeyList_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainMenuActivity.this, JourneyListActivity.class));
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

    // Test button code for easy testing of model on MainMenuActivity
    /*
    private void setupTestButton() {
        Button test = (Button) findViewById(R.id.buttonTest);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<DayData> dayDataList = DayData.getDayDataWithinInterval(DateHandler.createDate(2016, 03, 20), new Date());
                String info = "";
                for (DayData dayData : dayDataList) {
                    info = info + dayData.getInfo();
                }
                Toast.makeText(MainMenuActivity.this, info, Toast.LENGTH_LONG).show();
            }
        });
    }
    */
}