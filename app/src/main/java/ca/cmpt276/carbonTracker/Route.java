package ca.cmpt276.carbonTracker;

/**
 * Route class
 */

public class Route {
    private String name;
    private double highwayDistanceKM;
    private double cityDistanceKM;
    private double highwayDistanceMiles;
    private double cityDistanceMiles;
    private boolean isHidden;

    // Constructor passing in km (assuming UI designed for metric system first)
    public Route(String name, double highwayDistanceKM, double cityDistanceKM) {
        this.name = name;
        this.highwayDistanceKM = highwayDistanceKM;
        this.cityDistanceKM = cityDistanceKM;
        this.highwayDistanceMiles = convertKM_Miles(highwayDistanceKM);
        this.cityDistanceMiles = convertKM_Miles(cityDistanceKM);
        this.isHidden = false;
    }

    private double convertKM_Miles(double miles) {
        return (miles * 0.621371);
    }

    public double getHighwayDistanceKM() {
        return highwayDistanceKM;
    }

    public double getCityDistanceKM() {
        return cityDistanceKM;
    }

    public String getName() {
        return name;
    }

    public boolean isHidden() {
        return isHidden;
    }

    public void setHidden(boolean hidden) {
        isHidden = hidden;
    }

    public double getHighwayDistanceMiles() {
        return highwayDistanceMiles;
    }

    public double getCityDistanceMiles() {
        return cityDistanceMiles;
    }

    public double getTotalDistanceKM() {
        return highwayDistanceKM + cityDistanceKM;
    }
}