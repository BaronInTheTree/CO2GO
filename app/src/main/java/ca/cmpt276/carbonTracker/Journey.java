package ca.cmpt276.carbonTracker;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

/**
 * The Journey class is immutable (once a journey is added, it cannot be changed or removed). It
 * holds the mode of transportation, route, and dateString that the journey was created all encompassed
 * into one class.
 *
 * @author Team Teal
 */

public class Journey implements Comparable<Journey> {

    private Transportation transport;
    private Route route;
    private String dateString;
    private Date dateTime;
    private double emissionsMiles;
    private double emissionsKM;

    public Journey(Transportation transport, Route route, Date dateTime) {
        this.transport = transport;
        this.route = route;
        this.dateString = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        this.dateTime = dateTime;
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

    public Date getDateTime() {
        return dateTime;
    }

    @Override
    public int compareTo(Journey journey) {
        if (getDateTime() == null || journey.getDateTime() == null)
            return 0;
        return getDateTime().compareTo(journey.getDateTime());
    }

    public Route getRoute() {
        return route;
    }

    public String getDateString() {
        return dateString;
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

    public void setTransport(Transportation transport) {
        this.transport = transport;
        calculateEmissions();
    }

    public void setRoute(Route route) {
        this.route = route;
        calculateEmissions();
    }

    public void setDate(String dateString) {
        try {
            this.dateString = dateString;
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            this.dateTime = formatter.parse(dateString);
        }
        catch(ParseException e) {
            e.printStackTrace();
        }
    }


}