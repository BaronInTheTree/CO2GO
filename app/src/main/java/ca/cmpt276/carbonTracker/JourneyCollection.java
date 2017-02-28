package ca.cmpt276.carbonTracker;

import java.util.ArrayList;

/**
 * Created by song on 2017-02-27.
 */

public class JourneyCollection {
    private ArrayList<Journey> journeyCollection;

    public JourneyCollection(){
        journeyCollection = new ArrayList<>();
    }

    public void addJourney(Journey journey){
        journeyCollection.add(journey);
    }

    public void deleteJourney(Journey journey){
        journeyCollection.remove(journey);
    }

    public double getTotalEmissionsKM(){
        double totalEmissions = 0;
        for (Journey journey : journeyCollection){
            totalEmissions += journey.getEmissionsKM();
        }
        return totalEmissions;
    }

    public double getTotalEmissionsMiles(){
        double totalEmissions = 0;
        for (Journey journey : journeyCollection){
            totalEmissions += journey.getEmissionsMiles();
        }
        return totalEmissions;
    }



}
