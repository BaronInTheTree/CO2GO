package ca.cmpt276.carbonTracker.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.sasha.carbontracker.R;

import ca.cmpt276.carbonTracker.AlternateUI.AddCarActivity;
import ca.cmpt276.carbonTracker.AlternateUI.AddJourneyActivity_Alternate;
import ca.cmpt276.carbonTracker.AlternateUI.AddRouteActivity;
import ca.cmpt276.carbonTracker.AlternateUI.AddUtilityActivity;
import ca.cmpt276.carbonTracker.AlternateUI.MainMenuActivity_Alternate;
import ca.cmpt276.carbonTracker.Internal_Logic.CarbonModel;
import ca.cmpt276.carbonTracker.Internal_Logic.SaveData;
import ca.cmpt276.carbonTracker.Internal_Logic.Utility;

/*
 * UtilitySummaryActivity class displays all information about a specific utility
 * to screen via text and allows the user to return to previous page.
 */
public class UtilitySummaryActivity extends AppCompatActivity {

    private int index;
    private CarbonModel model;
    private ActionMenuView amvMenu;

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
        setupActionBar();
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
        startingDate.setText(utility.getStartDateString());

        // Set ending date
        TextView endingDate = (TextView) findViewById(R.id.utilitySummaryEndingDate);
        endingDate.setText(utility.getEndDateString());

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

    private void setupActionBar() {
        // Inflate your custom layout
        Toolbar toolBar = (Toolbar) findViewById(R.id.mainMenu_toolBar);
        amvMenu = (ActionMenuView) toolBar.findViewById(R.id.amvMenu);
        amvMenu.setOnMenuItemClickListener(new ActionMenuView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                return onOptionsItemSelected(menuItem);
            }
        });
        setSupportActionBar(toolBar);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_actions_mainmenu, amvMenu.getMenu());
        MenuItem itemSwitch = amvMenu.getMenu().findItem(R.id.toolbar_switch);
        itemSwitch.setActionView(R.layout.switch_layout);

        final SwitchCompat unitToggle = (SwitchCompat) amvMenu.getMenu().findItem(R.id.toolbar_switch).
                getActionView().findViewById(R.id.switchForActionBar);
        if (CarbonModel.getInstance().getTreeUnit().getTreeUnitStatus()) {
            unitToggle.setChecked(true);
        }
        unitToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    CarbonModel.getInstance().getTreeUnit().setTreeUnitStatus(true);
                    SaveData.saveTreeUnit(UtilitySummaryActivity.this);
                } else {
                    CarbonModel.getInstance().getTreeUnit().setTreeUnitStatus(false);
                    SaveData.saveTreeUnit(UtilitySummaryActivity.this);
                }
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch(id) {
            case R.id.action_settings: {
                return true;
            }
            case R.id.action_addVehicle: {
                Intent intent = new Intent(UtilitySummaryActivity.this, AddCarActivity.class);
                intent.putExtra("Caller", "UtilitySummary");
                startActivity(intent);
                finish();
                return true;
            }
            case R.id.action_addRoute: {
                Intent intent = new Intent(UtilitySummaryActivity.this, AddRouteActivity.class);
                intent.putExtra("Caller", "UtilitySummary");
                startActivity(intent);
                finish();
                return true;
            }
            case R.id.action_addUtility: {
                Intent intent = new Intent(UtilitySummaryActivity.this, AddUtilityActivity.class);
                intent.putExtra("Caller", "UtilitySummary");
                startActivity(intent);
                finish();
                return true;
            }
            case R.id.action_addJourney: {
                Intent intent = new Intent(UtilitySummaryActivity.this, AddJourneyActivity_Alternate.class);
                intent.putExtra("Caller", "UtilitySummary");
                startActivity(intent);
                finish();
                return true;
            }
            case android.R.id.home: {
                Intent intent = new Intent(UtilitySummaryActivity.this, MainMenuActivity_Alternate.class);
                startActivity(intent);
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}