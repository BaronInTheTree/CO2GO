package ca.cmpt276.carbonTracker;

/**
 * Created by song on 2017-02-27.
 */

public class Car {

    // nickname used as the primary key for Cars (unique identifying value, like a fingerprint)
    // If User tries to enter an already existing nickname, don't allow it, display message.

    private String make;
    private String model;
    private int year;
    private String nickname;
    private double co2GramsPerMile;
    private double co2GramsPerKM;
    private boolean isHidden;
    private double cityMPG;
    private double highwayMPG;

    public Car(String make, String model, int year, String nickname, double co2GramsPerMile){
        this.make = make;
        this.model = model;
        this.year = year;
        this.nickname = nickname;
        this.co2GramsPerMile = co2GramsPerMile;
        this.co2GramsPerKM = calcCO2GramsPerKM(co2GramsPerMile);
        this.isHidden = false;
    }

    private double calcCO2GramsPerKM(double co2GramsPerMile){
        return (co2GramsPerMile * 0.621371);
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

    public double getCo2GramsPerMile() {
        return co2GramsPerMile;
    }

    public double getCo2GramsPerKM() {
        return co2GramsPerKM;
    }

    public void setCo2GramsPerMi_KM(double co2GramsPerMile) {
        this.co2GramsPerMile = co2GramsPerMile;
        this.co2GramsPerKM = calcCO2GramsPerKM(co2GramsPerMile);
    }

    public boolean isHidden(){
        return isHidden;
    }

    public void setIsHidden(boolean hidden){
        this.isHidden = hidden;
    }
}
