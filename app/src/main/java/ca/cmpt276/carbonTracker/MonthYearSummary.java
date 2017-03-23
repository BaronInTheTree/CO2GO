package ca.cmpt276.carbonTracker;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import static ca.cmpt276.carbonTracker.DateHandler.getDateDiff;
import static ca.cmpt276.carbonTracker.TipCollection.BIKEWALK;
import static ca.cmpt276.carbonTracker.TipCollection.BUS;
import static ca.cmpt276.carbonTracker.TipCollection.CAR;
import static ca.cmpt276.carbonTracker.TipCollection.SKYTRAIN;

/**
 * Created by song on 2017-03-22.
 */

public class MonthYearSummary {

    private double monthCarEmission=0;
    private double monthBusEmission=0;
    private double monthSkytrainEmission=0;
    private double monthWalkBikeEmission=0;
    private double monthUtilityEmission=0;

    private double yearCarEmission=0;
    private double yearBusEmission=0;
    private double yearSkytrainEmission=0;
    private double yearWalkBikeEmission=0;
    private double yearUtilityEmission=0;

    private int monthCarDistance=0;
    private int monthBusDistance=0;
    private int monthSkytrainDistance=0;
    private int monthWalkBikeDistance=0;

    private int yearCarDistance=0;
    private int yearBusDistance=0;
    private int yearSkytrainDistance=0;
    private int yearWalkBikeDistance=0;

    public MonthYearSummary(){
        initializeJourneys();
        initializeUtilities();
    }

    private void initializeUtilities() {
        monthUtilityEmission=0;
        yearUtilityEmission=0;
    }

    private void initializeJourneys() {
        monthCarEmission=0;
        monthBusEmission=0;
        monthSkytrainEmission=0;
        monthWalkBikeEmission=0;

        yearCarEmission=0;
        yearBusEmission=0;
        yearSkytrainEmission=0;
        yearWalkBikeEmission=0;
        yearUtilityEmission=0;

        monthCarDistance=0;
        monthBusDistance=0;
        monthSkytrainDistance=0;
        monthWalkBikeDistance=0;

        yearCarDistance=0;
        yearBusDistance=0;
        yearSkytrainDistance=0;
        yearWalkBikeDistance=0;
    }

    public void updateJourneys(JourneyCollection journeys){
        Date today = new Date();
        if (journeys.getSize()>0){
            initializeJourneys();
            for (Journey journey:journeys.getJourneys()){
                if (getDateDiff(journey.getDateTime(),today, TimeUnit.DAYS)<=365){
                    if (journey.getTransportType()==CAR) {
                        yearCarEmission+=journey.getEmissionsKM();
                        yearCarDistance+=journey.getDistance();
                    }
                    if (journey.getTransportType()==BUS) {
                        yearBusEmission+=journey.getEmissionsKM();
                        yearBusDistance+=journey.getDistance();
                    }
                    if (journey.getTransportType()==SKYTRAIN) {
                        yearSkytrainEmission+=journey.getEmissionsKM();
                        yearSkytrainDistance+=journey.getDistance();
                    }
                    if (journey.getTransportType()==BIKEWALK) {
                        yearWalkBikeDistance+=journey.getEmissionsKM();
                        yearWalkBikeDistance+=journey.getDistance();
                    }
                }

                if (getDateDiff(journey.getDateTime(),today, TimeUnit.DAYS)<=28){
                    if (journey.getTransportType()==CAR) {
                        monthCarEmission+=journey.getEmissionsKM();
                        monthCarDistance+=journey.getDistance();
                    }
                    if (journey.getTransportType()==BUS) {
                        monthBusEmission+=journey.getEmissionsKM();
                        monthBusDistance+=journey.getDistance();
                    }
                    if (journey.getTransportType()==SKYTRAIN) {
                        monthSkytrainEmission+=journey.getEmissionsKM();
                        monthSkytrainDistance+=journey.getDistance();
                    }
                    if (journey.getTransportType()==BIKEWALK) {
                        monthWalkBikeDistance+=journey.getEmissionsKM();
                        monthWalkBikeDistance+=journey.getDistance();
                    }
                }
            }
        }
    }

    public void updateUtility(UtilityCollection utilityCollection){
        Date today = new Date();
        if (utilityCollection.getSize()>0){
            initializeUtilities();
            for (Utility utility:utilityCollection.getUtilityList()){
                if (getDateDiff(utility.getStartDate(),today, TimeUnit.DAYS)<=365){
                    yearUtilityEmission+= utility.getC02PerPerson();
                }
                if (getDateDiff(utility.getStartDate(),today, TimeUnit.DAYS)<=28){
                    monthUtilityEmission+= utility.getC02PerPerson();
                }
            }
        }
    }

    // Getters:
    public double getMonthCarEmission(){
        return monthCarEmission;
    }
    public double getMonthBusEmission(){
        return monthBusEmission;
    }
    public double getMonthSkytrainEmission(){
        return monthSkytrainEmission;
    }
    public double getMonthWalkBikeEmission(){
        return monthWalkBikeEmission;
    }
    public double getYearCarEmission(){
        return yearCarEmission;
    }
    public double getYearBusEmission(){
        return yearBusEmission;
    }
    public double getYearSkytrainEmission(){
        return yearSkytrainEmission;
    }
    public double getYearWalkBikeEmission(){
        return yearWalkBikeEmission;
    }
    public int getMonthCarDistance(){
        return monthCarDistance;
    }
    public int getMonthBusDistance(){
        return monthBusDistance;
    }
    public int getMonthSkytrainDistance(){
        return monthSkytrainDistance;
    }
    public int getMonthWalkBikeDistance(){
        return monthWalkBikeDistance;
    }
    public int getYearCarDistance(){
        return yearCarDistance;
    }
    public int getYearBusDistance(){
        return yearBusDistance;
    }
    public int getYearSkytrainDistance(){
        return yearSkytrainDistance;
    }
    public int getYearWalkBikeDistance(){
        return yearWalkBikeDistance;
    }
    public double getYearUtilityEmission(){
        return yearUtilityEmission;
    }
    public double getMonthUtilityEmission(){
        return monthUtilityEmission;
    }


}
