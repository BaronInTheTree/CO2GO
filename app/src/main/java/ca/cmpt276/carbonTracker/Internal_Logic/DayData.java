package ca.cmpt276.carbonTracker.Internal_Logic;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Stores all data pertaining to a particular date, including Journeys and Utilities entered.
 * Returns emissions for a particular date, to varying levels of detail.
 */

public class DayData {
    private List<Journey> journeyList;
    private List<Utility> utilityList;
    private HashMap<String, Double> emissionTransportationModes;
    private double totalEmissions;
    private Date date;

    public DayData(Date date) {
        this.date = date;
        journeyList = new ArrayList<>();
        utilityList = new ArrayList<>();
        emissionTransportationModes = new HashMap <String, Double>();
        totalEmissions = 0;
        updateUtilities();
        updateJourneys();
        setEmissionTransportationModes();
    }

    public void updateUtilities() {
        utilityList.clear();
        CarbonModel model = CarbonModel.getInstance();
        for (Utility utility : model.getUtilityCollection().getUtilityList()) {
            if (!utility.getStartDate().after(date) && !utility.getEndDate().before(date)) {
                utilityList.add(utility);
            }
        }
    }

    public void updateJourneys() {
        journeyList.clear();
        CarbonModel model = CarbonModel.getInstance();
        for (Journey journey : model.getJourneyCollection().getJourneys()) {
            if (DateHandler.compareDates(date, journey.getDateTime())) {
                journeyList.add(journey);
            }
        }
        System.out.println("MSG 1: UPDATED");
    }

    public double getTotalEmissions_KM() {
        totalEmissions = 0;
        for (Journey journey : journeyList) {
            totalEmissions += journey.getEmissionsKM();
        }
        for (Utility utility : utilityList) {
            totalEmissions += utility.getUsagePerDay();
        }
        return totalEmissions;
    }

    public double getRouteEmissions_KM(Route route) {
        double emissions = 0;
        for (Journey journey : journeyList) {
            if (journey.getRoute().equals(route)) {
                emissions += journey.getEmissionsKM();
            }
        }
        return emissions;
    }

    public double getCarEmissions_KM(Car car) {
        double emissions = 0;
        for (Journey journey : journeyList) {
            if (journey.getTransportation().equals(car)) {
                emissions += journey.getEmissionsKM();
            }
        }
        return emissions;
    }

    public double getTransportTypeEmissions_KM(Journey.Type type) {
        double emissions = 0;
        for (Journey journey : journeyList) {
            if (journey.getType().equals(type)) {
                emissions += journey.getEmissionsKM();
            }
        }
        return emissions;
    }

    public Date getDate() {
        return date;
    }

    public String getInfo() {
        String info = "Date: " + DateHandler.dateFormat.format(date)
                + ", CO2: " + getTotalEmissions_KM() + "\n";
        return info;
    }

    public void setEmissionTransportationModes(){
        emissionTransportationModes.clear();

        for (Journey journey:journeyList){
            if (journey.getType()== Journey.Type.CAR){
                String carName = journey.getTransportation().getNickname();
                if (!emissionTransportationModes.containsKey(carName)) {
                    emissionTransportationModes.put(carName,journey.getEmissionsKM());
                }
                else {
                    Double emission = emissionTransportationModes.get(carName);
                    emission += journey.getEmissionsKM();
                    emissionTransportationModes.put(carName,emission);
                }
            }
            else if (journey.getType()== Journey.Type.BUS){
                if (!emissionTransportationModes.containsKey("Bus")){
                    emissionTransportationModes.put("Bus",journey.getEmissionsKM());
                }
                else{
                    Double emission = emissionTransportationModes.get("Bus");
                    emission += journey.getEmissionsKM();
                    emissionTransportationModes.put("Bus",emission);
                }
            }
            else if (journey.getType()== Journey.Type.SKYTRAIN) {
                if (!emissionTransportationModes.containsKey("Skytrain")){
                    emissionTransportationModes.put("Skytrain",journey.getEmissionsKM());
                }
                else{
                    Double emission = emissionTransportationModes.get("Skytrain");
                    emission += journey.getEmissionsKM();
                    emissionTransportationModes.put("Skytrain",emission);
                }
            }
            else {
                if (!emissionTransportationModes.containsKey("WalkingBiking")){
                    emissionTransportationModes.put("WalkingBiking",journey.getEmissionsKM());
                }
                else{
                    Double emission = emissionTransportationModes.get("WalkingBiking");
                    emission += journey.getEmissionsKM();
                    emissionTransportationModes.put("WalkingBiking",emission);
                }
            }
        }

        for (Utility utility:utilityList){
            if (utility.isElectricity()){
                if (!emissionTransportationModes.containsKey("Electricity")){
                    emissionTransportationModes.put("Electricity", utility.getC02PerPerson());
                }
                else {
                    double emission = emissionTransportationModes.get("Electricity");
                    emission+= utility.getCO2PerDayPerPerson();
                    emissionTransportationModes.put("Electricity",emission);
                }
            }
            else {
                if (!emissionTransportationModes.containsKey("Gas")){
                    emissionTransportationModes.put("Gas", utility.getC02PerPerson());
                }
                else {
                    double emission = emissionTransportationModes.get("Gas");
                    emission+= utility.getCO2PerDayPerPerson();
                    emissionTransportationModes.put("Gas",emission);
                }
            }
        }

    }

    public HashMap<String, Double> getEmissionTransportationModes(){
        return emissionTransportationModes;
    }

}
