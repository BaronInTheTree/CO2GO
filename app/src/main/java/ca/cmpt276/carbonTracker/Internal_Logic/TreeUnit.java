package ca.cmpt276.carbonTracker.Internal_Logic;

/**
 * The TreeUnit class implements a human relatable unit for display when
 * a user views CO2 usage. The conversion rate is 1 Tree = 22.06 grams of CO2.
 *
 * @author Team Teal
 */

public class TreeUnit {
    private boolean isTreeEnabled;
    private static final double TREE_TO_CO2 = 22.06;

    public TreeUnit (boolean isTreeEnabled) {
        this.isTreeEnabled = isTreeEnabled;
    }
}
