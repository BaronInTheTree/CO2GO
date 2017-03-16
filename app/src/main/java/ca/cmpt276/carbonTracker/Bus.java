package ca.cmpt276.carbonTracker;

/**
 * Created by Kyle on 3/15/2017.
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
