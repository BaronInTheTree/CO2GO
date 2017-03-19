package ca.cmpt276.carbonTracker;

import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;

/**
 * Created by Adriel on 2017-03-18.
 */

public class SaveData extends JSONObject  {

    public static void saveRoutes() {
        CarbonModel model = CarbonModel.getInstance();

        JSONObject routeObject = new JSONObject();
        for (int i = 0; i < model.getRouteCollection().getListSize(); i++) {
            try {
                Route route = model.getRouteCollection().getRouteAtIndex(i);
                routeObject.put("RouteName"+i, route.getName());
                routeObject.put("RouteCityKM"+i, route.getCityDistanceKM());
                routeObject.put("RouteHighwayKM"+i, route.getHighwayDistanceKM());

                File file = new File(String.valueOf(Environment.getExternalStorageDirectory()));
                Writer writer = new BufferedWriter(new FileWriter(file));
                writer.write(routeObject.toString());
                writer.close();
                Log.i("written", "written");
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void saveHiddenRoutes() {
        CarbonModel model = CarbonModel.getInstance();
        JSONObject routeObject = new JSONObject();

        for (int i = 0; i < model.getRouteCollection().getHiddenListSize(); i++) {
            try {
                Route route = model.getRouteCollection().getHiddenRouteAtIndex(i);
                routeObject.put("RouteName"+i, route.getName());
                routeObject.put("RouteCityKM"+i, route.getCityDistanceKM());
                routeObject.put("RouteHighwayKM"+i, route.getHighwayDistanceKM());

                File file = new File(String.valueOf(Environment.getExternalStorageDirectory()));
                Writer writer = new BufferedWriter(new FileWriter(file));
                writer.write(routeObject.toString());
                writer.close();
                Log.i("written", "written");
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void loadAllRoutes() {
        try {
            File file = new File(String.valueOf(Environment.getExternalStorageDirectory()));
            FileInputStream fis = new FileInputStream(file);
            int length = fis.available();
            byte[] stream = new byte[length];
            fis.read(stream);
            fis.close();
            String routeObject = new String (stream);
            Log.i("route", routeObject);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //todo: save utilities, journey, cars, tip history
}
