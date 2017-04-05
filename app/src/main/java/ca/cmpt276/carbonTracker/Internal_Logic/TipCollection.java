package ca.cmpt276.carbonTracker.Internal_Logic;

import android.content.Context;

import com.example.sasha.carbontracker.R;

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
    public String getGeneralTip(Context appContext, MonthYearSummary summary) {
        if (generalTips.size()== 0) {
            updateGeneralTipsContent(appContext, summary);
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
                    updateGeneralTipsContent(appContext,summary);
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
    public String getJourneyTip(Context appContext, Journey currentJourney, MonthYearSummary summary){
        List<Tip> tips;
        Journey.Type journeyType = currentJourney.getType();
        if (journeyType== Journey.Type.CAR) tips = carTips;
        else if (journeyType== Journey.Type.BUS) tips = busTips;
        else if (journeyType== Journey.Type.SKYTRAIN) tips = skyTrainTips;
        else tips = bikeWalkTips;

        if (tips.size()== 0){
            if (journeyType== Journey.Type.CAR) updateCarTipsContent(appContext, currentJourney, summary);
            if (journeyType== Journey.Type.BUS) updateBusTipsContent(appContext, currentJourney, summary);
            if (journeyType== Journey.Type.SKYTRAIN) updateSkyTrainTipsContent(appContext, currentJourney,summary);
            if (journeyType== Journey.Type.WALK_BIKE) updateBikeWalkTipsContent(appContext, currentJourney,summary);
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
                        updateCarTipsContent(appContext, currentJourney,summary);
                        currentTipContent = carTipsContent.get(i);
                    }
                    if (journeyType== Journey.Type.BUS) {
                        updateBusTipsContent(appContext, currentJourney,summary);
                        currentTipContent = busTipsContent.get(i);
                    }
                    if (journeyType== Journey.Type.SKYTRAIN) {
                        updateSkyTrainTipsContent(appContext, currentJourney,summary);
                        currentTipContent = skyTrainTipsContent.get(i);
                    }
                    if (journeyType== Journey.Type.WALK_BIKE) {
                        updateBikeWalkTipsContent(appContext, currentJourney,summary);
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
    public String getUtilityTip(Context appContext, Utility currentBill){
        if (utilityTips.size()== 0) {
            updateUtilityTipsContent(appContext,currentBill);
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
                    updateUtilityTipsContent(appContext,currentBill);
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
    public void updateGeneralTipsContent(Context appContext, MonthYearSummary summary){
        if (generalTipsContent.size()!= 0) generalTipsContent.clear();
        generalTipsContent.add(appContext.getString(R.string.generalTip1_1) + summary.getMonthCarDistance() + appContext.getString(R.string.generalTip1_2));
        generalTipsContent.add(appContext.getString(R.string.generalTip2_1) + (int)summary.getMonthCarEmission() + appContext.getString(R.string.generalTip2_2));
        generalTipsContent.add(appContext.getString(R.string.generalTip3_1) + (int)summary.getYearCarEmission() + appContext.getString(R.string.generalTip2_2));
        generalTipsContent.add(appContext.getString(R.string.generalTip4_1)+ (int)summary.getMonthBusEmission() + appContext.getString(R.string.generalTip4_2));
        generalTipsContent.add(appContext.getString(R.string.generalTip5_1) + (int)summary.getMonthSkytrainEmission() + appContext.getString(R.string.generalTip5_2));
        if (summary.getMonthWalkBikeDistance()==0){
            generalTipsContent.add(appContext.getString(R.string.generalTip6_1));
        }
        else generalTipsContent.add(appContext.getString(R.string.generalTip6_2) + summary.getMonthWalkBikeDistance() +appContext.getString(R.string.generalTip6_3));
        if (summary.getYearWalkBikeDistance()==0){
            generalTipsContent.add(appContext.getString(R.string.generalTip7_1));
        }
        else generalTipsContent.add(appContext.getString(R.string.generalTip6_2) + summary.getYearWalkBikeDistance() +appContext.getString(R.string.generalTip7_2));
    }

    public void updateCarTipsContent(Context appContext, Journey currentJourney, MonthYearSummary summary) {
        if (carTipsContent.size() != 0) carTipsContent.clear();
        carTipsContent.add(appContext.getString(R.string.carTip1_1) + currentJourney.getEmissionPerKM() + appContext.getString(R.string.carTip1_2));
        carTipsContent.add(appContext.getString(R.string.carTip2_1) + (int) currentJourney.getEmissionsKM() + appContext.getString(R.string.carTip2_2));
        carTipsContent.add(appContext.getString(R.string.carTip2_1) + (int) currentJourney.getEmissionsKM() + appContext.getString(R.string.carTip3_2));
        carTipsContent.add(appContext.getString(R.string.carTip4_1) + currentJourney.getDistance() + appContext.getString(R.string.carTip4_2));
        carTipsContent.add(appContext.getString(R.string.carTip5_1) + currentJourney.getDistance() + appContext.getString(R.string.carTip5_2));
        carTipsContent.add(appContext.getString(R.string.carTip6_1) + summary.getMonthCarDistance()+ appContext.getString(R.string.carTip6_2));
        carTipsContent.add(appContext.getString(R.string.carTip7_1) + summary.getYearCarDistance() + appContext.getString(R.string.carTip6_2));
    }

    public void updateBusTipsContent(Context appContext, Journey currentJourney, MonthYearSummary summary){
        if (busTipsContent.size()!= 0) busTipsContent.clear();
        busTipsContent.add(appContext.getString(R.string.carTip2_1) + (int)currentJourney.getEmissionsKM()+ appContext.getString(R.string.carTip3_2));
        busTipsContent.add(appContext.getString(R.string.carTip4_1) + currentJourney.getDistance() + appContext.getString(R.string.busTip2_2));
        busTipsContent.add(appContext.getString(R.string.carTip4_1) + currentJourney.getDistance() + appContext.getString(R.string.carTip5_2));
        busTipsContent.add(appContext.getString(R.string.busTip4_1) + summary.getMonthBusDistance()+ appContext.getString(R.string.busTip4_2));
        busTipsContent.add(appContext.getString(R.string.busTip4_1) + summary.getMonthBusDistance()+ appContext.getString(R.string.busTip5_2));
        busTipsContent.add(appContext.getString(R.string.busTip6_1) + (int)summary.getMonthBusEmission() + appContext.getString(R.string.busTip6_2));
        busTipsContent.add(appContext.getString(R.string.busTip7_1) + (int)summary.getYearBusEmission() + appContext.getString(R.string.busTip6_2));
    }

    public void updateSkyTrainTipsContent(Context appContext, Journey currentJourney, MonthYearSummary summary){
        if (skyTrainTipsContent.size()!= 0) skyTrainTipsContent.clear();
        skyTrainTipsContent.add(appContext.getString(R.string.carTip2_1) + (int) currentJourney.getEmissionsKM() + appContext.getString(R.string.carTip3_2));
        skyTrainTipsContent.add(appContext.getString(R.string.carTip4_1) + currentJourney.getDistance() + appContext.getString(R.string.carTip5_2));
        skyTrainTipsContent.add(appContext.getString(R.string.carTip2_1) + + (int) currentJourney.getEmissionsKM() + appContext.getString(R.string.skyTrainTip3_2));
        skyTrainTipsContent.add(appContext.getString(R.string.busTip4_1) + summary.getMonthSkytrainDistance() + appContext.getString(R.string.skyTrainTip4_2));
        skyTrainTipsContent.add(appContext.getString(R.string.skyTrainTip5_1)+ (int)summary.getMonthSkytrainEmission() + appContext.getString(R.string.generalTip5_2));
        skyTrainTipsContent.add(appContext.getString(R.string.busTip4_1) + summary.getYearSkytrainDistance() + appContext.getString(R.string.skyTrainTip6_2));
        skyTrainTipsContent.add(appContext.getString(R.string.skyTrainTip7_1) + (int)summary.getYearSkytrainEmission() + appContext.getString(R.string.generalTip5_2));
    }

    public void updateBikeWalkTipsContent(Context appContext, Journey currentJourney, MonthYearSummary summary){
        if (bikeWalkTipsContent.size()!= 0) bikeWalkTipsContent.clear();
        bikeWalkTipsContent.add(appContext.getString(R.string.bikeWalkTip1_1));
        bikeWalkTipsContent.add(appContext.getString(R.string.bikeWalkTip2_1)+ summary.getMonthWalkBikeDistance()+ appContext.getString(R.string.bikeWalkTip2_2));
        bikeWalkTipsContent.add(appContext.getString(R.string.bikeWalkTip2_1) + summary.getYearWalkBikeDistance() + appContext.getString(R.string.bikeWalkTip3_2));
        bikeWalkTipsContent.add(appContext.getString(R.string.carTip7_1) + summary.getYearCarDistance()+ appContext.getString(R.string.bikeWalkTip4_2));
        bikeWalkTipsContent.add(appContext.getString(R.string.busTip4_1)+ summary.getYearBusDistance() + appContext.getString(R.string.bikeWalkTip5_2));
        bikeWalkTipsContent.add(appContext.getString(R.string.busTip4_1) + summary.getYearSkytrainDistance()+ appContext.getString(R.string.skyTrainTip6_2));
        bikeWalkTipsContent.add(appContext.getString(R.string.generalTip1_1) + summary.getMonthCarDistance() + appContext.getString(R.string.bikeWalkTip4_2));
    }

    public void updateUtilityTipsContent(Context appContext, Utility currentBill){
        if (utilityTipsContent.size()!=0) utilityTipsContent.clear();
        utilityTipsContent.add(appContext.getString(R.string.utilityTip1_1) + (int) currentBill.getC02PerPerson() + appContext.getString(R.string.utilityTip1_2));
        utilityTipsContent.add(appContext.getString(R.string.utilityTip1_1) + (int) currentBill.getC02PerPerson() + appContext.getString(R.string.utilityTip2_2));
        utilityTipsContent.add(appContext.getString(R.string.utilityTip1_1) + (int) currentBill.getC02PerPerson() + appContext.getString(R.string.utilityTip3_2));
        utilityTipsContent.add(appContext.getString(R.string.utilityTip1_1) + (int) currentBill.getC02PerPerson() + appContext.getString(R.string.utilityTip4_2));
        utilityTipsContent.add(appContext.getString(R.string.utilityTip1_1)+ (int) currentBill.getC02PerPerson() + appContext.getString(R.string.utilityTip5_2));
        utilityTipsContent.add(appContext.getString(R.string.utilityTip1_1) + (int) currentBill.getC02PerPerson() + appContext.getString(R.string.utilityTip6_2));
        utilityTipsContent.add(appContext.getString(R.string.utilityTip1_1)+ (int) currentBill.getC02PerPerson() + appContext.getString(R.string.utilityTip7_2));
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
