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

public class SelectRouteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_route);

        // to be removed later
        testButton();

        addRouteButton();

        populateListView();
    }

    private void testButton() {
        Button btn = (Button) findViewById(R.id.testRouteBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SelectRouteActivity.this, JourneyInfoActicity.class));
            }
        });
    }

    private void addRouteButton() {
        Button btn = (Button) findViewById(R.id.addRoute_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SelectRouteActivity.this, AddRouteActivity.class));
            }
        });
    }

    // TODO: get the proper list of routes from "outputRouteCollectionToString"
    private void populateListView() {
        // Create list of items
        CarbonModel model = CarbonModel.getInstance();
        List<String> routes = model.outputRouteCollectionToString();
        // Build adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,           // Context for activity
                R.layout.listview_format,  // Layout to use (create)
                routes);       // Items to be displayed

        // config list view
        ListView list = (ListView) findViewById(R.id.listOfRoutes);
        list.setAdapter(adapter);

        // Allows to click on list's items
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int i, long l) {
                startActivity(new Intent(SelectRouteActivity.this, JourneyInfoActicity.class));
            }
        });
    }

}
