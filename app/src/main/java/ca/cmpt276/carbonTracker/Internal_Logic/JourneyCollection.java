package ca.cmpt276.carbonTracker.Internal_Logic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.sasha.carbontracker.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The JourneyCollection class stores journeys that the user has created and they are then displayed
 * in the Footprint activities.
 *
 * @author Team Teal
 */



public class JourneyCollection {
    public static final int maxNickName = 18;
    public static final int maxDistance = 5;
    public static final int maxEmission = 10;
    private static final String XsmallGap = "  ";
    private static final String smallGap = "   ";
    private static final String regularGap = "    ";
    private static final String largeGap = "                     ";

    private List<Journey> journeyCollection;

    public JourneyCollection() {
        journeyCollection = new ArrayList<>();
    }

    public void addJourney(Journey journey) {
        journeyCollection.add(journey);
        sortJourneysByDate();
    }

    public void deleteJourney(int index) {
        journeyCollection.remove(index);
        //CarbonModel model = CarbonModel.getInstance();
        //model.getDayDataCollection().updateJourneyDates();
    }

    private void sortJourneysByDate() {
        Collections.sort(journeyCollection);
    }

    public List<Journey> getJourneys() {return journeyCollection;}

    public int getSize(){
        return journeyCollection.size();
    }

    public double getTotalEmissionsKM() {
        double totalEmissions = 0;
        for (Journey journey : journeyCollection) {
            totalEmissions += journey.getEmissionsKM();
        }
        return totalEmissions;
    }

    public double getTotalEmissionsMiles() {
        double totalEmissions = 0;
        for (Journey journey : journeyCollection) {
            totalEmissions += journey.getEmissionsMiles();
        }
        return totalEmissions;
    }

    public Journey getLatestJourney() {
        if (getNumberJourneys() > 0) return journeyCollection.get(journeyCollection.size() - 1);
        else return null;
    }

    public int getNumberJourneys() {
        return journeyCollection.size();
    }

    public String[] getJourneyDescriptionsTableFormat() {
        int NumJourneys = getNumberJourneys();
        String[] descriptions = new String[NumJourneys];
        for (int i = 0; i < NumJourneys; i++) {
            Journey journey = journeyCollection.get(i);

            String car = journey.getTransportation().getNickname();
            for (int j = car.length(); j < maxNickName; j++) {
                car += XsmallGap;
            }

            String route = journey.getRoute().getName();
            for (int j = route.length(); j < maxNickName; j++) {
                route += smallGap;
            }

            String distance = "" + journey.getRoute().getTotalDistanceKM();
            for (int j = distance.length(); j < maxDistance; j++) {
                distance += smallGap;
            }

            String emission = "" + journey.getEmissionsKM();
            for (int j = emission.length(); j < maxEmission; j++) {
                emission += XsmallGap;
            }

            if (emission.length() > maxEmission) emission = emission.substring(0, maxEmission);

            String date = journey.getDateString();

            descriptions[i] = date + regularGap + route + regularGap + distance + largeGap + car + regularGap + emission;

        }
        return descriptions;
    }

    public String[] getJourneyDescription() {
        int NumJourneys = getNumberJourneys();
        String[] descriptions = new String[NumJourneys];
        for (int i = 0; i < NumJourneys; i++) {
            Journey journey = journeyCollection.get(i);
            String car = journey.getTransportation().getNickname();
            String route = journey.getRoute().getName();
            String distance = "" + journey.getRoute().getTotalDistanceKM();
            String emission = "" + journey.getEmissionsKM();
            String date = journey.getDateString();

            descriptions[i] = date + ", " + route + ", " + distance + ", " + car + ", " + emission;
        }
        return descriptions;
    }

    public int[] getJourneyEmission() {
        int NumJourneys = getNumberJourneys();
        int[] emissions = new int[NumJourneys];
        for (int i = 0; i < NumJourneys; i++) {
            Journey journey = journeyCollection.get(i);
            int emission = (int) journey.getEmissionsKM();
            emissions[i] = emission;
        }
        return emissions;
    }

    public List<String> getJourneyList() {
        List<String> journeys = new ArrayList<>();
        CarbonModel model = CarbonModel.getInstance();

        for (Journey journey : journeyCollection) {
            journeys.add(DateHandler.dateFormat.format(journey.getDateTime())
                    + "\n" + journey.getTransportation().getNickname()
                    + ", " + journey.getType()
                    + " || " + journey.getRoute().getName() + ": "
                    + journey.getRoute().getTotalDistanceKM() + "km"
                    + "\n" + String.format("%.2f", TreeUnit.convertToTrees(journey.getEmissionsKM()))
                    + model.getTreeUnit().getUnitTypeList());
        }
        return journeys;
    }

    public List<String> getJourneyListTrees() {
        List<String> journeys = new ArrayList<>();

        CarbonModel model = CarbonModel.getInstance();

        for (Journey journey : journeyCollection) {
            journeys.add(DateHandler.dateFormat.format(journey.getDateTime())
                    + "\n" + journey.getTransportation().getNickname()
                    + ", " + journey.getType()
                    + " || " + journey.getRoute().getName() + ": "
                    + journey.getRoute().getTotalDistanceKM() + "km"
                    + " || " + String.format("%.2f", TreeUnit.convertToTrees(journey.getEmissionsKM()))
                    + " " + model.getTreeUnit().getUnitTypeList());
        }
        return journeys;
    }

    public Journey getJourneyAtIndex(int index) {
        return journeyCollection.get(index);
    }

    public ArrayAdapter<Journey> getArrayAdapter(Context context) {
        JourneyCollection.JourneyCollectionAdapter adapter = new JourneyCollection.JourneyCollectionAdapter(context);
        return adapter;
    }

    private class JourneyCollectionAdapter extends ArrayAdapter<Journey> {
        JourneyCollectionAdapter(Context context) {
            super(context, R.layout.journey_list, journeyCollection);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View routeView = convertView; // Check if we have a view
            if (routeView == null) {
                routeView = LayoutInflater.from(getContext()).inflate(R.layout.journey_list, parent, false);
            }

            String current = getJourneyList().get(position);
            TextView routeText = (TextView) routeView.findViewById(R.id.journeyListViewText);
            routeText.setText(current);

            return routeView;
        }
    }
}