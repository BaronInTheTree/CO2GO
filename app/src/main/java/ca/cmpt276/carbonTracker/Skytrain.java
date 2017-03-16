package ca.cmpt276.carbonTracker;

/**
 * Created by Kyle on 3/15/2017.
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
