package ca.cmpt276.carbonTracker.Internal_Logic;

/**
 * The WalkBike class is-a type of transportation with no CO2 usage. It has all preset values
 * so the user can select this type of transportation and move on to edit other things (such as route).
 *
 * @author Team Teal
 */

public class WalkBike extends Transportation {

    public WalkBike() {
        nickname = "Walking / Biking";
        co2GramsPerKM_City = 0;
        co2GramsPerKM_Highway = 0;
        co2GramsPerMile_City = 0;
        co2GramsPerMile_Highway = 0;
    }

}
