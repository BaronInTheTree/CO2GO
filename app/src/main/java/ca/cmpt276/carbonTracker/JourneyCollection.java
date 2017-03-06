package ca.cmpt276.carbonTracker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by song on 2017-02-27.
 */

public class JourneyCollection {
    private List<Journey> journeyCollection;

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

    public Journey getLatestJourney(){
        if (getNumberJourneys()>0) return journeyCollection.get(journeyCollection.size()-1);
        else return null;
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
            if (car.length()>18) car = car.substring(0,18);

            String route = journey.getRoute().getName();
            for (int j=route.length();j<18;j++){
                route+=" ";
            }
            if (route.length()>18) route = route.substring(0,18);

            String distance = "" + journey.getRoute().getTotalDistanceKM();
            for (int j=distance.length();j<10;j++){
                distance+=" ";
            }
            if (distance.length()>10) distance = distance.substring(0,10);

            String emission = "" + journey.getEmissionsKM();
            for (int j=emission.length();j<8;j++){
                emission+=" ";
            }
            if (emission.length()>8) emission = emission.substring(0,8);

            String date = journey.getDate();

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
            if (car.length()>18) car = car.substring(0,18);
            String route = journey.getRoute().getName();
            if (route.length()>18) route = route.substring(0,18);
            String distance = "" + journey.getRoute().getTotalDistanceKM();
            if (distance.length()>10) distance = distance.substring(0,10);
            String emission = "" + journey.getEmissionsKM();
            if (emission.length()>8) emission = emission.substring(0,8);
            String date = journey.getDate();

            descriptions[i]=date+", "+route+", "+distance+", "+car+", "+emission;
        }
        return descriptions;
    }

    public int[] getJourneyEmission(){
        int NumJourneys = getNumberJourneys();
        int[] emissions = new int[NumJourneys];
        for (int i = 0; i < NumJourneys; i++) {
            Journey journey = journeyCollection.get(i);
            int emission = (int) journey.getEmissionsKM();
            emissions[i]=emission;
        }
        return emissions;
    }



}
