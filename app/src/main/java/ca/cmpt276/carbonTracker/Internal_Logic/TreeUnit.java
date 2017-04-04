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
    private static final float TREE_TO_CO2_GRAPH = (float)44.12;
    private static final String CO2 = "g CO2";
    private static final String EMISSION= "Emission (g):";
    private static final String TREES_COLON = "Trees:";
    private static final String TREES = "Trees";

    public TreeUnit () {
        this.isTreeUnitEnabled = false;
    }

    public boolean getTreeUnitStatus() {
        return isTreeUnitEnabled;
    }

    public void setTreeUnitStatus(boolean status) {
        isTreeUnitEnabled = status;
    }


    // The next two functions return a String with whatever there was to replace (g to Trees or vice versa).

    // Related to activities, such as JourneyInformationActivity.
    public String getUnitTypeSummary() {
        if (isTreeUnitEnabled) {
            return TREES_COLON;
        }
        return EMISSION;
    }

    // More related to the model, such as for JourneyCollection.
    public String getUnitTypeList() {
        if (isTreeUnitEnabled) {
            return TREES;
        }
        return CO2;
    }

    public double getUnitValue(double emission) {
        if (isTreeUnitEnabled) {
            return emission/TREE_TO_CO2;
        }
        return emission;
    }

    public float getUnitValueGraphs(float emission) {
        if (isTreeUnitEnabled) {
            return emission/TREE_TO_CO2_GRAPH;
        }
        return emission;
    }

    public static double convertToTrees(double emission) {
        return emission/TREE_TO_CO2;
    }
}
