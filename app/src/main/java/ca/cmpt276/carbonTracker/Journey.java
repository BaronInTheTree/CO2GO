package ca.cmpt276.carbonTracker;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The Journey class is immutable (once a journey is added, it cannot be changed or removed). It
 * holds the mode of transportation, route, and date that the journey was created all encompassed
 * into one class.
 *
 * @author Team Teal
 */

public class Journey {

    private Transportation transport;
    private Route route;
    private String date;
    private double emissionsMiles;
    private double emissionsKM;

    public Journey(Transportation transport, Route route) {
        this.transport = transport;
        this.route = route;
        this.date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        calculateEmissions();
    }

    public Transportation getTransportation() {
        return transport;
    }

    public int getTransportType(){
        String transportContent = transport.getNickname();
        if (transportContent.equals("Bus")) return 1;
        else if (transportContent.equals("Skytrain")) return 2;
        else if (transportContent.equals("Walking / Biking")) return 3;
        else return 0;
    }

    public Route getRoute() {
        return route;
    }

    public String getDate() {
        return date;
    }

    public void calculateEmissions() {
        emissionsKM = (transport.getCo2GramsPerKM_City() * route.getCityDistanceKM())
                + (transport.getCo2GramsPerKM_Highway() * route.getHighwayDistanceKM());
        emissionsMiles = (transport.getCo2GramsPerMile_City() * route.getCityDistanceMiles())
                + (transport.getCo2GramsPerMile_Highway() * route.getHighwayDistanceMiles());
    }

    public double getEmissionsMiles() {
        return emissionsMiles;
    }

    public double getEmissionsKM() {
        return emissionsKM;
    }

    public int getDistance() {
        return route.getTotalDistanceKM();
    }

    public int getEmissionPerKM() {
        return (int)emissionsKM/route.getCityDistanceKM();
    }
}