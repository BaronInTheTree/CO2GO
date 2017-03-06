package ca.cmpt276.carbonTracker;

/**
 * Created by song on 2017-02-27.
 */

public class CarbonModel {
    private static CarbonModel instance = new CarbonModel();
    private JourneyCollection journeys;
    private CarCollection carCollection;
    private RouteCollection routeCollection;
    private CarData carData;

    public static CarbonModel getInstance() {
        return instance;
    }

    private CarbonModel() {
        journeys = new JourneyCollection();
        carCollection = new CarCollection();
        routeCollection = new RouteCollection();
        carData = new CarData();
    }

    public void addNewJourney(Journey newJourney){
        instance.journeys.addJourney(newJourney);
    }

    public Journey getCurrentJourney(){return journeys.getLatestJourney();}

    public Route getCurrentRoute(){return routeCollection.getLatestRoute();}

    public Car getCurrentCar(){return carCollection.getLatestCar();}

    public CarData getCarData(){
        return carData;
    }

    public JourneyCollection getJourneys() {
        return journeys;
    }

    public CarCollection getCarCollection() {
        return carCollection;
    }

    public RouteCollection getRouteCollection() {
        return routeCollection;
    }
}
