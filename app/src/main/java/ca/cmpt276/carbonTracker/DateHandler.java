package ca.cmpt276.carbonTracker;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
/**
 * Stores a list of years and months for GUI
 * Updates number of selectable days in list depending on year and month selected
 */
public class DateHandler {
    private List<String> yearList;
    private List<String> monthList;
    private List<String> dayList;
    public DateHandler() {
        yearList = new ArrayList<>();
        monthList = new ArrayList<>();
        dayList = new ArrayList<>();
        initializeYearList();
        initializeMonthList();
    }
    // TODO: replace 2017 with method to get current date
    private void initializeYearList() {
        for (int year = 2017; year >= 1970; year--) {
            yearList.add("" + year);
        }
    }
    private void initializeMonthList() {
        for (int month = 1; month <= 12; month++) {
            monthList.add("" + month);
        }
    }
    public void initializeDayList(int year, int month) {
        Calendar calendar = new GregorianCalendar(year, month - 1, 1);
        dayList.clear();
        int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        for (int day = 1; day <= daysInMonth; day++) {
            dayList.add("" + day);
        }
    }
    public List<String> getYearList() {
        return yearList;
    }
    public List<String> getMonthList() {
        return monthList;
    }
    public List<String> getDayList() {
        return dayList;
    }
}