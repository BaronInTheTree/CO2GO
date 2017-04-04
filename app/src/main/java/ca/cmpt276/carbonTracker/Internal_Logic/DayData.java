package ca.cmpt276.carbonTracker.Internal_Logic;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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
    private float totalEmissions;
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
            else if (DateHandler.areDatesEqual(date, utility.getStartDate())
                    || DateHandler.areDatesEqual(date, utility.getEndDate())) {
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

    public float getTotalEmissions_KM() {
        totalEmissions = 0;
        for (Journey journey : journeyList) {
            totalEmissions += journey.getEmissionsKM();
        }
        for (Utility utility : utilityList) {
            totalEmissions += utility.getUsagePerDay();
        }
        return totalEmissions;
    }

    public float getRouteEmissions_KM(Route route) {
        float emissions = 0;
        for (Journey journey : journeyList) {
            if (journey.getRoute().equals(route)) {
                emissions += journey.getEmissionsKM();
            }
        }
        return emissions;
    }

    public float getCarEmissions_KM(Car car) {
        float emissions = 0;
        for (Journey journey : journeyList) {
            if (journey.getTransportation().getNickname().equals(car.getNickname())) {
                System.out.println("TST 10");
                emissions += journey.getEmissionsKM();
            }
        }
        return emissions;
    }

    public float getTransportTypeEmissions_KM(Journey.Type type) {
        float emissions = 0;
        for (Journey journey : journeyList) {
            if (journey.getType().equals(type)) {
                emissions += journey.getEmissionsKM();
            }
        }
        return emissions;
    }

    public float getNaturalGasEmissions() {
        float emissions = 0;
        for (Utility utility : utilityList) {
            if (utility.isNaturalGas()) {
                emissions += utility.getTotalCO2();
            }
        }
        return emissions;
    }

    public float getElectricityEmissions() {
        float emissions = 0;
        for (Utility utility : utilityList) {
            if (utility.isElectricity()) {
                emissions += utility.getTotalCO2();
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

    public List<Journey> getJourneyList(){
        return journeyList;
    }

    public List<Utility> getUtilityList(){
        return utilityList;
    }


    public static ArrayList<DayData> getDayDataWithinInterval(Date startDate, Date endDate) {
        ArrayList<DayData> dayDataList = new ArrayList<>();
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

    public static ArrayList<ArrayList> getDayDataPerWeekInYear(ArrayList<DayData> dayDataList) {
        ArrayList<ArrayList> yearData = new ArrayList<>();
        for (int i = dayDataList.size() - 1; i >= 0; i-=7) {
            ArrayList<DayData> weekData = new ArrayList<>();
            if (i >= 7) {
                for (int j = i; j > (i - 7); j--) {
                    weekData.add(dayDataList.get(j));
                }
            }
            else {
                for (int j = i; j >= 0; j--) {
                    weekData.add(dayDataList.get(j));
                }
            }
            yearData.add(0, weekData);
        }
        return yearData;
    }

    public static float getWeeklyElectricityEmissions(ArrayList<DayData> weekData) {
        float emissions = 0;
        for (DayData dayData : weekData) {
            emissions += dayData.getElectricityEmissions();
        }
        return emissions;
    }

    public static float getWeeklyGasEmissions(ArrayList<DayData> weekData) {
        float emissions = 0;
        for (DayData dayData : weekData) {
            emissions += dayData.getNaturalGasEmissions();
        }
        return emissions;
    }

    public static float getWeeklyBusEmissions(ArrayList<DayData> weekData) {
        float emissions = 0;
        for (DayData dayData : weekData) {
            emissions += dayData.getTransportTypeEmissions_KM(Journey.Type.BUS);
        }
        return emissions;
    }

    public static float getWeeklySkytrainEmissions(ArrayList<DayData> weekData) {
        float emissions = 0;
        for (DayData dayData : weekData) {
            emissions += dayData.getTransportTypeEmissions_KM(Journey.Type.SKYTRAIN);
        }
        return emissions;
    }

    public static float getWeeklyCarEmissions(ArrayList<DayData> weekData, Car car) {
        float emissions = 0;
        for (DayData dayData : weekData) {
            emissions += dayData.getCarEmissions_KM(car);
        }
        return emissions;
    }

    public static DayData getDayDataAtDate(Date date) {
        return new DayData(date);
    }
}
