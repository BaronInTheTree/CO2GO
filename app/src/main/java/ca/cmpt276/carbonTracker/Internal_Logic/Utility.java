package ca.cmpt276.carbonTracker.Internal_Logic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

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
    private int timespan;
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
        timespan = (int) DateHandler.getDateDiff(startingDate, endingDate, TimeUnit.DAYS);
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

    public int getTimespan() {
        return timespan;
    }

    public void setTimespan(int timespan) {
        this.timespan = timespan;
    }

    public int getUsage() {
        return usage;
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

    public double getTotalCO2() {
        if (naturalGas) {
            return usage * NATURAL_GAS_CO2_EMISSION;
        }
        else {
            return usage * ELECTRICITY_CO2_EMISSION;
        }
    }

    public double getCO2PerDay() {
        // Makes sure difference in days is always positive

        if (naturalGas) {
            return getUsagePerDay() * NATURAL_GAS_CO2_EMISSION;
        } else {
            return getUsagePerDay() * ELECTRICITY_CO2_EMISSION;
        }
    }

    public float getCO2PerDayPerPerson(){
        return (float)getCO2PerDay()/numPeople;
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