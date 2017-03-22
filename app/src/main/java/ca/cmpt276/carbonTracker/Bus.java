package ca.cmpt276.carbonTracker;


/**
 * The Bus class is a type of transportation that the user may select which has a set output
 * of daily CO2 usage. It is-a mode of transportation.
 *
 * @author Team Teal
 */

public class Bus extends Transportation {

    public Bus() {
        nickname = "Bus";
        co2GramsPerKM_Highway = 89;
        co2GramsPerKM_City = 89;
        calcCO2PerMile();
    }

    private void calcCO2PerMile () {
        co2GramsPerMile_Highway = co2GramsPerKM_Highway / 0.621371;
        co2GramsPerMile_City = co2GramsPerKM_City / 0.621371;
    }

}
