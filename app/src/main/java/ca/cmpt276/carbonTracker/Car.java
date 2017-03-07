package ca.cmpt276.carbonTracker;

/**
 * Car class
 */

public class Car {

    // nickname used as the primary key for Cars (unique identifying value, like a fingerprint)
    // If User tries to enter an already existing nickname, don't allow it, display message.

    private String make;
    private String model;
    private int year;
    private String nickname;
    private double co2GramsPerMile_Highway;
    private double co2GramsPerKM_Highway;
    private double co2GramsPerMile_City;
    private double co2GramsPerKM_City;
    private boolean isHidden;
    private double cityMPG;
    private double highwayMPG;
    private String trany;
    private int cylinders;
    private double displacement;
    private String fuelType;

    // Used to populate an arrayList of cars directly from .csv file at runtime
    public Car(String make, String model, int year, double highwayMPG, double cityMPG, String trany,
               int cylinders, double displacement, String fuelType) {
        this.make = make;
        this.model = model;
        this.year = year;
        this.highwayMPG = highwayMPG;
        this.cityMPG = cityMPG;
        this.trany = trany;
        this.displacement = displacement;
        this.cylinders = cylinders;
        this.fuelType = fuelType;
        setEmissions();
    }

    public String getInfo() {
        return "Make: " + make
                + "\nModel: " + model
                + "\nYear: " + year
                + "\nSpecs: " + trany
                + ", " + cylinders
                + "C, " + displacement
                + "L, " + fuelType;
    }

    private void setEmissions() {
        this.co2GramsPerMile_Highway = calcCO2PerMile(highwayMPG);
        this.co2GramsPerMile_City = calcCO2PerMile(cityMPG);
        this.co2GramsPerKM_Highway = calcCO2PerKM(highwayMPG);
        this.co2GramsPerKM_City = calcCO2PerKM(cityMPG);
        this.isHidden = false;
    }

    // 8500g or 8.5kg/gallon averaged from multiple gov't sources
    private double calcCO2PerMile(double mpg) {
        return (8500 / mpg);
    }

    private double calcCO2PerKM(double mpg) {
        return (8500 / mpg * 0.621371);
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    public String getNickname() {
        return nickname;
    }

    public double getCo2GramsPerMile_Highway() {
        return co2GramsPerMile_Highway;
    }

    public double getCo2GramsPerKM_Highway() {
        return co2GramsPerKM_Highway;
    }

    public void setHidden(boolean hidden) {
        this.isHidden = hidden;
    }

    public double getCo2GramsPerMile_City() {
        return co2GramsPerMile_City;
    }

    public double getCo2GramsPerKM_City() {
        return co2GramsPerKM_City;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getTrany() {
        return trany;
    }

    public int getCylinders() {
        return cylinders;
    }

    public double getDisplacement() {
        return displacement;
    }

    public String getFuelType() {
        return fuelType;
    }
}