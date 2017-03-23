package ca.cmpt276.carbonTracker;

import java.util.ArrayList;
import java.util.List;

/**
 * TipCollection, as the name implies, stores tips for the user to read. It is also able to maintain
 * specific types of tips, such as ones for cars or utilities, and are displayed accordingly once
 * the user has provided specific data for each of them. Specific types of tips are all stored
 * in collections and they are randomly cycled through with a unique one being displayed at least
 * once every seven times.
 *
 * Song Xiao - Team Teal
 */

public class TipCollection {

    private static final int numTips = 7;
    private static final int CAR = 0;
    private static final int BUS = 1;
    private static final int SKYTRAIN = 2;
    private static final int BIKEWALK = 3;
    private static final String ERROR = "Sorry. System running out of tips.";

    private List<Tip> recentShownTips;  // collection of seven tips recently shown to the users.
    private List<Tip> generalTips;   // tips shown on the main page. They are not relevant to any specific action.
    private List<Tip> carTips;
    private List<Tip> busTips;
    private List<Tip> skyTrainTips;
    private List<Tip> bikeWalkTips;
    private List<Tip> utilityTips;

    // Collections of actual tip content of each category.
    private List<String> generalTipsContent;
    private List<String> carTipsContent;
    private List<String> busTipsContent;
    private List<String> skyTrainTipsContent;
    private List<String> bikeWalkTipsContent;
    private List<String> utilityTipsContent;

    public TipCollection() {
        generalTips = new ArrayList<>();
        recentShownTips = new ArrayList<>();

        carTips = new ArrayList<>();
        busTips = new ArrayList<>();
        skyTrainTips = new ArrayList<>();
        bikeWalkTips = new ArrayList<>();
        utilityTips = new ArrayList<>();

        generalTipsContent = new ArrayList<>();
        carTipsContent = new ArrayList<>();
        busTipsContent = new ArrayList<>();
        skyTrainTipsContent = new ArrayList<>();
        bikeWalkTipsContent = new ArrayList<>();
        utilityTipsContent  = new ArrayList<>();

        updateGeneralTipsContent();
    }

    // return a tip after the user clicks the "View Tips" button on the main page.
    public String getGeneralTip() {
        if (generalTips.size()== 0) {
            generalTipsInitialize();
        }

        boolean findTip =false;

        while (!findTip) {
            for (int i = 0; i < numTips; i++) {
                if (!recentShownTips.contains(generalTips.get(i))) {
                    findTip=true;
                    if (recentShownTips.size() == numTips) {
                        recentShownTips.remove(0);
                    }
                    updateGeneralTipsContent();
                    String currentTipContent = generalTipsContent.get(i);
                    Tip currentTip = new Tip(currentTipContent);
                    generalTips.set(i, currentTip);
                    recentShownTips.add(generalTips.get(i));
                    return generalTips.get(i).getTipContent();
                }
            }
            if (!findTip) recentShownTips.remove(0);
        }
        return ERROR;
    }

    // return a tip to user after a new journey is created.
    public String getJourneyTip(int journeyType, Journey currentJourney){
        List<Tip> tips = new ArrayList<>();
        if (journeyType== CAR) tips = carTips;
        if (journeyType== BUS) tips = busTips;
        if (journeyType== SKYTRAIN) tips = skyTrainTips;
        if (journeyType== BIKEWALK) tips = bikeWalkTips;

        if (tips.size()== 0){
            if (journeyType== CAR) updateCarTipsContent(currentJourney);
            if (journeyType== BUS) updateBusTipsContent(currentJourney);
            if (journeyType== SKYTRAIN) updateSkyTrainTipsContent(currentJourney);
            if (journeyType== BIKEWALK) updateBikeWalkTipsContent(currentJourney);
            journeyInitialize(journeyType);
        }

        boolean findTip= false;

        while (!findTip) {
            for (int i = 0; i < numTips; i++) {
                if (!recentShownTips.contains(tips.get(i))) {
                    findTip = true;
                    if (recentShownTips.size() == numTips) {
                        recentShownTips.remove(0);
                    }
                    String currentTipContent="";
                    if (journeyType== CAR) {
                        updateCarTipsContent(currentJourney);
                        currentTipContent = carTipsContent.get(i);
                    }
                    if (journeyType== BUS) {
                        updateBusTipsContent(currentJourney);
                        currentTipContent = busTipsContent.get(i);
                    }
                    if (journeyType== SKYTRAIN) {
                        updateSkyTrainTipsContent(currentJourney);
                        currentTipContent = skyTrainTipsContent.get(i);
                    }
                    if (journeyType== BIKEWALK) {
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
        return ERROR;
    }

    // return a tip to the user after a new utility bill is entered.
    public String getUtilityTip(Utility currentBill){
        if (utilityTips.size()== 0) {
            utilityTipsInitialize();
        }

        boolean findTip =false;

        while (!findTip) {
            for (int i = 0; i < numTips; i++) {
                if (!recentShownTips.contains(utilityTips.get(i))) {
                    findTip=true;
                    if (recentShownTips.size() == numTips) {
                        recentShownTips.remove(0);
                    }
                    updateUtilityTipsContent(currentBill);
                    String currentTipContent = utilityTipsContent.get(i);
                    Tip currentTip = new Tip(currentTipContent);
                    utilityTips.set(i, currentTip);
                    recentShownTips.add(utilityTips.get(i));
                    return utilityTips.get(i).getTipContent();
                }
            }
            if (!findTip) recentShownTips.remove(0);
        }
        return ERROR;
    }


    // Initialize collections of tips for each category.

    public void generalTipsInitialize(){
        for (int i = 0; i< numTips; i++){
            Tip currentTip = new Tip(generalTipsContent.get(i));
            generalTips.add(currentTip);
        }
    }

    public void journeyInitialize(int journeyType){
        if (journeyType== CAR) {
            for (int i = 0; i< numTips; i++){
                Tip currentTip = new Tip(carTipsContent.get(i));
                carTips.add(currentTip);
            }
        }
        if (journeyType== BUS){
            for (int i = 0; i< numTips; i++){
                Tip currentTip = new Tip(busTipsContent.get(i));
                busTips.add(currentTip);
            }
        }
        if (journeyType== SKYTRAIN){
            for (int i = 0; i< numTips; i++){
                Tip currentTip = new Tip(skyTrainTipsContent.get(i));
                skyTrainTips.add(currentTip);
            }
        }
        if (journeyType== BIKEWALK){
            for (int i = 0; i< numTips; i++){
                Tip currentTip = new Tip(bikeWalkTipsContent.get(i));
                bikeWalkTips.add(currentTip);
            }
        }
    }

    public void utilityTipsInitialize(){
        for (int i = 0; i< numTips; i++){
            Tip currentTip = new Tip(utilityTipsContent.get(i));
            utilityTips.add(currentTip);
        }
    }

    // update tip contents for each category. Note: all contents need to be updated to reflect current user data.

    public void updateGeneralTipsContent(){
        if (generalTipsContent.size()!= 0) generalTipsContent.clear();
        // The following tips need getMonthDriveDistance(), getMonthDriveEmission() and other corresponding methods.
        generalTipsContent.add("Your total driving distance within last 4 weeks are getMonthDriveDistance(), consider to take more bus or skytrain to lower your driving distance in the future.");
        generalTipsContent.add("Driving within the last four weeks contributes to getMonthDriveEmission() CO2 emission, consider to take more bus or skytrain to lower your emission in the future.");
        generalTipsContent.add("Driving last year contributes to getYearDriveEmission() CO2 emission, consider to take more bus or skytrain to lower your emission in the future.");
        generalTipsContent.add("Taking buses within the last four weeks contributes to “getMonthBusEmission()” CO2 emission, consider to take skytrain more often to lower your emission in the future.");
        generalTipsContent.add("Taking Skytrain within the last four weeks contributes to “getMonthBusEmission()” CO2 emission, consider to ride bikes or walk more often to lower your emission in the future.");
        generalTipsContent.add("You have biked and walked “getMonthBikeWalkDistance()” within last 4 weeks, keep it up!");
        generalTipsContent.add("Your electricity usage over the past year is “getYearElectricity()”, try to reduce your electricity usage to lower your total CO2 emission.");
    }

    public void updateCarTipsContent(Journey currentJourney) {
        if (carTipsContent.size() != 0) carTipsContent.clear();
        carTipsContent.add("Your CO2 emission per km drive is " + currentJourney.getEmissionPerKM() + " g/km, consider to drive a more economical car.");
        carTipsContent.add("Your CO2 emission for this trip by car is " + (int) currentJourney.getEmissionsKM() + " g, consider to take bus or skytrain to reduce your emission.");
        carTipsContent.add("Your CO2 emission for this trip by car is " + (int) currentJourney.getEmissionsKM() + " g, consider to ride bikes or walk to cut your emission to 0!");
        carTipsContent.add("Distance of your trip by car is " + currentJourney.getDistance() + " km, consider to take bus or skytrain to reduce your emission.");
        carTipsContent.add("Distance of your trip by car is " + currentJourney.getDistance() + " km, consider to ride bikes or walk if possible.");
        // The following tips need getMonthDriveDistance() and getMonthDriveEmission() method.
        carTipsContent.add("Your total driving distance within last 4 weeks are getMonthDriveDistance() km, consider to take more bus or skytrain in the future.");
        carTipsContent.add("Your total driving distance within last year are getYearDriveDistance() km, consider to take more bus or skytrain in the future.");
    }

    public void updateBusTipsContent(Journey currentJourney){
        if (busTipsContent.size()!= 0) busTipsContent.clear();
        busTipsContent.add("Your CO2 emission for this trip by bus is " + (int)currentJourney.getEmissionsKM()+ " g, consider to ride bikes or walk to cut your trip emission to 0!");
        busTipsContent.add("Distance of your trip by bus is " + currentJourney.getDistance() + " km, an equivalent trip by skytrain will generate ~40% less CO2.");
        busTipsContent.add("Distance of your trip by bus is " + currentJourney.getDistance() + " km, consider to ride bikes or walk if possible.");
        // The following tips need supporting methods.
        busTipsContent.add("You have traveled “getMonthBusDistance()” by Bus within last 4 weeks, consider to take skytrain more often in the future.");
        busTipsContent.add("You have traveled “getMonthBusDistance()” by Bus within last 4 weeks, consider to ride bikes or walk more often in the future.");
        busTipsContent.add("Taking buses within the last four weeks contributes to “getMonthBusEmission()” CO2 emission, consider to take skytrain more often in the future.");
        busTipsContent.add("Taking buses within the last four weeks contributes to “getMonthBusEmission()” CO2 emission, consider to ride bikes more often in the future.");

    }

    public void updateSkyTrainTipsContent(Journey currentJourney){
        if (skyTrainTipsContent.size()!= 0) skyTrainTipsContent.clear();
        skyTrainTipsContent.add("Your CO2 emission for this trip by SkyTrain is " + (int) currentJourney.getEmissionsKM() + " g, consider to ride bikes or walk to cut your trip emission to 0!");
        skyTrainTipsContent.add("Distance of your trip by SkyTrain is " + currentJourney.getDistance() + " km, consider to ride bikes or walk if possible.");
        skyTrainTipsContent.add("Your CO2 emission for this trip by Skytrain is " + + (int) currentJourney.getEmissionsKM() + " g, consider to bike part of your trip to reduce your emission.");
        // The following tips need supporting methods.
        skyTrainTipsContent.add("You have traveled “getMonthSkytrainDistance()” by Skytrain within last 4 weeks, consider to ride bikes or walk more often in the future.");
        skyTrainTipsContent.add("Taking Skytrain within the last four weeks contributes to “getMonthBusEmission()” CO2 emission, consider to ride bikes or walk more often in the future.");
        skyTrainTipsContent.add("You have traveled “getYearSkytrainDistance()” by Skytrain over the past year, consider to ride bikes or walk more often in the future.");
        skyTrainTipsContent.add("Taking Skytrain within the last year contributes to “getYearSkyTrainEmission()” CO2 emission, consider to ride bikes more often in the future.");
    }

    public void updateBikeWalkTipsContent(Journey currentJourney){
        if (bikeWalkTipsContent.size()!= 0) bikeWalkTipsContent.clear();
        bikeWalkTipsContent.add("Good job! Your trip does not generate any CO2. Keep it up!");
        // The following tips need supporting methods.
        bikeWalkTipsContent.add("You have biked and walked “getMonthBikeWalkDistance()” within last 4 weeks, keep it up!");
        bikeWalkTipsContent.add("You have biked and walked “getYearBikeWalkDistance()” within last year, keep it up!");
        bikeWalkTipsContent.add("Your estimated electricity usage per day is “getCurrentDailyElectricity()”, try to reduce your electricity usage.");
        bikeWalkTipsContent.add("You estimated gas usage per day is “getCurrentDailyGas()”, try to reduce your gas usage.");
        bikeWalkTipsContent.add("You have traveled “getYearBusDistance()” by Bus over the past year, consider to ride bikes or walk more often in the future.");
        bikeWalkTipsContent.add("You have traveled “getYearSkytrainDistance()” by Skytrain over past year, consider to ride bikes or walk more often in the future.");

    }

    public void updateUtilityTipsContent(Utility currentBill){
        if (utilityTipsContent.size()!=0) utilityTipsContent.clear();
        utilityTipsContent.add("Your CO2 emission from the utility bill entered is ," + (int) currentBill.getC02PerPerson() + " g. If your heating equipment is old, consider to upgrade it to more efficient models." );
        utilityTipsContent.add("Your CO2 emission from the utility bill entered is ," + (int) currentBill.getC02PerPerson() + " g. If you use air conditioner, consider to reduce its usage by creating better airflow in your home.");
        utilityTipsContent.add("Your CO2 emission from the utility bill entered is ," + (int) currentBill.getC02PerPerson() + " g. Consider to turn off computers and other electronic devices when not being used.");
        utilityTipsContent.add("Your CO2 emission from the utility bill entered is ," + (int) currentBill.getC02PerPerson() + " g. Consider to install a programmable thermostat to automatically reduce unnecessary heating." );
        utilityTipsContent.add("Your CO2 emission from the utility bill entered is ,"+ (int) currentBill.getC02PerPerson() + " g. Avoid over heating your laundry and try to hang dry your laundry." );
        utilityTipsContent.add("Your CO2 emission from the utility bill entered is ," + (int) currentBill.getC02PerPerson() + " g. If your fridge or stove is old, consider replace it with new ones with higher energy efficiency.");
        utilityTipsContent.add("Your CO2 emission from the utility bill entered is ,"+ (int) currentBill.getC02PerPerson() + " g, Consider to use energy saving light bulbs wherever possible.");
    }

    public int getRecentTipSize() {
        return recentShownTips.size();
    }
    public void removeRecentTip(int index) {
        recentShownTips.remove(index);
    }
    public void addRecentTip(Tip tip) {
        recentShownTips.add(tip);
    }
    public Tip getRecentTipAtIndex(int index) {
        return recentShownTips.get(index);
    }

}
