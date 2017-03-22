package ca.cmpt276.carbonTracker;

/**
 * The Transportation class is an abstract class which extends to Bus, Skytrain, and WalkBike classes.
 *
 * @author Team Teal
 */

public abstract class Transportation {
    protected double co2GramsPerMile_Highway;
    protected double co2GramsPerKM_Highway;
    protected double co2GramsPerMile_City;
    protected double co2GramsPerKM_City;
    protected String nickname;

    public String getNickname() {
        return nickname;
    }

    public double getCo2GramsPerMile_Highway() {
        return co2GramsPerMile_Highway;
    }

    public double getCo2GramsPerKM_Highway() {
        return co2GramsPerKM_Highway;
    }

    public double getCo2GramsPerMile_City() {
        return co2GramsPerMile_City;
    }

    public double getCo2GramsPerKM_City() {
        return co2GramsPerKM_City;
    }
}
