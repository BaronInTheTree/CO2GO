package ca.cmpt276.carbonTracker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * The JourneyCollection class stores journeys that the user has created and they are then displayed
 * in the Footprint activities.
 *
 * @author Team Teal
 */

public class JourneyCollection {
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
    }

    private void sortJourneysByDate() {
        Collections.sort(journeyCollection);
    }

    public void editJourney(int index) {
        journeyCollection.get(index);
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
            for (int j = car.length(); j < 18; j++) {
                car += "  ";
            }

            String route = journey.getRoute().getName();
            for (int j = route.length(); j < 18; j++) {
                route += "   ";
            }

            String distance = "" + journey.getRoute().getTotalDistanceKM();
            for (int j = distance.length(); j < 4; j++) {
                distance += "   ";
            }

            String emission = "" + journey.getEmissionsKM();
            for (int j = emission.length(); j < 8; j++) {
                emission += "  ";
            }

            if (emission.length() > 10) emission = emission.substring(0, 10);

            String date = journey.getDateString();

            descriptions[i] = date + "    " + route + "    " + distance + "                     " + car + "    " + emission;
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

        for (Journey journey : journeyCollection) {
            journeys.add(journey.getDateString()
                    + "\n" + journey.getTransportation().getNickname()
                    + ", " + journey.getType()
                    + "\n" + journey.getRoute().getName() + ": "
                    + journey.getRoute().getTotalDistanceKM() + "km"
                    + "\n" + journey.getEmissionsKM() + "g CO2");
        }
        return journeys;
    }

    public Journey getJourneyAtIndex(int index) {
        return journeyCollection.get(index);
    }
}