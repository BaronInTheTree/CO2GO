package ca.cmpt276.carbonTracker.Internal_Logic;

/**
 * The Transportation class is an abstract class which extends to Bus, Skytrain, and WalkBike classes.
 *
 * @author Team Teal
 */

public class Transportation {
    protected double co2GramsPerMile_Highway;
    protected double co2GramsPerKM_Highway;
    protected double co2GramsPerMile_City;
    protected double co2GramsPerKM_City;
    protected String nickname;
    protected int iconID;

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

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getIconID() {
        return iconID;
    }

    public void setIconID(int iconID) {
        this.iconID = iconID;
    }
}