package ca.cmpt276.carbonTracker.Internal_Logic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/*
 * Utility class contains data like nickname, fuel used, timespan and
 * number of people in the house.
 * Household can only use electricity OR natural gas.
 */
public class Utility {

    //1 GWh = 3600GJ
    private static final int ELECTRICITY_CO2_EMISSION = 9000;   // per GWh
    private static final double NATURAL_GAS_CO2_EMISSION = 56.1;   // per GJ

    private String nickname;
    private boolean naturalGas;
    private boolean electricity;
    private double timespan;
    private String startDateString;
    private String endDateString;
    private int usage;
    private int numPeople;
    private Date startDate;
    private Date endDate;

    public Utility(String nickname, boolean naturalGas, boolean electricity,
                   String startDate, String endDate, int usage, int numPeople) {
        this.nickname = nickname;
        this.naturalGas = naturalGas;
        this.electricity = electricity;
        this.startDateString = startDate;
        this.endDateString = endDate;
        this.usage = usage;
        this.numPeople = numPeople;

        setDates();
        computeTimeSpan();
    }

    private void setDates() {
        try {
            startDate = (new SimpleDateFormat("yyyy-MM-dd").parse(startDateString));
            endDate = (new SimpleDateFormat("yyyy-MM-dd").parse(endDateString));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void computeTimeSpan() {
        Date startingDate = null;
        Date endingDate = null;

        try {
            startingDate = new SimpleDateFormat("yyyy-MM-dd").parse(getStartDateString());
            endingDate = new SimpleDateFormat("yyyy-MM-dd").parse(getEndDateString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Break up into individual ints (months are indexed at 1 not 0)
        int startYear = Integer.parseInt(new SimpleDateFormat("yyyy").format(startingDate));
        int startMonth = Integer.parseInt(new SimpleDateFormat("MM").format(startingDate)) - 1;
        int startDay = Integer.parseInt(new SimpleDateFormat("dd").format(startingDate));

        int endYear = Integer.parseInt(new SimpleDateFormat("yyyy").format(endingDate));
        int endMonth = Integer.parseInt(new SimpleDateFormat("MM").format(endingDate)) - 1;
        int endDay = Integer.parseInt(new SimpleDateFormat("dd").format(endingDate));

        int monthDiff = endMonth - startMonth;
        int totalDays = 0;

        if (endYear == startYear) {
            if (endMonth == startMonth) {
                totalDays += (endDay - startDay + 1);
            } else {

                totalDays += endDay; // endMonth/endDay to endMonth/01

                // startMonth/startDay to startMonth/end of startMonth
                Calendar startMonthCal = new GregorianCalendar(startYear, startMonth, 1);
                totalDays += (startMonthCal.getActualMaximum(Calendar.DAY_OF_MONTH) - startDay + 1);

                for (int m = monthDiff; m > 1; m--) {
                    Calendar calendar = new GregorianCalendar(startYear, endMonth - m + 1, 1);
                    totalDays += calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
                }
            }
        } else {
            // Sum from endMonth/Day to first day of endYear
            totalDays += endDay;
            for (int m = endMonth - 1; m > 0; m--) {
                Calendar calendar = new GregorianCalendar(endYear, m, 1);
                totalDays += calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            }
            // Sum from startMonth/Day to last day of startYear
            Calendar startMonthCal = new GregorianCalendar(startYear, startMonth, 1);
            totalDays += (startMonthCal.getActualMaximum(Calendar.DAY_OF_MONTH) - startDay + 1);
            for (int m = startMonth + 1; m < 12; m++) {
                Calendar calendar = new GregorianCalendar(startYear, m, 1);
                totalDays += calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            }

            // Add for multiple years
            for (int y = endYear - 1; y >= startYear + 1; y--) {
                if (y % 4 == 0) {
                    totalDays += 366;
                } else {
                    totalDays += 365;
                }
            }
        }
        timespan = totalDays;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public boolean isNaturalGas() {
        return naturalGas;
    }

    public void setNaturalGas(boolean naturalGas) {
        this.naturalGas = naturalGas;
    }

    public boolean isElectricity() {
        return electricity;
    }

    public void setElectricity(boolean electricity) {
        this.electricity = electricity;
    }

    public String getStartDateString() {
        return startDateString;
    }

    public String getEndDateString() {
        return endDateString;
    }

    public double getTimespan() {
        return timespan;
    }

    public void setTimespan(int timespan) {
        this.timespan = timespan;
    }

    public int getUsage() {
        return usage;
    }

    public int getUsageForPeriod(int days) {
        // Makes sure difference in days is always positive
        days = Math.abs(days);

        return usage / days;
    }

    public double getUsagePerDay() {
        return usage / timespan;
    }

    public void setUsage(int numResourcesConsumed, boolean naturalGas, boolean electricity) {
        this.naturalGas = naturalGas;
        this.electricity = electricity;
        this.usage = numResourcesConsumed;
    }

    public int getNumPeople() {
        return numPeople;
    }

    public void setNumPeople(int numPeople) {
        this.numPeople = numPeople;
    }

    public double getC02PerPerson() {
        if (naturalGas) {
            return usage * NATURAL_GAS_CO2_EMISSION / numPeople;
        } else {
            return usage * ELECTRICITY_CO2_EMISSION / numPeople;
        }
    }

    public double getTotalC02(int days) {
        // Makes sure difference in days is always positive
        days = Math.abs(days);

        if (naturalGas) {
            return getUsageForPeriod(days) * NATURAL_GAS_CO2_EMISSION;
        } else {
            return getUsageForPeriod(days) * ELECTRICITY_CO2_EMISSION;
        }
    }

    public String displayToList() {
        String string = nickname + " - ";

        // Select which fuel used
        if (naturalGas) {
            string = string + "Natural Gas";
        } else {
            string = string + "Electricity";
        }

        return string;
    }

    public void setStartDateString(String startDateString) {
        this.startDateString = startDateString;
    }

    public void setEndDateString(String endDateString) {
        this.endDateString = endDateString;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public int getStartYear() {
        return Integer.parseInt(new SimpleDateFormat("yyyy").format(getStartDate()));
    }

    public int getStartMonth() {
        return Integer.parseInt(new SimpleDateFormat("MM").format(getStartDate()));
    }

    public int getStartDay() {
        return Integer.parseInt(new SimpleDateFormat("dd").format(getStartDate()));
    }

    public int getEndYear() {
        return Integer.parseInt(new SimpleDateFormat("yyyy").format(getEndDate()));
    }

    public int getEndMonth() {
        return Integer.parseInt(new SimpleDateFormat("MM").format(getEndDate()));
    }

    public int getEndDay() {
        return Integer.parseInt(new SimpleDateFormat("dd").format(getEndDate()));
    }
}