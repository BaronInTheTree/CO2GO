package ca.cmpt276.carbonTracker.Internal_Logic;

/**
 * The TreeUnit class implements a human relatable unit for display when
 * a user views CO2 usage. The conversion rate is 1 Tree = 44.12 grams of CO2.
 * For example, a car with 4412 grams of CO2 would be equal to 100 trees.
 *
 * @author Team Teal
 */

public class TreeUnit {
    private boolean isTreeUnitEnabled;
    private static final double TREE_TO_CO2 = 44.12;
    private static final String CO2 = "CO2";
    private static final String EMISSION= "Emission (g):";
    private static final String TREES = "Trees:";

    public TreeUnit () {
        this.isTreeUnitEnabled = false;
    }

    public boolean getTreeUnitStatus() {
        return isTreeUnitEnabled;
    }

    public void setTreeUnitStatus(boolean status) {
        isTreeUnitEnabled = status;
    }

    public String getUnitType() {
        if (isTreeUnitEnabled) {
            return TREES;
        }
        return EMISSION;
    }

    public double getUnitValue(double emission) {
        if (isTreeUnitEnabled) {
            return emission/TREE_TO_CO2;
        }
        return emission;
    }
}
