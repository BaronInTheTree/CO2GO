package ca.cmpt276.carbonTracker;

import java.util.ArrayList;
import java.util.List;

/**
 * JourneyCollection class
 */

public class JourneyCollection {
    private List<Journey> journeyCollection;

    public JourneyCollection() {
        journeyCollection = new ArrayList<>();
    }

    public void addJourney(Journey journey) {
        journeyCollection.add(journey);
    }

    public void deleteJourney(Journey journey) {
        journeyCollection.remove(journey);
    }
    public void deleteJourneyAtIndex(int index) {
        journeyCollection.remove(index);
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

            String date = journey.getDate();

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
            String date = journey.getDate();

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

    public Journey getJourneyAtIndex(int index) {
        return journeyCollection.get(index);
    }
}