package ca.cmpt276.carbonTracker;

/**
 * Car class
 */

public class Car implements Cloneable {

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
    private double kgC02perGallon;

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
        setKgC02();
        setEmissions();
    }

    public void editCar(Car car){
        this.nickname = car.getNickname();
        this.make = car.getMake();
        this.model = car.getModel();
        this.year = car.getYear();
        this.trany = car.getTrany();
        this.displacement = car.getDisplacement();
        this.cylinders = car.getCylinders();
        this.fuelType = car.getFuelType();
        setKgC02();
        setEmissions();
    }

    public String getBasicInfo() {
        return nickname + ",\t" + make + ",\t" + model + ",\t" + year;
    }

    private void setKgC02() {
        if (fuelType.equals("Gasoline")) {
            kgC02perGallon = 8890;
        } else if (fuelType.equals("Diesel")) {
            kgC02perGallon = 10160;
        } else if (fuelType.equals("Electric")) {
            kgC02perGallon = 0;
        }
    }

    private void setEmissions() {
        this.co2GramsPerMile_Highway = calcCO2PerMile(highwayMPG);
        this.co2GramsPerMile_City = calcCO2PerMile(cityMPG);
        this.co2GramsPerKM_Highway = calcCO2PerKM(highwayMPG);
        this.co2GramsPerKM_City = calcCO2PerKM(cityMPG);
        this.isHidden = false;
    }

    private double calcCO2PerMile(double mpg) {
        if (kgC02perGallon > 0) {
            return (kgC02perGallon / mpg);
        } else return 0;
    }

    private double calcCO2PerKM(double mpg) {
        if (kgC02perGallon > 0) {
            return (kgC02perGallon / mpg * 0.621371);
        } else return 0;
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

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}