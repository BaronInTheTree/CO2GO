package ca.cmpt276.carbonTracker;

/**
 * Created by song on 2017-02-27.
 */

public class CarbonModel {
    private static CarbonModel instance = new CarbonModel();
    private JourneyCollection journeys;

    public static CarbonModel getInstance() {
        return instance;
    }

    private CarbonModel() {
        journeys = new JourneyCollection();
    }

    public void addNewJourney(Journey newJourney){
        instance.journeys.addJourney(newJourney);
    }

    public Journey getCurrentJourney(){
        return journeys.getLatestJourney();
    }


}
