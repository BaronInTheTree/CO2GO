package ca.cmpt276.carbonTracker.Internal_Logic;

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
    private static final String ERROR = "Sorry. System running out of tips.";

    // List of Tip objects for each category
    private List<Tip> recentShownTips;    // collection of seven tips recently shown to the users.
    private List<Tip> generalTips;       // tips shown on the main page. They are not relevant to any specific action.
    private List<Tip> carTips;
    private List<Tip> busTips;
    private List<Tip> skyTrainTips;
    private List<Tip> bikeWalkTips;
    private List<Tip> utilityTips;

    // List of actual tip contents for each category.
    private List<String> generalTipsContent;
    private List<String> carTipsContent;
    private List<String> busTipsContent;
    private List<String> skyTrainTipsContent;
    private List<String> bikeWalkTipsContent;
    private List<String> utilityTipsContent;

    public TipCollection() {

        recentShownTips = new ArrayList<>();
        generalTips = new ArrayList<>();
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
    }

    // return a tip after the user clicks the "View Tips" button on the main page.
    public String getGeneralTip(MonthYearSummary summary) {
        if (generalTips.size()== 0) {
            updateGeneralTipsContent(summary);
            initializeGeneralTips();
        }
        boolean findTip =false;

        while (!findTip) {
            for (int i = 0; i < numTips; i++) {
                if (!recentShownTips.contains(generalTips.get(i))) {
                    findTip=true;
                    if (recentShownTips.size() == numTips) {
                        recentShownTips.remove(0);
                    }
                    updateGeneralTipsContent(summary);
                    String currentTipContent = generalTipsContent.get(i);
                    generalTips.get(i).setTipContent(currentTipContent);
                    recentShownTips.add(generalTips.get(i));
                    return generalTips.get(i).getTipContent();
                }
            }
            if (!findTip) recentShownTips.remove(0);
        }
        return ERROR;
    }

    // return a tip to user after a new journey is created.
    public String getJourneyTip(Journey currentJourney, MonthYearSummary summary){
        List<Tip> tips;
        Journey.Type journeyType = currentJourney.getType();
        if (journeyType== Journey.Type.CAR) tips = carTips;
        else if (journeyType== Journey.Type.BUS) tips = busTips;
        else if (journeyType== Journey.Type.SKYTRAIN) tips = skyTrainTips;
        else tips = bikeWalkTips;

        if (tips.size()== 0){
            if (journeyType== Journey.Type.CAR) updateCarTipsContent(currentJourney, summary);
            if (journeyType== Journey.Type.BUS) updateBusTipsContent(currentJourney, summary);
            if (journeyType== Journey.Type.SKYTRAIN) updateSkyTrainTipsContent(currentJourney,summary);
            if (journeyType== Journey.Type.WALK_BIKE) updateBikeWalkTipsContent(currentJourney,summary);
            initializeJourneyTips(journeyType);
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
                    if (journeyType== Journey.Type.CAR) {
                        updateCarTipsContent(currentJourney,summary);
                        currentTipContent = carTipsContent.get(i);
                    }
                    if (journeyType== Journey.Type.BUS) {
                        updateBusTipsContent(currentJourney,summary);
                        currentTipContent = busTipsContent.get(i);
                    }
                    if (journeyType== Journey.Type.SKYTRAIN) {
                        updateSkyTrainTipsContent(currentJourney,summary);
                        currentTipContent = skyTrainTipsContent.get(i);
                    }
                    if (journeyType== Journey.Type.WALK_BIKE) {
                        updateBikeWalkTipsContent(currentJourney,summary);
                        currentTipContent = bikeWalkTipsContent.get(i);
                    }
                    tips.get(i).setTipContent(currentTipContent);
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
            updateUtilityTipsContent(currentBill);
            initializeUtilityTips();
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
                    utilityTips.get(i).setTipContent(currentTipContent);
                    recentShownTips.add(utilityTips.get(i));
                    return utilityTips.get(i).getTipContent();
                }
            }
            if (!findTip) recentShownTips.remove(0);
        }
        return ERROR;
    }

    // The following three functions: Initialize list of Tip objects for each category.
    public void initializeGeneralTips(){
        for (int i = 0; i< numTips; i++){
            Tip currentTip = new Tip(generalTipsContent.get(i));
            generalTips.add(currentTip);
        }
    }

    public void initializeJourneyTips(Journey.Type journeyType){
        if (journeyType== Journey.Type.CAR) {
            for (int i = 0; i< numTips; i++){
                Tip currentTip = new Tip(carTipsContent.get(i));
                carTips.add(currentTip);
            }
        }
        if (journeyType== Journey.Type.BUS){
            for (int i = 0; i< numTips; i++){
                Tip currentTip = new Tip(busTipsContent.get(i));
                busTips.add(currentTip);
            }
        }
        if (journeyType== Journey.Type.SKYTRAIN){
            for (int i = 0; i< numTips; i++){
                Tip currentTip = new Tip(skyTrainTipsContent.get(i));
                skyTrainTips.add(currentTip);
            }
        }
        if (journeyType== Journey.Type.WALK_BIKE){
            for (int i = 0; i< numTips; i++){
                Tip currentTip = new Tip(bikeWalkTipsContent.get(i));
                bikeWalkTips.add(currentTip);
            }
        }
    }

    public void initializeUtilityTips(){
        for (int i = 0; i< numTips; i++){
            Tip currentTip = new Tip(utilityTipsContent.get(i));
            utilityTips.add(currentTip);
        }
    }

    // The following six functions: Update tip contents for each category.
    // Note: all contents need to be updated to reflect current user data.
    public void updateGeneralTipsContent(MonthYearSummary summary){
        if (generalTipsContent.size()!= 0) generalTipsContent.clear();
        generalTipsContent.add("Your total driving distance within last 4 weeks are " + summary.getMonthCarDistance() + "km, consider to take more bus or skytrain to lower your driving distance in the future.");
        generalTipsContent.add("Driving within the last four weeks contributes to " +   (int)summary.getMonthCarEmission() + "g CO2 emission, consider to take more bus or skytrain to lower your emission in the future.");
        generalTipsContent.add("Driving last year contributes to " + (int)summary.getYearCarEmission() + "g CO2 emission, consider to take more bus or skytrain to lower your emission in the future.");
        generalTipsContent.add("Taking buses within the last four weeks contributes to " + (int)summary.getMonthBusEmission() + "g CO2 emission, consider to take skytrain more often to lower your emission in the future.");
        generalTipsContent.add("Taking Skytrain within the last four weeks contributes to " + (int)summary.getMonthSkytrainEmission() + "g CO2 emission, consider to ride bikes or walk more often to lower your emission in the future.");
        if (summary.getMonthWalkBikeDistance()==0){
            generalTipsContent.add("You have not entered any journey by bike or walk during last month, do not forget to record your journeys by bike/walk.");
        }
        else generalTipsContent.add("You have biked and walked " + summary.getMonthWalkBikeDistance() +"km within last 4 weeks, keep it up!");
        if (summary.getYearWalkBikeDistance()==0){
            generalTipsContent.add("You have not entered any journey by bike or walk during last year, do not forget to record your journeys by bike/walk.");
        }
        else generalTipsContent.add("You have biked and walked " + summary.getYearWalkBikeDistance() +"km over last year, keep it up!");
    }

    public void updateCarTipsContent(Journey currentJourney, MonthYearSummary summary) {
        if (carTipsContent.size() != 0) carTipsContent.clear();
        carTipsContent.add("Your CO2 emission per km drive is " + currentJourney.getEmissionPerKM() + " g/km, consider to drive a more economical car.");
        carTipsContent.add("Your CO2 emission for this trip by car is " + (int) currentJourney.getEmissionsKM() + " g, consider to take bus or skytrain to reduce your emission.");
        carTipsContent.add("Your CO2 emission for this trip by car is " + (int) currentJourney.getEmissionsKM() + " g, consider to ride bikes or walk to cut your emission to 0!");
        carTipsContent.add("Distance of your trip by car is " + currentJourney.getDistance() + " km, consider to take bus or skytrain to reduce your emission.");
        carTipsContent.add("Distance of your trip by car is " + currentJourney.getDistance() + " km, consider to ride bikes or walk if possible.");
        carTipsContent.add("Your total driving distance within last 4 weeks are " + summary.getMonthCarDistance()+ "km, consider to take more bus or skytrain in the future.");
        carTipsContent.add("Your total driving distance within last year are " + summary.getYearCarDistance() + "km, consider to take more bus or skytrain in the future.");
    }

    public void updateBusTipsContent(Journey currentJourney, MonthYearSummary summary){
        if (busTipsContent.size()!= 0) busTipsContent.clear();
        busTipsContent.add("Your CO2 emission for this trip by bus is " + (int)currentJourney.getEmissionsKM()+ " g, consider to ride bikes or walk to cut your trip emission to 0!");
        busTipsContent.add("Distance of your trip by bus is " + currentJourney.getDistance() + " km, an equivalent trip by skytrain will generate ~40% less CO2.");
        busTipsContent.add("Distance of your trip by bus is " + currentJourney.getDistance() + " km, consider to ride bikes or walk if possible.");
        busTipsContent.add("You have traveled " + summary.getMonthBusDistance()+ "km by Bus within last 4 weeks, consider to take skytrain more often in the future.");
        busTipsContent.add("You have traveled " + summary.getMonthBusDistance()+ " km by Bus within last 4 weeks, consider to ride bikes or walk more often in the future.");
        busTipsContent.add("Taking buses within the last four weeks contributes to " + (int)summary.getMonthBusEmission() + "g CO2 emission, consider to take skytrain or bike more often in the future.");
        busTipsContent.add("Taking buses within the last year contributes to " + (int)summary.getYearBusEmission() + "g CO2 emission, consider to take skytrain or bike more often in the future.");
    }

    public void updateSkyTrainTipsContent(Journey currentJourney, MonthYearSummary summary){
        if (skyTrainTipsContent.size()!= 0) skyTrainTipsContent.clear();
        skyTrainTipsContent.add("Your CO2 emission for this trip by SkyTrain is " + (int) currentJourney.getEmissionsKM() + " g, consider to ride bikes or walk to cut your trip emission to 0!");
        skyTrainTipsContent.add("Distance of your trip by SkyTrain is " + currentJourney.getDistance() + " km, consider to ride bikes or walk if possible.");
        skyTrainTipsContent.add("Your CO2 emission for this trip by Skytrain is " + + (int) currentJourney.getEmissionsKM() + " g, consider to bike part of your trip to reduce your emission.");
        skyTrainTipsContent.add("You have traveled " + summary.getMonthSkytrainDistance() + "km by Skytrain within last 4 weeks, consider to ride bikes or walk more often in the future.");
        skyTrainTipsContent.add("Taking Skytrain within the last four weeks contributes to "+ (int)summary.getMonthSkytrainEmission() +"g CO2 emission, consider to ride bikes or walk more often in the future.");
        skyTrainTipsContent.add("You have traveled " + summary.getYearSkytrainDistance() + "km by Skytrain over the past year, consider to ride bikes or walk more often in the future.");
        skyTrainTipsContent.add("Taking Skytrain within the last year contributes to " + (int)summary.getYearSkytrainEmission() + "g CO2 emission, consider to ride bikes more often in the future.");
    }

    public void updateBikeWalkTipsContent(Journey currentJourney, MonthYearSummary summary){
        if (bikeWalkTipsContent.size()!= 0) bikeWalkTipsContent.clear();
        bikeWalkTipsContent.add("Good job! Your trip does not generate any CO2. Keep it up!");
        bikeWalkTipsContent.add("You have biked and walked " + summary.getMonthWalkBikeDistance()+ "km within last 4 weeks, keep it up!");
        bikeWalkTipsContent.add("You have biked and walked " + summary.getYearWalkBikeDistance() + "km within last year, keep it up!");
        bikeWalkTipsContent.add("You have driven " + summary.getYearCarDistance()+ "km over the past year, consider to bike or walk more often in the future.");
        bikeWalkTipsContent.add("You have traveled " + summary.getYearBusDistance() + "km by Bus over the past year, consider to bike or walk more often in the future.");
        bikeWalkTipsContent.add("You have traveled " + summary.getYearSkytrainDistance()+ " km by Skytrain over the past year, consider to bike or walk more often in the future.");
        bikeWalkTipsContent.add("You have driven " + summary.getMonthCarDistance() + "km over the last 4 weeks, consider to bikes or walk more often in the future.");
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

    // Other utility method - Recent
    public int getRecentTipSize() { return recentShownTips.size(); }
    public void removeRecentTip(int index) {
        recentShownTips.remove(index);
    }
    public void addRecentTip(Tip tip) {
        recentShownTips.add(tip);
    }
    public Tip getRecentTipAtIndex(int index) {
        return recentShownTips.get(index);
    }

    // Other utility method - General
    public int getGeneralTipSize() { return generalTips.size(); }
    public void removeGeneralTip(int index) {
        generalTips.remove(index);
    }
    public void addGeneralTip(Tip tip) {
        generalTips.add(tip);
    }
    public Tip getGeneralTipAtIndex(int index) {
        return generalTips.get(index);
    }

    // Other utility method - Car
    public int getCarTipSize() { return carTips.size(); }
    public void removeCarTip(int index) {
        carTips.remove(index);
    }
    public void addCarTip(Tip tip) {
        carTips.add(tip);
    }
    public Tip getCarTipAtIndex(int index) {
        return carTips.get(index);
    }

    // Other utility method - Bus
    public int getBusTipSize() { return busTips.size(); }
    public void removeBusTip(int index) {
        busTips.remove(index);
    }
    public void addBusTip(Tip tip) {
        busTips.add(tip);
    }
    public Tip getBusTipAtIndex(int index) {
        return busTips.get(index);
    }

    // Other utility method - Skytrain
    public int getSkytrainTipSize() { return skyTrainTips.size(); }
    public void removeSkytrainTip(int index) {
        skyTrainTips.remove(index);
    }
    public void addSkytrainTip(Tip tip) {
        skyTrainTips.add(tip);
    }
    public Tip getSkytrainTipAtIndex(int index) {
        return skyTrainTips.get(index);
    }

    // Other utility method - Bike/Walk
    public int getBikeWalkTipSize() { return bikeWalkTips.size(); }
    public void removeBikeWalkTip(int index) {
        bikeWalkTips.remove(index);
    }
    public void addBikeWalkTip(Tip tip) {
        bikeWalkTips.add(tip);
    }
    public Tip getBikeWalkTipAtIndex(int index) {
        return bikeWalkTips.get(index);
    }


    // Other utility method - Utility
    public int getUtilityTipSize() { return utilityTips.size(); }
    public void removeUtilityTip(int index) {
        utilityTips.remove(index);
    }
    public void addUtilityTip(Tip tip) {
        utilityTips.add(tip);
    }
    public Tip getUtilityTipAtIndex(int index) {
        return utilityTips.get(index);
    }
}
