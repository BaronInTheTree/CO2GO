package ca.cmpt276.carbonTracker.Internal_Logic;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The CarbonModel is the singleton class that is the main facade of the CarbonTracker app. It
 * stores everything the user needs for accessing their journeyCollection (such as routes and cars) and also
 * a collection of tips.
 *
 * @author Team Teal
 */

public class CarbonModel {
    private static CarbonModel instance = new CarbonModel();
    private JourneyCollection journeyCollection;
    private CarCollection carCollection;
    private RouteCollection routeCollection;
    private CarData carData;
    private Car selectedCar;
    private Route selectedRoute;
    private TipCollection tips;
    private String selectedTransportType;
    private UtilityCollection utilityCollection;
    private DateHandler dateHandler;
    private MonthYearSummary summary;
    private TreeUnit treeUnit;

    public static CarbonModel getInstance() {
        if (instance == null) {
            instance = new CarbonModel();
            return instance;
        }
        return instance;
    }

    private CarbonModel() {
        journeyCollection = new JourneyCollection();
        carCollection = new CarCollection();
        routeCollection = new RouteCollection();
        carData = new CarData();
        tips = new TipCollection();
        dateHandler = new DateHandler();
        utilityCollection = new UtilityCollection();
        summary = new MonthYearSummary();
        treeUnit = new TreeUnit();
    }

    public void addNewJourney(Journey newJourney) {
        instance.journeyCollection.addJourney(newJourney);
    }

    public Journey getCurrentJourney() {
        return journeyCollection.getLatestJourney();
    }

    public CarData getCarData() {
        return carData;
    }

    public JourneyCollection getJourneyCollection() {
        return journeyCollection;
    }

    public CarCollection getCarCollection() {
        return carCollection;
    }

    public RouteCollection getRouteCollection() {
        return routeCollection;
    }

    public MonthYearSummary getSummary(){ return summary;}

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

    public DateHandler getDateHandler() {
        return dateHandler;
    }

    public Journey createJourney() {
        if (getSelectedTransportType().equals("Car")){
            Journey journey = new Journey(getSelectedCar(), getSelectedRoute(), new Date(),
                    Journey.Type.CAR);
            return journey;
        } else if (getSelectedTransportType().equals("WalkBike")) {
            WalkBike walkBike = new WalkBike();
            Journey journey = new Journey(walkBike, getSelectedRoute(), new Date(),
                    Journey.Type.WALK_BIKE);
            return journey;
        } else if (getSelectedTransportType().equals("Bus")) {
            Bus bus = new Bus();
            Journey journey = new Journey(bus, getSelectedRoute(), new Date(),
                    Journey.Type.BUS);
            return journey;
        } else {
            Skytrain skytrain = new Skytrain();
            Journey journey = new Journey(skytrain, getSelectedRoute(), new Date(),
                    Journey.Type.SKYTRAIN);
            return journey;
        }
    }

    public List<String> getTransportationOptions() {
        List<String> options = new ArrayList<>();
        for (String string : carCollection.getUICollection()) {
            options.add(string);
        }
        options.add("Walk/Bike");
        options.add("Bus");
        options.add("Skytrain");
        return options;
    }

    public UtilityCollection getUtilityCollection() {
        return utilityCollection;
    }

    public void setUtilityCollection(UtilityCollection utilityCollection) {
        this.utilityCollection = utilityCollection;
    }

    public List<String> getUtilityFuel() {
        return utilityCollection.getUtilityFuelList();
    }

    public TreeUnit getTreeUnit() {
        return treeUnit;
    }
}