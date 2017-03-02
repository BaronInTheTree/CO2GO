package ca.cmpt276.carbonTracker;

/**
 * Created by song on 2017-02-27.
 */

public class Journey {

    // Immutable class. Once journey is added, it cannot be changed or removed.

    private Car car;
    private Route route;
    private double emissionsMiles;
    private double emissionsKM;

    public Journey(Car car, Route route) {
        this.car = car;
        this.route = route;
    }

    public Car getCar() {
        return car;
    }

    public Route getRoute() {
        return route;
    }

    // Incomplete. Consulting Brian on how exactly to do this
    private void calculateEmissions(){
        emissionsKM = (car.getCo2GramsPerKM_City() * route.getCityDistanceKM())
                + (car.getCo2GramsPerKM_Highway() * route.getHighwayDistanceKM());
        emissionsMiles = (car.getCo2GramsPerMile_City() * route.getCityDistanceMiles())
                + (car.getCo2GramsPerMile_Highway() * route.getHighwayDistanceMiles());
    }

    public double getEmissionsMiles() {
        return emissionsMiles;
    }

    public double getEmissionsKM() {
        return emissionsKM;
    }
}
