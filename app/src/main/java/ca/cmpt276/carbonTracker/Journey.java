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
        double co2GramsPerMile = car.getCo2GramsPerMile();
        double co2GramsPerKM = car.getCo2GramsPerKM();
    }

    public double getEmissionsMiles() {
        return emissionsMiles;
    }

    public double getEmissionsKM() {
        return emissionsKM;
    }
}
