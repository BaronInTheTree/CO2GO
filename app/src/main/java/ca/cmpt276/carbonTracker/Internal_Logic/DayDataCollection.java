package ca.cmpt276.carbonTracker.Internal_Logic;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Stores all DayData objects inside a single ArrayList
 */

public class DayDataCollection {
    private List<DayData> dayDataCollection;

    public DayDataCollection() {
        dayDataCollection = new ArrayList<>();
    }

    public void addDayData(Date date) {
        if (!doesDateEntryExist(date)) {
            dayDataCollection.add(new DayData(date));
        }
    }

    public void initializeJourneyDates() {
        CarbonModel model = CarbonModel.getInstance();
        for (Journey journey : model.getJourneyCollection().getJourneys()) {
            addDayData(journey.getDateTime());
        }
    }

    public void updateJourneyDates() {
        CarbonModel model = CarbonModel.getInstance();
        for (DayData dayData : model.getDayDataCollection().getDayDataList()) {
            dayData.updateJourneys();
        }
    }

    public void updateUtilityDates() {
        CarbonModel model = CarbonModel.getInstance();
        for (DayData dayData : model.getDayDataCollection().getDayDataList()) {
            dayData.updateUtilities();
        }
    }


    public void initializeUtilityDates() {
        CarbonModel model = CarbonModel.getInstance();
        for (Utility utility : model.getUtilityCollection().getUtilityList()) {
            int timespan = utility.getTimespan();
            Date currentDate = utility.getStartDate();
            while (timespan > 0) {
                if (!doesDateEntryExist(currentDate)) {
                    DayData dayData = new DayData(currentDate);
                    dayDataCollection.add(dayData);
                }
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(currentDate);
                calendar.add(Calendar.DATE, 1);
                currentDate = calendar.getTime();
                timespan--;
            }
        }
    }

    public List<DayData> getDayDataList() {
        return dayDataCollection;
    }

    public DayData getDayDataAtDate(Date date) {
        for (DayData dayData : dayDataCollection) {
            if (DateHandler.compareDates(dayData.getDate(), date)) {
                return dayData;
            }
        }
        return null;
    }

    public boolean doesDateEntryExist(Date date) {
        for (DayData dayData : dayDataCollection) {
            if (DateHandler.compareDates(dayData.getDate(), date)) {
                return true;
            }
        }
        return false;
    }
}
