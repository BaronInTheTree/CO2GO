package ca.cmpt276.carbonTracker;

/*
 * UtilityCollection class contains a list of utilities and a list of resources used.
 */
import java.util.ArrayList;
import java.util.List;

public class UtilityCollection {

    private static final String NATURAL_GAS = "Natural Gas";
    private static final String ELECTRICITY = "Electricity";
    private List<Utility> utilityList;
    private List<String> utilityData;

    public UtilityCollection() {
        utilityList = new ArrayList<>();
        utilityData = new ArrayList<>();
        populateUtilityData();
    }

    private void populateUtilityData() {
        utilityData.add(NATURAL_GAS);
        utilityData.add(ELECTRICITY);
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

    public List<String> displayUtilityList() {
        List<String> utilities = new ArrayList<>();

        for(Utility utility: utilityList) {
            utilities.add(utility.displayToList());
        }
        return utilities;
    }

    public List<String> getUtilityData() {
        return utilityData;
    }
}