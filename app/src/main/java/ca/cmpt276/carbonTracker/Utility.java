package ca.cmpt276.carbonTracker;

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
    private int timespanOfBill;
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

        // TODO: get difference in days
        // Convert difference in milliseconds to difference of days
//        timespanOfBill = (int) (startDate.getTime() - endDate.getTime()) / (1000 * 60 * 60 * 24);
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

    public int getTimespanOfBill() {
        return timespanOfBill;
    }

    public void setTimespanOfBill(int timespanOfBill) {
        this.timespanOfBill = timespanOfBill;
    }

    public int getUsage() {
        return usage;
    }

    public int getUsageForPeriod(int days) {
        return usage / days;
    }

    public int getUsagePerDay() {
        return usage / timespanOfBill;
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
        if (naturalGas) {
            return getUsageForPeriod(days) * NATURAL_GAS_CO2_EMISSION;
        } else {
            return getUsageForPeriod(days) * ELECTRICITY_CO2_EMISSION;
        }
    }
}