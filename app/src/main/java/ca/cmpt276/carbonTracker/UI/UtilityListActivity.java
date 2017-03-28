package ca.cmpt276.carbonTracker.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.sasha.carbontracker.R;

import java.util.List;

import ca.cmpt276.carbonTracker.Internal_Logic.*;

/*
 * UtilityListActivity class lists all created utilities to screen displaying the nickname of
 * utility and its fuel type.
 * Activity allows the user to add a new utility or return to main menu.
 */
public class UtilityListActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_EDIT_UTILITY = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_utility_list);

        SaveData.loadUtilities(this);
        populateListOfUtilities();
        addUtilityBtn();
        backBtn();
        updateListView();
    }

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
                finish();
            }
        });
    }

    public void updateListView() {
        CarbonModel carbonModel = CarbonModel.getInstance();
        ArrayAdapter<Utility> adapter = carbonModel.getUtilityCollection().getArrayAdapter(UtilityListActivity.this);
        ListView list = (ListView) findViewById(R.id.listOfUtilities);
        list.clearChoices();
        list.setAdapter(adapter);
        registerForContextMenu(list);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (R.id.listOfUtilities == v.getId()) {
            menu.setHeaderTitle("Do what with utility?");
            menu.add("Edit");
            menu.add("Delete");
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        CarbonModel cm = CarbonModel.getInstance();
        AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int selectedUtilityPosition = acmi.position;
        if (item.toString().equals("Edit")) {
            Intent editUtilityIntent = EditUtilityActivity.makeIntent(UtilityListActivity.this);
            editUtilityIntent.putExtra("UtilityIndex", selectedUtilityPosition);
            startActivityForResult(editUtilityIntent, REQUEST_CODE_EDIT_UTILITY);
            finish();
        } else if (item.toString().equals("Delete")) {
            cm.getUtilityCollection().deleteUtility(selectedUtilityPosition);
            SaveData.saveUtilities(this);
        }
        updateListView();
        return true;
    }
}