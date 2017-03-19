package ca.cmpt276.carbonTracker;

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
    private int daysOperational;
    private int numResourcesConsumed;
    private int numPeople;

    public Utility(boolean naturalGas, boolean electricity, int daysOperational, int numResourcesConsumed, int numPeople) {
        this.naturalGas = naturalGas;
        this.electricity = electricity;
        this.daysOperational = daysOperational;
        this.numResourcesConsumed = numResourcesConsumed;
        this.numPeople = numPeople;
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

    public int getDaysOperational() {
        return daysOperational;
    }

    public void setDaysOperational(int daysOperational) {
        this.daysOperational = daysOperational;
    }

    public int getUsage() {
        return numResourcesConsumed;
    }

    public int getUsageForPeriod(int days) {
        return numResourcesConsumed / days;
    }

    public int getUsagePerDay() {
        return numResourcesConsumed / daysOperational;
    }

    public void setUsage(int numResourcesConsumed, boolean naturalGas, boolean electricity) {
        this.naturalGas = naturalGas;
        this.electricity = electricity;
        this.numResourcesConsumed = numResourcesConsumed;
    }

    public int getNumPeople() {
        return numPeople;
    }

    public void setNumPeople(int numPeople) {
        this.numPeople = numPeople;
    }

    public double getC02PerPerson() {
        if (naturalGas) {
            return numResourcesConsumed * NATURAL_GAS_CO2_EMISSION / numPeople;
        } else {
            return numResourcesConsumed * ELECTRICITY_CO2_EMISSION / numPeople;
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