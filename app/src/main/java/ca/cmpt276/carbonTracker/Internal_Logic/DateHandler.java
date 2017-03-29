package ca.cmpt276.carbonTracker.Internal_Logic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Stores a list of years and months for GUI
 * Updates number of selectable days in list depending on year and month selected
 */
public class DateHandler {

    private List<String> yearList;
    private List<String> monthList;
    private List<String> dayList;
    public final int MIN_YEAR = 1970; // Earliest year that Java recognizes in the Date class.
    public final int MAX_YEAR = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
    private int currentYear;
    private int currentMonth; // 0-11
    private int currentDay;
    public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public DateHandler() {
        yearList = new ArrayList<>();
        monthList = new ArrayList<>();
        dayList = new ArrayList<>();
        initializeYearList();
        initializeMonthList();
        initializeCurrentDate();
    }

    // static function to calculate the time difference between two dates.
    public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillies = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);
    }

    public static int totalDaysInMonth(int year, int month) {
        Calendar calendar = new GregorianCalendar(year, month - 1, 1);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    private void initializeCurrentDate() {
        currentYear = MAX_YEAR;
        currentMonth = Integer.parseInt(new SimpleDateFormat("MM").format(new Date()));
        currentDay = Integer.parseInt(new SimpleDateFormat("dd").format(new Date()));
    }

    private void initializeYearList() {
        for (int year = MAX_YEAR; year >= MIN_YEAR; year--) {
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

    public static Date createDate(int year, int month, int day) {
        try {
            String dateString = year + "-" + month + "-" + day;
            return DateHandler.dateFormat.parse(dateString);
        }
        catch (ParseException e) {
            e.printStackTrace();
            return null;
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

    public int getCurrentYear() {
        return currentYear;
    }

    public int getCurrentMonth() {
        return currentMonth;
    }

    public int getCurrentDay() {
        return currentDay;
    }

    public static boolean areDatesEqual(Date date1, Date date2) {
        return dateFormat.format(date1).equals(dateFormat.format(date2));
    }
}