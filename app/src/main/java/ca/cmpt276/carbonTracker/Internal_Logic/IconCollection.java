package ca.cmpt276.carbonTracker.Internal_Logic;

import com.example.sasha.carbontracker.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kyle on 4/7/2017.
 */

public class IconCollection {
    public static final List<Integer> iconIDs = new ArrayList<Integer>() {{
        add(R.mipmap.icon_car_black);
        add(R.mipmap.icon_car_purple);
        add(R.mipmap.icon_car_red_sports);
        add(R.mipmap.icon_car_white);
        add(R.mipmap.icon_car_black_pickup);
        add(R.mipmap.icon_car_orange_pickup);
    }};
    public static List<String> iconNames = new ArrayList<String>() {{
        add("Black Car");
        add("Purple Car");
        add("Red Sports Car");
        add("White Car");
        add("Black Truck");
        add("Orange Truck");
    }};
}
