package ca.cmpt276.carbonTracker;

/*
 * UtilityCollection class contains a list of utilities.
 */
import java.util.ArrayList;
import java.util.List;

public class UtilityCollection {

    private List<Utility> utilityList;

    public UtilityCollection() {
        utilityList = new ArrayList<>();
    }

    public void addUtility(Utility utility) {
        utilityList.add(utility);
    }

    public Utility getUtility(int index) {
        return utilityList.get(index);
    }

    public void deleteUtility(int index) {
        utilityList.remove(index);
    }

    public double getTotalEmissionsForPeriod(int days) {
        double totalEmissions = 0;
        for (Utility utility : utilityList) {
            totalEmissions = totalEmissions + utility.getUsageForPeriod(days);
        }
        return totalEmissions;
    }
}