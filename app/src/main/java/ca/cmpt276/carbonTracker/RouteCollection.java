package ca.cmpt276.carbonTracker;

import java.util.ArrayList;

/**
 * Created by song on 2017-02-27.
 */

public class RouteCollection {

    private ArrayList<Route> routeCollection;

    public RouteCollection(){
        routeCollection = new ArrayList<>();
    }

    public void addRoute(Route route){
        routeCollection.add(route);
    }

    // See CarCollection class for details
    public void hideRoute(Route route){
        for (Route r : routeCollection){
            if (r.getName().equals(route.getName())){
                r.setHidden(true);
            }
        }
    }

    public void deleteRoute(Route route){
        for (Route r : routeCollection){
            if (r.getName().equals(route.getName())){
                routeCollection.remove(r);
            }
        }
    }


}
