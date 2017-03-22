package ca.cmpt276.carbonTracker;

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
    private String startDate;
    private String endDate;
    private int usage;
    private int numPeople;

    public Utility(String nickname, boolean naturalGas, boolean electricity,
                   String startDate, String endDate, int usage, int numPeople) {
        this.nickname = nickname;
        this.naturalGas = naturalGas;
        this.electricity = electricity;
        this.startDate = startDate;
        this.endDate = endDate;
        this.usage = usage;
        this.numPeople = numPeople;

        computeTimeSpan();
    }

    private void computeTimeSpan() {
        Date startingDay = null;
        Date endingDay = null;

        try {
            startingDay = new SimpleDateFormat("yyyy-MM-dd").parse(getStartDate());
            endingDay = new SimpleDateFormat("yyyy-MM-dd").parse(getEndDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long differenceInMilliSeconds = startingDay.getTime() - endingDay.getTime();

        System.out.println("TST 3: StartDate = " + startingDay);
        System.out.println("TST 4: EndDate = " + endingDay);

        int startYear = Integer.parseInt(new SimpleDateFormat("yyyy").format(startingDay));
        int startMonth = Integer.parseInt(new SimpleDateFormat("MM").format(startingDay)) - 1;
        int startDay = Integer.parseInt(new SimpleDateFormat("dd").format(startingDay));

        int endYear = Integer.parseInt(new SimpleDateFormat("yyyy").format(endingDay));
        int endMonth = Integer.parseInt(new SimpleDateFormat("MM").format(endingDay)) - 1;
        int endDay = Integer.parseInt(new SimpleDateFormat("dd").format(endingDay));

        System.out.println("StartYear: " + startYear
                + "\nStartMonth: " + startMonth
                + "\nStartDay: " + startDay);
        System.out.println("EndYear: " + endYear
                + "\nEndMonth: " + endMonth
                + "\nEndDay: " + endDay);

        int yearDiff = endYear - startYear;
        int monthDiff = endMonth - startMonth;
        int totalDays = 0;

        if (endYear == startYear) {
            if (endMonth == startMonth) {
                totalDays += (endDay - startDay + 1);
                System.out.println("Year = Year, Month = Month");
            }
            else {
                System.out.println("Year = Year, Month =! Month\nMonth Diff = " + monthDiff);

                totalDays += endDay; // endMonth/endDay to endMonth/01
                System.out.println("Days in endMonth: " + endDay);

                // startMonth/startDay to startMonth/end of startMonth
                Calendar startMonthCal = new GregorianCalendar(startYear, startMonth, 1);
                totalDays += (startMonthCal.getActualMaximum(Calendar.DAY_OF_MONTH) - startDay + 1);
                System.out.println("Total days in startMonth: " + startMonthCal.getActualMaximum(Calendar.DAY_OF_MONTH));
                System.out.println("Days in startMonth: " + (startMonthCal.getActualMaximum(Calendar.DAY_OF_MONTH) - startDay + 1));

                for (int m = monthDiff; m > 1; m--) {
                    Calendar calendar = new GregorianCalendar(startYear, endMonth - m + 1, 1);
                    totalDays += calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
                    System.out.println("Days in Month " + (endMonth - m + 1) + ": " + calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
                }
            }
        }
        else {
            System.out.println("Year != Year, Month != Month");
            // Sum from endMonth/Day to 01/01 of endYear
            totalDays += endDay;
            for (int m = endMonth - 1; m > 0; m--) {
                Calendar calendar = new GregorianCalendar(endYear, m, 1);
                totalDays += calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
                System.out.println("Sub endMonth/Day to 01/01: " + calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
            }
            System.out.println("Subtotal endYear: " + totalDays);
            // Sum from startMonth/Day to 12/31 of startYear
            Calendar startMonthCal = new GregorianCalendar(startYear, startMonth, 1);
            totalDays += (startMonthCal.getActualMaximum(Calendar.DAY_OF_MONTH) - startDay + 1);
            System.out.println("Sub startMonth to end of startMonth: " + (startMonthCal.getActualMaximum(Calendar.DAY_OF_MONTH) - startDay + 1));
            for (int m = startMonth + 1; m < 12; m++) {
                Calendar calendar = new GregorianCalendar(startYear, m, 1);
                totalDays += calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
                System.out.println("Subtotal month " + m + ": " + calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
            }
            System.out.println("Subtotal startYear + endYear: " + totalDays);

            for (int y = endYear - 1; y >= startYear + 1; y--) {
                if (y % 4 == 0) {
                    totalDays += 366;
                    System.out.println("Adding leap year");
                }
                else {
                    totalDays += 365;
                    System.out.println("Adding year");
                }
            }
        }
        System.out.println("TOTAL DAYS = " + totalDays);



        // If year = different
            // Calculate 01/01 -> endDate
            // Calculate startDate -> 12/31
            // If yearDiff > 1, add 365
            // If leapYear, add 1
        // If Year = Same
            // If month = Same
                // endDay - startDay
            // If Month = Diff
                // Calculate endDay -> start of endMonth
                // Calculate startDay -> end of startMonth
                // for (int m = diff; m > 1; m--)
                    // gregCal(year, endMonth - m + 1, 1)
                    // add maxDays for that month

        // Convert to days from milliseconds
        //timespan = (int) differenceInMilliSeconds / (1000 * 60 * 60 * 24);
        timespan = totalDays;
        System.out.println("TST 1 - Timespan: " + timespan);
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

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
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

    // TODO: C02 usage is not correctly computed
    public double getC02PerPerson() {
        if (naturalGas) {
            return usage * NATURAL_GAS_CO2_EMISSION / numPeople;
        } else {
            return usage * ELECTRICITY_CO2_EMISSION / numPeople;
        }
    }

    public double getTotalC02(int days) {
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
}