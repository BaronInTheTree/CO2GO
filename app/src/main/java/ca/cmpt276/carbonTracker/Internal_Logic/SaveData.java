package ca.cmpt276.carbonTracker.Internal_Logic;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONObject;

import static android.content.Context.MODE_PRIVATE;
/**
 * The SaveData activity saves utilities, journey, cars, and routes created into a SharedPreferences
 * file. We iterate through a loop for each collection, encompass it into the file, and then we are
 * able to also populate the collections by calling the respective load methods.
 *
 * A note that the tip history is saved in the TipCollection class so we do not access it here.
 *
 * @author Team Teal
 */

public class SaveData extends JSONObject  {
    private static final int MAX_TIP_SIZE = 7; // Maximum number of recent tips.
    ////////////////////
    // Saving Route
    ////////////////////
    public static void loadAllRoutes(Context context) {
        loadRoutes(context);
        loadHiddenRoutes(context);
    }

    private static void loadRoutes(Context context) {
        CarbonModel model = CarbonModel.getInstance();
        RouteCollection rc = model.getRouteCollection();
        int index = 0;
        while (index < rc.getListSize()) {
            rc.removeRoute(index);
        }

        SharedPreferences prefs = context.getSharedPreferences("RouteCollection", MODE_PRIVATE);

        while (!prefs.getString("Route"+index, "").equals("")) {
            Gson routeData = new Gson();
            String jsonRouteData = prefs.getString("Route" + index, null);
            Route route = routeData.fromJson(jsonRouteData, Route.class);
            rc.addRoute(route);
            index++;
        }
    }

    private static void loadHiddenRoutes(Context context) {
        CarbonModel model = CarbonModel.getInstance();
        RouteCollection rc = model.getRouteCollection();
        int index = 0;
        while (index < rc.getHiddenListSize()) {
            rc.removeHiddenRoute(index);
        }

        SharedPreferences prefs = context.getSharedPreferences("HiddenRouteCollection", MODE_PRIVATE);

        while (!prefs.getString("HiddenRoute"+index, "").equals("")) {
            Gson routeData = new Gson();
            String jsonRouteData = prefs.getString("HiddenRoute" + index, null);
            Route route = routeData.fromJson(jsonRouteData, Route.class);
            rc.addHiddenRoute(route);
            index++;
        }
    }

    public static void saveRoutes(Context context) {
        CarbonModel model = CarbonModel.getInstance();
        RouteCollection rc = model.getRouteCollection();
        SharedPreferences prefs = context.getSharedPreferences("RouteCollection", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        for (int i = 0; i < rc.getListSize(); i++) {
            Gson routeData = new Gson();
            String jsonRouteData = routeData.toJson(rc.getRouteAtIndex(i));
            editor.putString("Route"+i, jsonRouteData);
        }
        editor.commit();
        editor.apply();
    }

    public static void saveHiddenRoutes(Context context) {
        CarbonModel model = CarbonModel.getInstance();
        RouteCollection rc = model.getRouteCollection();
        SharedPreferences prefs = context.getSharedPreferences("HiddenRouteCollection", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        for (int i = 0; i < rc.getHiddenListSize(); i++) {
            Gson routeData = new Gson();
            String jsonRouteData = routeData.toJson(rc.getHiddenRouteAtIndex(i));
            editor.putString("HiddenRoute"+i, jsonRouteData);
        }
        editor.commit();
        editor.apply();
    }


    //////////////////
    // Saving Car
    /////////////////

    public static void saveCars(Context context) {
        CarbonModel model = CarbonModel.getInstance();
        CarCollection cc = model.getCarCollection();
        SharedPreferences prefs = context.getSharedPreferences("CarCollection", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        for (int i = 0; i < cc.getListSize(); i++) {
            Gson carData = new Gson();
            String jsonCarData = carData.toJson(cc.getCarAtIndex(i));
            editor.putString("Car"+i, jsonCarData);
        }
        editor.commit();
        editor.apply();
    }

    public static void saveHiddenCars(Context context) {
        CarbonModel model = CarbonModel.getInstance();
        CarCollection cc = model.getCarCollection();
        SharedPreferences prefs = context.getSharedPreferences("HiddenCarCollection", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        for (int i = 0; i < cc.getHiddenListSize(); i++) {
            Gson carData = new Gson();
            String jsonCarData = carData.toJson(cc.getHiddenCarAtIndex(i));
            editor.putString("HiddenCar"+i, jsonCarData);
        }
        editor.commit();
        editor.apply();
    }

    public static void loadAllCars(Context context) {
        loadCars(context);
        loadHiddenCars(context);
    }

    private static void loadCars(Context context) {
        CarbonModel model = CarbonModel.getInstance();
        CarCollection cc = model.getCarCollection();
        int index = 0;
        while (index < cc.getListSize()) {
            cc.removeCar(index);
        }

        SharedPreferences prefs = context.getSharedPreferences("CarCollection", MODE_PRIVATE);

        while (!prefs.getString("Car"+index, "").equals("")) {
            Gson carData = new Gson();
            String jsonCarData = prefs.getString("Car" + index, null);
            Car car = carData.fromJson(jsonCarData, Car.class);
            cc.addCar(car);
            index++;
        }
    }

    private static void loadHiddenCars(Context context) {
        CarbonModel model = CarbonModel.getInstance();
        CarCollection cc = model.getCarCollection();
        int index = 0;
        while (index < cc.getHiddenListSize()) {
            cc.removeHiddenCar(index);
        }

        SharedPreferences prefs = context.getSharedPreferences("HiddenCarCollection", MODE_PRIVATE);

        while (!prefs.getString("HiddenCar"+index, "").equals("")) {
            Gson carData = new Gson();
            String jsonCarData = prefs.getString("HiddenCar" + index, null);
            Car car = carData.fromJson(jsonCarData, Car.class);
            cc.addHiddenCar(car);
            index++;
        }
    }


    //////////////////
    // Saving Journey
    /////////////////

    public static void loadJourneys(Context context) {
        CarbonModel model = CarbonModel.getInstance();
        JourneyCollection jc = model.getJourneyCollection();
        int index = 0;
        while (index < jc.getNumberJourneys()) {
            jc.deleteJourney(index);
        }

        SharedPreferences prefs = context.getSharedPreferences("JourneyCollection", MODE_PRIVATE);

        while (!prefs.getString("Journey"+index, "").equals("")) {
            Gson journeyData = new Gson();
            String jsonJourneyData = prefs.getString("Journey" + index, null);
            Journey journey = journeyData.fromJson(jsonJourneyData, Journey.class);
            jc.addJourney(journey);
            index++;
        }
    }

    public static void saveJourneys(Context context) {
        CarbonModel model = CarbonModel.getInstance();
        JourneyCollection jc = model.getJourneyCollection();
        SharedPreferences prefs = context.getSharedPreferences("JourneyCollection", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        for (int i = 0; i < jc.getNumberJourneys(); i++) {
            Gson journeyData = new Gson();
            String jsonJourneyData = journeyData.toJson(jc.getJourneyAtIndex(i));
            editor.putString("Journey"+i, jsonJourneyData);
        }
        editor.commit();
        editor.apply();
    }

    //////////////////
    // Saving Favourite Journey
    /////////////////

    public static void loadFavouriteJourneys(Context context) {
        CarbonModel model = CarbonModel.getInstance();
        JourneyCollection favourites = model.getFavouriteJourneyCollection();
        int index = 0;
        while (index < favourites.getNumberJourneys()) {
            favourites.deleteJourney(index);
        }

        SharedPreferences prefs = context.getSharedPreferences("FavouriteJourneyCollection", MODE_PRIVATE);

        while (!prefs.getString("FavouriteJourney"+index, "").equals("")) {
            Gson journeyData = new Gson();
            String jsonJourneyData = prefs.getString("FavouriteJourney" + index, null);
            Journey journey = journeyData.fromJson(jsonJourneyData, Journey.class);
            favourites.addJourney(journey);
            index++;
        }
    }

    public static void saveFavouriteJourneys(Context context) {
        CarbonModel model = CarbonModel.getInstance();
        JourneyCollection favourites = model.getFavouriteJourneyCollection();
        SharedPreferences prefs = context.getSharedPreferences("FavouriteJourneyCollection", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        for (int i = 0; i < favourites.getNumberJourneys(); i++) {
            Gson journeyData = new Gson();
            String jsonJourneyData = journeyData.toJson(favourites.getJourneyAtIndex(i));
            editor.putString("FavouriteJourney"+i, jsonJourneyData);
        }
        editor.commit();
        editor.apply();
    }

    //////////////////
    // Saving Utility
    /////////////////

    public static void loadUtilities(Context context) {
        CarbonModel model = CarbonModel.getInstance();
        UtilityCollection uc = model.getUtilityCollection();
        int index = 0;
        while (index < uc.getNumberUtilities()) {
            uc.deleteUtility(index);
        }

        SharedPreferences prefs = context.getSharedPreferences("UtilityCollection", MODE_PRIVATE);

        while (!prefs.getString("Utility"+index, "").equals("")) {
            Gson utilityData = new Gson();
            String jsonUtilityData = prefs.getString("Utility" + index, null);
            Utility utility = utilityData.fromJson(jsonUtilityData, Utility.class);
            uc.addUtility(utility);
            index++;
        }
    }

    public static void saveUtilities(Context context) {
        CarbonModel model = CarbonModel.getInstance();
        UtilityCollection uc = model.getUtilityCollection();
        SharedPreferences prefs = context.getSharedPreferences("UtilityCollection", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        for (int i = 0; i < uc.getNumberUtilities(); i++) {
            Gson utilityData = new Gson();
            String jsonUtilityData = utilityData.toJson(uc.getUtility(i));
            editor.putString("Utility"+i, jsonUtilityData);
        }
        editor.commit();
        editor.apply();
    }


    //////////////////
    // Saving Tip
    /////////////////

    public static void loadTips(Context context) {
        CarbonModel model = CarbonModel.getInstance();
        TipCollection tc = model.getTips();
        int index = 0;

        while (index < tc.getRecentTipSize()) {
            tc.removeRecentTip(index);
        }
        while (index < tc.getGeneralTipSize()) {
            tc.removeGeneralTip(index);
        }
        while (index < tc.getCarTipSize()) {
            tc.removeCarTip(index);
        }
        while (index < tc.getBusTipSize()) {
            tc.removeBusTip(index);
        }
        while (index < tc.getBikeWalkTipSize()) {
            tc.removeBikeWalkTip(index);
        }
        while (index < tc.getSkytrainTipSize()) {
            tc.removeSkytrainTip(index);
        }
        while (index < tc.getUtilityTipSize()) {
            tc.removeUtilityTip(index);
        }


        SharedPreferences prefsRecent = context.getSharedPreferences("RecentTips", MODE_PRIVATE);
        SharedPreferences prefsGeneral = context.getSharedPreferences("GeneralTips", MODE_PRIVATE);
        SharedPreferences prefsCar = context.getSharedPreferences("CarTips", MODE_PRIVATE);
        SharedPreferences prefsBus = context.getSharedPreferences("BusTips", MODE_PRIVATE);
        SharedPreferences prefsSkytrain = context.getSharedPreferences("SkytrainTips", MODE_PRIVATE);
        SharedPreferences prefsBikeWalk = context.getSharedPreferences("BikeWalkTips", MODE_PRIVATE);
        SharedPreferences prefsUtility = context.getSharedPreferences("UtilityTips", MODE_PRIVATE);

        while (!prefsRecent.getString("Tip"+index, "").equals("") && index < MAX_TIP_SIZE) {
            Gson tipData = new Gson();
            String jsonTipData = prefsRecent.getString("Tip" + index, null);
            Tip tip = tipData.fromJson(jsonTipData, Tip.class);
            tc.addRecentTip(tip);
            index++;
        }

        index = 0;
        while (!prefsGeneral.getString("Tip"+index, "").equals("")) {
            Gson tipData = new Gson();
            String jsonTipData = prefsGeneral.getString("Tip" + index, null);
            Tip tip = tipData.fromJson(jsonTipData, Tip.class);
            tc.addGeneralTip(tip);
            index++;
        }

        index = 0;
        while (!prefsCar.getString("Tip"+index, "").equals("")) {
            Gson tipData = new Gson();
            String jsonTipData = prefsCar.getString("Tip" + index, null);
            Tip tip = tipData.fromJson(jsonTipData, Tip.class);
            tc.addCarTip(tip);
            index++;
        }

        index = 0;
        while (!prefsBus.getString("Tip"+index, "").equals("")) {
            Gson tipData = new Gson();
            String jsonTipData = prefsBus.getString("Tip" + index, null);
            Tip tip = tipData.fromJson(jsonTipData, Tip.class);
            tc.addBusTip(tip);
            index++;
        }

        index = 0;
        while (!prefsSkytrain.getString("Tip"+index, "").equals("")) {
            Gson tipData = new Gson();
            String jsonTipData = prefsSkytrain.getString("Tip" + index, null);
            Tip tip = tipData.fromJson(jsonTipData, Tip.class);
            tc.addSkytrainTip(tip);
            index++;
        }

        index = 0;
        while (!prefsBikeWalk.getString("Tip"+index, "").equals("")) {
            Gson tipData = new Gson();
            String jsonTipData = prefsBikeWalk.getString("Tip" + index, null);
            Tip tip = tipData.fromJson(jsonTipData, Tip.class);
            tc.addBikeWalkTip(tip);
            index++;
        }

        index = 0;
        while (!prefsUtility.getString("Tip"+index, "").equals("")) {
            Gson tipData = new Gson();
            String jsonTipData = prefsUtility.getString("Tip" + index, null);
            Tip tip = tipData.fromJson(jsonTipData, Tip.class);
            tc.addUtilityTip(tip);
            index++;
            System.out.println("load");
        }
    }

    public static void saveTips(Context context) {
        CarbonModel model = CarbonModel.getInstance();
        TipCollection tc = model.getTips();

        SharedPreferences prefsRecent = context.getSharedPreferences("RecentTips", MODE_PRIVATE);
        SharedPreferences.Editor editorRecent = prefsRecent.edit();
        editorRecent.clear();

        SharedPreferences prefsGeneral = context.getSharedPreferences("GeneralTips", MODE_PRIVATE);
        SharedPreferences.Editor editorGeneral = prefsGeneral.edit();
        editorGeneral.clear();

        SharedPreferences prefsCar = context.getSharedPreferences("CarTips", MODE_PRIVATE);
        SharedPreferences.Editor editorCar = prefsCar.edit();
        editorCar.clear();

        SharedPreferences prefsBus = context.getSharedPreferences("BusTips", MODE_PRIVATE);
        SharedPreferences.Editor editorBus = prefsBus.edit();
        editorBus.clear();

        SharedPreferences prefsSkytrain = context.getSharedPreferences("SkytrainTips", MODE_PRIVATE);
        SharedPreferences.Editor editorSkytrain = prefsSkytrain.edit();
        editorSkytrain.clear();

        SharedPreferences prefsBikeWalk = context.getSharedPreferences("BikeWalkTips", MODE_PRIVATE);
        SharedPreferences.Editor editorBikeWalk = prefsBikeWalk.edit();
        editorBikeWalk.clear();

        SharedPreferences prefsUtility = context.getSharedPreferences("UtilityTips", MODE_PRIVATE);
        SharedPreferences.Editor editorUtility = prefsUtility.edit();
        editorUtility.clear();

        for (int i = 0; i < tc.getRecentTipSize(); i++) {
            Gson tipData = new Gson();
            String jsonTipData = tipData.toJson(tc.getRecentTipAtIndex(i));
            editorRecent.putString("Tip"+i, jsonTipData);
        }
        editorRecent.commit();
        editorRecent.apply();

        for (int i = 0; i < tc.getGeneralTipSize(); i++) {
            Gson tipData = new Gson();
            String jsonTipData = tipData.toJson(tc.getGeneralTipAtIndex(i));
            editorGeneral.putString("Tip"+i, jsonTipData);
        }
        editorGeneral.commit();
        editorGeneral.apply();

        for (int i = 0; i < tc.getCarTipSize(); i++) {
            Gson tipData = new Gson();
            String jsonTipData = tipData.toJson(tc.getCarTipAtIndex(i));
            editorCar.putString("Tip"+i, jsonTipData);
        }
        editorCar.commit();
        editorCar.apply();

        for (int i = 0; i < tc.getBusTipSize(); i++) {
            Gson tipData = new Gson();
            String jsonTipData = tipData.toJson(tc.getBusTipAtIndex(i));
            editorBus.putString("Tip"+i, jsonTipData);
        }
        editorBus.commit();
        editorBus.apply();

        for (int i = 0; i < tc.getSkytrainTipSize(); i++) {
            Gson tipData = new Gson();
            String jsonTipData = tipData.toJson(tc.getSkytrainTipAtIndex(i));
            editorSkytrain.putString("Tip"+i, jsonTipData);
        }
        editorSkytrain.commit();
        editorSkytrain.apply();

        for (int i = 0; i < tc.getBikeWalkTipSize(); i++) {
            Gson tipData = new Gson();
            String jsonTipData = tipData.toJson(tc.getBikeWalkTipAtIndex(i));
            editorBikeWalk.putString("Tip"+i, jsonTipData);
        }
        editorBikeWalk.commit();
        editorBikeWalk.apply();

        for (int i = 0; i < tc.getUtilityTipSize(); i++) {
            Gson tipData = new Gson();
            String jsonTipData = tipData.toJson(tc.getUtilityTipAtIndex(i));
            editorUtility.putString("Tip"+i, jsonTipData);
        }
        editorUtility.commit();
        editorUtility.apply();
    }

    /////////////////////
    // Saving Tree Unit
    /////////////////////
    public static void saveTreeUnit(Context context) {
        CarbonModel model = CarbonModel.getInstance();
        SharedPreferences prefs = context.getSharedPreferences("TreeUnitSaveData", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.putBoolean("TreeEnabled", model.getTreeUnit().getTreeUnitStatus());
        editor.commit();
        editor.apply();
        System.out.println("save" + model.getTreeUnit().getTreeUnitStatus());
    }


    public static void loadTreeUnit(Context context) {
        CarbonModel model = CarbonModel.getInstance();
        SharedPreferences prefs = context.getSharedPreferences("TreeUnitSaveData", MODE_PRIVATE);
        model.getTreeUnit().setTreeUnitStatus(prefs.getBoolean("TreeEnabled", false));
        System.out.println("load" + model.getTreeUnit().getTreeUnitStatus());
    }
}
