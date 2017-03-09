package ca.cmpt276.carbonTracker;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
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

public class SelectRouteActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_ADD_ROUTE = 1000;
    private static final int REQUEST_CODE_EDIT_ROUTE = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_route);

        setupBackButton();
        addRouteButton();
        updateListView();
    }

    private void setupBackButton() {
        Button backButton = (Button) findViewById(R.id.backButtonRoute);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void addRouteButton() {
        Button btn = (Button) findViewById(R.id.addRoute_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(SelectRouteActivity.this, AddRouteActivity.class), REQUEST_CODE_ADD_ROUTE);
            }
        });
    }

    // TODO: get the proper list of routes from "outputRouteCollectionToString"
//    private void populateListView() {
//        // Create list of items
//        CarbonModel model = CarbonModel.getInstance();
//        List<String> routes = model.outputRouteCollectionToString();
//        // Build adapter
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
//                this,           // Context for activity
//                R.layout.listview_format,  // Layout to use (create)
//                routes);       // Items to be displayed
//
//        // config list view
//        ListView list = (ListView) findViewById(R.id.listOfRoutes);
//        list.setAdapter(adapter);
//
//        // Allows to click on list's items
//        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> a, View v, int i, long l) {
//                startActivity(new Intent(SelectRouteActivity.this, JourneyInformationActivity.class));
//            }
//        });
//    }

    public void updateListView() {
        CarbonModel carbonModel = CarbonModel.getInstance();
        ArrayAdapter<Route> adapter = carbonModel.getRouteCollection().getArrayAdapter(SelectRouteActivity.this);
        ListView list = (ListView) findViewById(R.id.listOfRoutes);
        list.clearChoices();
        list.setAdapter(adapter);
        registerClickCallback();
        registerForContextMenu(list);
    }

    private void registerClickCallback() {
        final CarbonModel model = CarbonModel.getInstance();
        ListView list = (ListView) findViewById(R.id.listOfRoutes);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int i, long l) {
                final Route routeToUse = model.getRouteCollection().getRouteAtIndex(i);
                AlertDialog.Builder builder = new AlertDialog.Builder(SelectRouteActivity.this);
                builder.setTitle("Confirm");
                builder.setMessage("Are you sure you want to use Route " +
                        routeToUse.getName() + "?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        model.setSelectedRoute(routeToUse);
                        startActivity(new Intent(SelectRouteActivity.this, JourneyInformationActivity.class));
                    }
                });
                builder.setNegativeButton("No", null);
                builder.show();
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (R.id.listOfRoutes == v.getId()) {
            menu.setHeaderTitle("Do what with route?");
            menu.add("Edit");
            menu.add("Delete");
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        CarbonModel cm = CarbonModel.getInstance();
        AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int selectedRoutePosition = acmi.position;
        cm.setSelectedRoute(cm.getRouteCollection().getRouteAtIndex(selectedRoutePosition));
        if (item.toString().equals("Edit")) {
            Intent editRouteIntent = EditRouteActivity.makeIntent(SelectRouteActivity.this);
            editRouteIntent.putExtra("RouteName", cm.getSelectedRoute().getName());
            editRouteIntent.putExtra("RouteCityKM", cm.getSelectedRoute().getCityDistanceKM());
            editRouteIntent.putExtra("RouteHighwayKM", cm.getSelectedRoute().getHighwayDistanceKM());
            editRouteIntent.putExtra("RouteIndex", selectedRoutePosition);
            startActivityForResult(editRouteIntent, REQUEST_CODE_EDIT_ROUTE);
        } else if (item.toString().equals("Delete")) {
            cm.getRouteCollection().hideRoute(cm.getSelectedRoute());
            cm.getRouteCollection().removeRoute(selectedRoutePosition);
        }
        updateListView();
        return true;
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, SelectRouteActivity.class);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_ADD_ROUTE || requestCode == REQUEST_CODE_EDIT_ROUTE) {
            updateListView();
        }
    }
}