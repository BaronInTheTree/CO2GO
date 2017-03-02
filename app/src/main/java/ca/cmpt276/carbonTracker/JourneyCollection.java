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

    public int getNumberJourneys(){
        return journeyCollection.size();
    }

    public String[] getJourneyDescriptionsTableFormat(){
        int NumJourneys = getNumberJourneys();
        String[] descriptions = new String[NumJourneys];
        for (int i = 0; i < NumJourneys; i++) {
            Journey journey = journeyCollection.get(i);

            String car = journey.getCar().getNickname();
            for (int j=car.length();j<18;j++){
                car+=" ";
            }

            String route = journey.getRoute().getName();
            for (int j=route.length();j<18;j++){
                route+=" ";
            }

            String distance = "" + journey.getRoute().getTotalDistanceKM();
            for (int j=distance.length();j<10;j++){
                distance+=" ";
            }

            String emission = "" + journey.getEmissionsKM();
            for (int j=emission.length();j<8;j++){
                emission+=" ";
            }

            // need to figure out how to get date in journey class, then update the following code.
            String date = "2017-01-01";

            descriptions[i]=date+"    "+route+"    "+distance+"    "+car+"    "+emission;
        }

        return descriptions;
    }

    public String[] getJourneyDescription(){
        int NumJourneys = getNumberJourneys();
        String[] descriptions = new String[NumJourneys];
        for (int i = 0; i < NumJourneys; i++) {
            Journey journey = journeyCollection.get(i);
            String car = journey.getCar().getNickname();
            String route = journey.getRoute().getName();
            String distance = "" + journey.getRoute().getTotalDistanceKM();
            String emission = "" + journey.getEmissionsKM();
            // need to figure out how to get date in journey class, then update the following code.
            String date = "2017-01-01";

            descriptions[i]=date+", "+route+", "+distance+", "+car+", "+emission;
        }
        return descriptions;
    }

    public String[] getJourneyEmission(){
        int NumJourneys = getNumberJourneys();
        String[] emissions = new String[NumJourneys];
        for (int i = 0; i < NumJourneys; i++) {
            Journey journey = journeyCollection.get(i);
            String emission = "" + journey.getEmissionsKM();
            emissions[i]=emission;
        }
        return emissions;
    }



}
