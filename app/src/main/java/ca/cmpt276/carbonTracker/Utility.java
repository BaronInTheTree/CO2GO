package ca.cmpt276.carbonTracker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * Utility class contains data about the about resources used, for how long and
 * number of people in the house.
 * Household can only use electricity OR natural gas.
 */
public class Utility {

    //1 GWh = 3600GJ
    private static final int ELECTRICITY_CO2_EMISSION = 9000;   // per GWh
    private static final double NATURAL_GAS_CO2_EMISSION = 56.1;   // per GJ

    private boolean naturalGas;
    private boolean electricity;
    private int timespan;
    private String startDate;
    private String endDate;
    private int usage;
    private int numPeople;

    public Utility(boolean naturalGas, boolean electricity, String startDate,
                   String endDate, int usage, int numPeople) {
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
            startingDay = new SimpleDateFormat("yyyy-mm-dd").parse(getStartDate());
            endingDay = new SimpleDateFormat("yyyy-mm-dd").parse(getEndDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long differenceInMilliSeconds = startingDay.getTime() - endingDay.getTime();

        // Convert to days from milliseconds
        timespan = (int) differenceInMilliSeconds / (1000 * 60 * 60 * 24);
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

    public int getTimespan() {
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

    public int getUsagePerDay() {
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

    @Override
    public String toString() {
        String string = "";

        // Select which fuel used
        if(naturalGas) {
            string = usage + "GJ of Natural Gas ";
        } else {
            string = usage + "GWh of Electricity ";
        }

        string = string + "from " + startDate + " to " +
                endDate + " with " + numPeople + " people. " +
                "\n Utility produced " + getTotalC02(timespan) + ".";

        return string;
    }
}