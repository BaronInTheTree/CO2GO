package ca.cmpt276.carbonTracker;

import java.util.List;

/**
 * The CarbonModel is the singleton class that is the main facade of the CarbonTracker app. It
 * stores everything the user needs for accessing their journeys (such as routes and cars) and also
 * a collection of tips.
 *
 * @author Team Teal
 */

public class CarbonModel {
    private static CarbonModel instance = new CarbonModel();
    private JourneyCollection journeys;
    private CarCollection carCollection;
    private RouteCollection routeCollection;
    private CarData carData;
    private Car selectedCar;
    private Route selectedRoute;
    private TipCollection tips;
    private String selectedTransportType;
    private UtilityCollection utilityCollection;

    public static CarbonModel getInstance() {
        if (instance == null) {
            instance = new CarbonModel();
            return instance;
        }
        return instance;
    }

    private CarbonModel() {
        journeys = new JourneyCollection();
        carCollection = new CarCollection();
        routeCollection = new RouteCollection();
        carData = new CarData();
        tips = new TipCollection();
        utilityCollection = new UtilityCollection();
    }

    public void addNewJourney(Journey newJourney) {
        instance.journeys.addJourney(newJourney);
    }

    public Journey getCurrentJourney() {
        return journeys.getLatestJourney();
    }

    public CarData getCarData() {
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

    public Car getSelectedCar() {
        return selectedCar;
    }

    public void setSelectedCar(Car selectedCar) {
        this.selectedCar = selectedCar;
    }

    public Route getSelectedRoute() {
        return selectedRoute;
    }

    public void setSelectedRoute(Route selectedRoute) {
        this.selectedRoute = selectedRoute;
    }

    public TipCollection getTips(){return tips;}

    public String getSelectedTransportType() {
        return selectedTransportType;
    }

    public void setSelectedTransportType(String selectedTransportType) {
        this.selectedTransportType = selectedTransportType;
    }

    public Journey createJourney() {
        if (getSelectedTransportType().equals("Car")){
            Journey journey = new Journey(getSelectedCar(), getSelectedRoute());
            return journey;
        }
        else if (getSelectedTransportType().equals("WalkBike")){
            WalkBike walkBike = new WalkBike();
            Journey journey = new Journey(walkBike, getSelectedRoute());
            return journey;
        }
        else if (getSelectedTransportType().equals("Bus")){
            Bus bus = new Bus();
            Journey journey = new Journey(bus, getSelectedRoute());
            return journey;
        }
        else {
            Skytrain skytrain = new Skytrain();
            Journey journey = new Journey(skytrain, getSelectedRoute());
            return journey;
        }
    }

    public List<String> getUtilityData() {
        return utilityCollection.getUtilityData();
    }
}