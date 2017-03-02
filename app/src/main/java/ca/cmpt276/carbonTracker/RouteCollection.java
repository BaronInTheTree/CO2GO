package ca.cmpt276.carbonTracker;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by song on 2017-02-27.
 */

public class RouteCollection {

    private List<Route> routeCollection;
    private List<Route> hiddenRouteCollection;

    public RouteCollection(){
        routeCollection = new ArrayList<>();
        hiddenRouteCollection = new ArrayList<>();
    }

    public void addRoute(Route route){
        routeCollection.add(route);
    }

    // See CarCollection class for details
    public void hideRoute(Route route){
        for (Route r : routeCollection){
            if (r.getName().equals(route.getName())){
                r.setHidden(true);
                hiddenRouteCollection.add(r);
                routeCollection.remove(r);
            }
        }
    }

    public void editRoute(Route route, int index){
        routeCollection.remove(index);
        routeCollection.add(index, route);
    }


}
