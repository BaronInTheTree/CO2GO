package ca.cmpt276.carbonTracker;

/**
 * Created by song on 2017-02-27.
 */

public class Route {
    private String name;
    private double highwayDistanceKM;
    private double cityDistanceKM;
    private double highwayDistanceMiles;
    private double cityDistanceMiles;
    private boolean isHidden;

    public Route(String name, double highwayDistanceKM, double cityDistanceKM) {
        this.name = name;
        this.highwayDistanceKM = highwayDistanceKM;
        this.cityDistanceKM = cityDistanceKM;
        this.isHidden = false;
    }

    public double getHighwayDistanceKM() {
        return highwayDistanceKM;
    }

    public double getCityDistanceKM() {
        return cityDistanceKM;
    }

    public String getName(){
        return name;
    }

    public boolean isHidden() {
        return isHidden;
    }

    public void setHidden(boolean hidden) {
        isHidden = hidden;
    }
}
