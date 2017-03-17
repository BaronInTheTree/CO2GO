package ca.cmpt276.carbonTracker;

/**
 * Route class
 */

public class Route {
    private String name;
    private int highwayDistanceKM;
    private int cityDistanceKM;
    private int highwayDistanceMiles;
    private int cityDistanceMiles;
    private boolean isHidden;
    private static final double KM_TO_MILES_MULTIPLIER = 0.621371;

    // Constructor passing in km (assuming UI designed for metric system first)
    public Route(String name, int highwayDistanceKM, int cityDistanceKM) {
        this.name = name;
        this.highwayDistanceKM = highwayDistanceKM;
        this.cityDistanceKM = cityDistanceKM;
        this.highwayDistanceMiles = convertKM_Miles(highwayDistanceKM);
        this.cityDistanceMiles = convertKM_Miles(cityDistanceKM);
        this.isHidden = false;
    }

    private int convertKM_Miles(double miles) {
        Double retVal = miles * KM_TO_MILES_MULTIPLIER;
        return retVal.intValue();
    }

    public int getHighwayDistanceKM() {
        return highwayDistanceKM;
    }

    public int getCityDistanceKM() {
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

    public int getHighwayDistanceMiles() {
        return highwayDistanceMiles;
    }

    public int getCityDistanceMiles() {
        return cityDistanceMiles;
    }

    public int getTotalDistanceKM() {
        return highwayDistanceKM + cityDistanceKM;
    }
}