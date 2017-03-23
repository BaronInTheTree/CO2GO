package ca.cmpt276.carbonTracker.Internal_Logic;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.sasha.carbontracker.R;

/*
 * UtilityCollection class contains a list of utilities and a list of possible fuels.
 */
public class UtilityCollection {

    private static final String NATURAL_GAS = "Natural Gas";
    private static final String ELECTRICITY = "Electricity";
    private List<Utility> utilityList;
    private List<String> utilityFuelList;

    public UtilityCollection() {
        utilityList = new ArrayList<>();
        utilityFuelList = new ArrayList<>();
        populateUtilityData();
    }

    private void populateUtilityData() {
        utilityFuelList.add(NATURAL_GAS);
        utilityFuelList.add(ELECTRICITY);
    }

    public void addUtility(Utility utility) {
        utilityList.add(utility);
    }

    public Utility getUtility(int index) {
        return utilityList.get(index);
    }

    public void deleteUtility(int index) {
        utilityList.remove(index);
    }

    public int getSize(){
        return utilityList.size();
    }

    public List<Utility> getUtilityList(){
        return utilityList;
    }

    public double getTotalEmissionsForPeriod(int days) {
        double totalEmissions = 0;
        for (Utility utility : utilityList) {
            totalEmissions = totalEmissions + utility.getUsageForPeriod(days);
        }
        return totalEmissions;
    }

    public List<String> displayUtilityList() {
        List<String> utilities = new ArrayList<>();

        for (Utility utility : utilityList) {
            utilities.add(utility.displayToList());
        }
        return utilities;
    }

    public List<String> getUtilityFuelList() {
        return utilityFuelList;
    }

    public Utility getUtilityAtIndex(int index) {
        return utilityList.get(index);
    }

    private class UtilityCollectionAdapter extends ArrayAdapter<Utility> {
        UtilityCollectionAdapter(Context context) {
            super(context, R.layout.listview_format, utilityList);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View utilityView = convertView; // Check if we have a view
            if (utilityView == null) {
                utilityView = LayoutInflater.from(getContext()).inflate(R.layout.listview_format, parent, false);
            }

            Utility current = utilityList.get(position);
            TextView utilityText = (TextView) utilityView.findViewById(R.id.routeListViewText);
            utilityText.setText(current.displayToList());

            return utilityView;
        }
    }

    public ArrayAdapter<Utility> getArrayAdapter(Context context) {
        UtilityCollectionAdapter adapter = new UtilityCollectionAdapter(context);
        return adapter;
    }

    public int getNumberUtilities() {
        return utilityList.size();
    }
}