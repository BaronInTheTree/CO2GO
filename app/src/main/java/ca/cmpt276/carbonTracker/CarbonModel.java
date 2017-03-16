package ca.cmpt276.carbonTracker;

import java.util.ArrayList;
import java.util.List;

/**
 * CarbonModel class
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

    // TODO: replace with a collection of cars that user owns
    public List<String> outputCarCollectionToString() {
        List<String> list = new ArrayList<>();
/*
        for (Car car: carCollection) {
            String str = car.toString();
            list.add(str);
        }
*/
        return list;
    }

    // TODO: replace with a collection of routes that user uses
    public List<String> outputRouteCollectionToString() {
        List<String> list = new ArrayList<>();
/*
        for (Route route: routeCollection) {
            String str = route.toString();
            list.add(str);
        }
*/
        return list;
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
}