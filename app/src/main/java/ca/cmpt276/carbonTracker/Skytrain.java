package ca.cmpt276.carbonTracker;

/**
 * The Skytrain class is-a mode of transportation that the user may select which has a set output
 * of daily CO2 usage. It has preset values that will be added in to the user's journey.
 *
 * @author Team Teal
 */

public class Skytrain extends Transportation {

    public Skytrain() {
        nickname = "Skytrain";
        co2GramsPerKM_Highway = 50.4;
        co2GramsPerKM_City = 50.4;
        calcCO2PerMile();
    }

    private void calcCO2PerMile () {
        co2GramsPerMile_Highway = co2GramsPerKM_Highway / 0.621371;
        co2GramsPerMile_City = co2GramsPerKM_City / 0.621371;
    }

}
