package ca.cmpt276.carbonTracker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by song on 2017-03-14.
 */

public class TipCollection {

    private List<Tip> recentShownTips;
    private List<Tip> carTips;
    private List<Tip> busTips;
    private List<Tip> skyTrainTips;
    private List<Tip> bikeWalkTips;
    private List<Tip> electricityTips;
    private List<Tip> gasTips;
    private List<Tip> generalTips;

    private List<String> carTipsContent;
    private List<String> busTipsContent;
    private List<String> skyTrainTipsContent;
    private List<String> bikeWalkTipsContent;
    private List<String> electricityTipsContent;
    private List<String> gasTipsContent;
    private List<String> generalTipsContent;


    public TipCollection() {
        generalTips = new ArrayList<>();
        recentShownTips = new ArrayList<>();

        carTips = new ArrayList<>();
        busTips = new ArrayList<>();
        skyTrainTips = new ArrayList<>();
        bikeWalkTips = new ArrayList<>();
        electricityTips = new ArrayList<>();
        gasTips = new ArrayList<>();

        generalTipsContent = new ArrayList<>();
        carTipsContent = new ArrayList<>();
        busTipsContent = new ArrayList<>();
        skyTrainTipsContent = new ArrayList<>();
        bikeWalkTipsContent = new ArrayList<>();
        electricityTipsContent = new ArrayList<>();
        gasTipsContent  = new ArrayList<>();

        updateGeneralTips();
    }

    public String getGeneralTip() {
        if (generalTips.size()==0) {
            generalTipsInitialize();
        }

        boolean findTip =false;

        while (!findTip) {
            for (int i = 0; i < 7; i++) {
                if (!recentShownTips.contains(generalTips.get(i))) {
                    findTip=true;
                    if (recentShownTips.size() == 7) {
                        recentShownTips.remove(0);
                    }
                    updateGeneralTips();
                    String currentTipContent = generalTipsContent.get(i);
                    Tip currentTip = new Tip(currentTipContent);
                    generalTips.set(i, currentTip);
                    recentShownTips.add(generalTips.get(i));
                    return generalTips.get(i).getTipContent();
                }
            }
            if (!findTip) recentShownTips.remove(0);
        }
        return "Error!";
    }

    public String getJourneyTip(int journeyType, Journey currentJourney){
        List<Tip> tips = new ArrayList<>();
        if (journeyType==0) tips = carTips;
        if (journeyType==1) tips = busTips;
        if (journeyType==2) tips = skyTrainTips;
        if (journeyType==3) tips = bikeWalkTips;

        if (tips.size()==0){
            if (journeyType==0) updateCarTipsContent(currentJourney);
            if (journeyType==1) updateBusTipsContent(currentJourney);
            if (journeyType==2) updateSkyTrainTipsContent(currentJourney);
            if (journeyType==3) updateBikeWalkTipsContent(currentJourney);
            journeyInitialize(journeyType);
        }

        boolean findTip= false;

        while (!findTip) {
            for (int i = 0; i < 7; i++) {
                if (!recentShownTips.contains(tips.get(i))) {
                    findTip = true;
                    if (recentShownTips.size() == 7) {
                        recentShownTips.remove(0);
                    }
                    String currentTipContent="";
                    if (journeyType==0) {
                        updateCarTipsContent(currentJourney);
                        currentTipContent = carTipsContent.get(i);
                    }
                    if (journeyType==1) {
                        updateBusTipsContent(currentJourney);
                        currentTipContent = busTipsContent.get(i);
                    }
                    if (journeyType==2) {
                        updateSkyTrainTipsContent(currentJourney);
                        currentTipContent = skyTrainTipsContent.get(i);
                    }
                    if (journeyType==3) {
                        updateBikeWalkTipsContent(currentJourney);
                        currentTipContent = bikeWalkTipsContent.get(i);
                    }

                    Tip currentTip = new Tip(currentTipContent);
                    tips.set(i, currentTip);
                    recentShownTips.add(tips.get(i));
                    return tips.get(i).getTipContent();
                }
            }
            if (!findTip) recentShownTips.remove(0);
        }
        return "error!";
    }

    // Todo: similar to above, write getUtilityTip(). It depends on how electricity and gas are implemented.

    public void generalTipsInitialize(){
        for (int i=0;i<7;i++){
            Tip currentTip = new Tip(generalTipsContent.get(i));
            generalTips.add(currentTip);
        }
    }

    public void journeyInitialize(int journeyType){
        if (journeyType==0) {
            for (int i=0;i<7;i++){
                Tip currentTip = new Tip(carTipsContent.get(i));
                carTips.add(currentTip);
            }
        }
        if (journeyType==1){
            for (int i=0;i<7;i++){
                Tip currentTip = new Tip(busTipsContent.get(i));
                busTips.add(currentTip);
            }
        }
        if (journeyType==2){
            for (int i=0;i<7;i++){
                Tip currentTip = new Tip(skyTrainTipsContent.get(i));
                skyTrainTips.add(currentTip);
            }
        }
        if (journeyType==3){
            for (int i=0;i<7;i++){
                Tip currentTip = new Tip(bikeWalkTipsContent.get(i));
                bikeWalkTips.add(currentTip);
            }
        }
    }

    public void electricityTipsInitialize(){
        for (int i=0;i<7;i++){
            Tip currentTip = new Tip(electricityTipsContent.get(i));
            electricityTips.add(currentTip);
        }
    }

    public void gasTipsInitialize(){
        for (int i=0;i<7;i++){
            Tip currentTip = new Tip(gasTipsContent.get(i));
            gasTips.add(currentTip);
        }
    }

    public void updateGeneralTips(){
        if (generalTipsContent.size()!=0) generalTipsContent.clear();
        // need the getMonthDriveDistance() and getMonthDriveEmission() and getYearDriveEmission() and other relevant methods
        generalTipsContent.add("Your total driving distance within last 4 weeks are getMonthDriveDistance(), consider to take more bus or skytrain to lower your driving distance in the future.");
        generalTipsContent.add("Driving within the last four weeks contributes to getMonthDriveEmission() CO2 emission, consider to take more bus or skytrain to lower your emission in the future.");
        generalTipsContent.add("Driving last year contributes to getYearDriveEmission() CO2 emission, consider to take more bus or skytrain to lower your emission in the future.");
        generalTipsContent.add("Taking buses within the last four weeks contributes to “getMonthBusEmission()” CO2 emission, consider to take skytrain more often to lower your emission in the future.");
        generalTipsContent.add("Taking Skytrain within the last four weeks contributes to “getMonthBusEmission()” CO2 emission, consider to ride bikes or walk more often to lower your emission in the future.");
        generalTipsContent.add("You have biked and walked “getMonthBikeWalkDistance()” within last 4 weeks, keep it up!");
        generalTipsContent.add("Your electricity usage over the past year is “getYearElectricity()”, try to reduce your electricity usage to lower your total CO2 emission.");
        //generalTipsContent.add("Your gas usage over the past year is “getYearGas()”, try to reduce your gas usage to lower your total CO2 emission.");
    }

    public void updateCarTipsContent(Journey currentJourney) {
        if (carTipsContent.size() != 0) carTipsContent.clear();
        carTipsContent.add("Your CO2 emission per km drive is " + currentJourney.getEmissionPerKM() + " g/km, consider to drive a more economical car.");
        carTipsContent.add("Your CO2 emission for this trip by car is " + (int) currentJourney.getEmissionsKM() + " g, consider to take bus or skytrain to reduce your emission.");
        carTipsContent.add("Your CO2 emission for this trip by car is " + (int) currentJourney.getEmissionsKM() + " g, consider to ride bikes or walk to cut your emission to 0!");
        carTipsContent.add("Distance of your trip by car is " + currentJourney.getDistance() + " km, consider to take bus or skytrain to reduce your emission.");
        carTipsContent.add("Distance of your trip by car is " + currentJourney.getDistance() + " km, consider to ride bikes or walk if possible.");
        // need the getMonthDriveDistance() and getMonthDriveEmission() method
        carTipsContent.add("Your total driving distance within last 4 weeks are getMonthDriveDistance() km, consider to take more bus or skytrain in the future.");
        carTipsContent.add("Your total driving distance within last year are getYearDriveDistance() km, consider to take more bus or skytrain in the future.");
        //carTipsContent.add("Driving within the last four weeks contributes to getMonthDriveEmission() CO2 emission, consider to take more bus or skytrain to lower your emission in the future.");
    }

    public void updateBusTipsContent(Journey currentJourney){
        if (busTipsContent.size()!=0) busTipsContent.clear();
        busTipsContent.add("Your total CO2 emission for this trip is " + (int)currentJourney.getEmissionsKM()+ " g, consider to ride bikes or walk to cut your trip emission to 0!");
        busTipsContent.add("Distance of your trip by bu is" + currentJourney.getDistance() + " km, an equivalent trip by skytrain will generate ~40% less CO2.");
        busTipsContent.add("Distance of your trip by bu is " + currentJourney.getDistance() + " km, consider to ride bikes or walk if possible.");
        // need supporting methods.
        busTipsContent.add("You have traveled “getMonthBusDistance()” by Bus within last 4 weeks, consider to take skytrain more often in the future.");
        busTipsContent.add("You have traveled “getMonthBusDistance()” by Bus within last 4 weeks, consider to ride bikes or walk more often in the future.");
        busTipsContent.add("Taking buses within the last four weeks contributes to “getMonthBusEmission()” CO2 emission, consider to take skytrain more often in the future.");
        busTipsContent.add("Taking buses within the last four weeks contributes to “getMonthBusEmission()” CO2 emission, consider to ride bikes more often in the future.");

    }

    public void updateSkyTrainTipsContent(Journey currentJourney){
        if (skyTrainTipsContent.size()!=0) skyTrainTipsContent.clear();
        skyTrainTipsContent.add("Your CO2 emission for this trip by SkyTrain is " + (int) currentJourney.getEmissionsKM() + " g, consider to ride bikes or walk to cut your trip emission to 0!");
        skyTrainTipsContent.add("Distance of your trip by SkyTrain is " + currentJourney.getDistance() + " km, consider to ride bikes or walk if possible.");
        skyTrainTipsContent.add("Your CO2 emission for this trip by Skytrain is " + + (int) currentJourney.getEmissionsKM() + " g, consider to bike part of your trip to reduce your emission.");
        // need supporting methods.
        skyTrainTipsContent.add("You have traveled “getMonthSkytrainDistance()” by Skytrain within last 4 weeks, consider to ride bikes or walk more often in the future.");
        skyTrainTipsContent.add("Taking Skytrain within the last four weeks contributes to “getMonthBusEmission()” CO2 emission, consider to ride bikes or walk more often in the future.");
        skyTrainTipsContent.add("You have traveled “getYearSkytrainDistance()” by Skytrain over the past year, consider to ride bikes or walk more often in the future.");
        skyTrainTipsContent.add("Taking Skytrain within the last year contributes to “getYearSkyTrainEmission()” CO2 emission, consider to ride bikes more often in the future.");
    }

    public void updateBikeWalkTipsContent(Journey currentJourney){
        if (bikeWalkTipsContent.size()!=0) bikeWalkTipsContent.clear();
        bikeWalkTipsContent.add("Good job! Your trip does not generate any CO2. Keep it up!");
        //need supporting methods.
        bikeWalkTipsContent.add("You have biked and walked “getMonthBikeWalkDistance()” within last 4 weeks, keep it up!");
        bikeWalkTipsContent.add("You have biked and walked “getYearBikeWalkDistance()” within last year, keep it up!");
        bikeWalkTipsContent.add("Your estimated electricity usage per day is “getCurrentDailyElectricity()”, try to reduce your electricity usage.");
        bikeWalkTipsContent.add("You estimated gas usage per day is “getCurrentDailyGas()”, try to reduce your gas usage.");
        bikeWalkTipsContent.add("You have traveled “getYearBusDistance()” by Bus over the past year, consider to ride bikes or walk more often in the future.");
        bikeWalkTipsContent.add("You have traveled “getYearSkytrainDistance()” by Skytrain over past year, consider to ride bikes or walk more often in the future.");

    }

    // Todo: write similar functions for electricty and gas. use one of the following two templates.
    // Your current monthly electricity usage is “getCurrentMonthElectricity()”...
    // The CO2 emission from your current monthly eletricity usage is "getCO2EmissionByElectricity" ...


}
