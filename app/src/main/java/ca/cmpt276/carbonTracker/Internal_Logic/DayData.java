package ca.cmpt276.carbonTracker.Internal_Logic;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Stores all data pertaining to a particular date, including Journeys and Utilities entered.
 * Returns emissions for a particular date, to varying levels of detail.
 *
 * Use static methods getDayDataAtDate and getDayDataWithinInterval to gather information
 * and populate graphs during runtime.
 */

public class DayData {
    private List<Journey> journeyList;
    private List<Utility> utilityList;
    private double totalEmissions;
    private Date date;

    public DayData(Date date) {
        this.date = date;
        journeyList = new ArrayList<>();
        utilityList = new ArrayList<>();
        totalEmissions = 0;
        updateUtilities();
        updateJourneys();
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
            if (DateHandler.areDatesEqual(date, journey.getDateTime())) {
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

    public static List<DayData> getDayDataWithinInterval(Date startDate, Date endDate) {
        List<DayData> dayDataList = new ArrayList<>();
        Date currentDate = startDate;

        while (!DateHandler.areDatesEqual(currentDate, endDate)) {
            dayDataList.add(new DayData(currentDate));
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(currentDate);
            calendar.add(Calendar.DATE, 1);
            currentDate = calendar.getTime();
        }
        dayDataList.add(new DayData(endDate));
        return dayDataList;
    }

    public static DayData getDayDataAtDate(Date date) {
        return new DayData(date);
    }
}
