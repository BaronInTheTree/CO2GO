package ca.cmpt276.carbonTracker;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import static android.content.Context.MODE_PRIVATE;
/**
 * The SaveData activity saves utilities, journey, cars, and routes created into a SharedPreferences
 * file. We iterate through a loop for each collection, encompass it into the file, and then we are
 * able to also populate the collections by calling the respective load methods.
 *
 * A note that the tip history is saved in the TipCollection class so we do not access it here.
 */

public class SaveData extends JSONObject  {

    public static void saveAllRoutes(Context context) {
        saveRoutes(context);
        saveHiddenRoutes(context);
    }

    public static void loadAllRoutes(Context context) {
        loadRoutes(context);
        loadHiddenRoutes(context);
    }

    private static void loadHiddenRoutes(Context context) {
    }

    public static void loadRoutes(Context context) {
        CarbonModel model = CarbonModel.getInstance();
        RouteCollection rc = model.getRouteCollection();
        int index = 0;
//        while (index < rc.getListSize()) {
//            rc.removeRoute(index);
//        }

        SharedPreferences prefs = context.getSharedPreferences("RouteCollection", MODE_PRIVATE);
        index = 0;

        while (prefs.getString("Route"+index, "") != "") {
            Log.i("load",index+"");
            Gson routeData = new Gson();
            String jsonRouteData = prefs.getString("Route" + index, null);
            Route route = routeData.fromJson(jsonRouteData, Route.class);
            rc.editRoute(route, index);
            index++;
        }
    }

    public static void saveRoutes(Context context) {
        CarbonModel model = CarbonModel.getInstance();
        RouteCollection rc = model.getRouteCollection();
        SharedPreferences prefs = context.getSharedPreferences("RouteCollection", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        //editor.clear();
        for (int i = 0; i < rc.getListSize(); i++) {
            Gson routeData = new Gson();
            String jsonRouteData = routeData.toJson(rc.getRouteAtIndex(i));
            editor.putString("Route"+i, jsonRouteData);
            Log.i("added", ""+i);
        }
        editor.commit();
        editor.apply();
    }

    public static void saveHiddenRoutes(Context context) {
        CarbonModel model = CarbonModel.getInstance();
        RouteCollection rc = model.getRouteCollection();

        SharedPreferences prefs = context.getSharedPreferences("RouteCollection", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        for (int i = 0; i < rc.getHiddenListSize(); i++) {
            Gson routeData = new Gson();
            String jsonRouteData = routeData.toJson(rc.getHiddenRouteAtIndex(i));
            editor.putString("HiddenRoute"+i, jsonRouteData);
        }
        editor.apply();
    }

    //todo: save utilities, journey, cars, tip history
}
