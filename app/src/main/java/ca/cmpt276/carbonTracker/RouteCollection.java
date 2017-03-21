package ca.cmpt276.carbonTracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.example.sasha.carbontracker.R;

/**
 * RouteCollection class
 */
public class RouteCollection {

    private List<Route> routeCollection;
    private List<Route> hiddenRouteCollection;

    public RouteCollection() {
        routeCollection = new ArrayList<>();
        hiddenRouteCollection = new ArrayList<>();
    }

    public void addRoute(Route route) {
        routeCollection.add(route);
    }

    public void addHiddenRoute(Route route) {
        hiddenRouteCollection.add(route);
    }

    // See CarCollection class for details
    public void hideRoute(Route route) {
        for (Route r : routeCollection) {
            if ((r.getName().equals(route.getName())) && (r.getCityDistanceKM() == route.getCityDistanceKM()) &&
                    (r.getHighwayDistanceKM() == route.getHighwayDistanceKM())) {
                r.setHidden(true);
                hiddenRouteCollection.add(r);
            }
        }
    }

    public void removeRoute(int index) {
        routeCollection.remove(index);
    }

    public void removeHiddenRoute(int index) {
        hiddenRouteCollection.remove(index);
    }

    public void editRoute(Route route, int index) {
        routeCollection.remove(index);
        routeCollection.add(index, route);
    }

    public ArrayAdapter<Route> getArrayAdapter(Context context) {
        RouteCollectionAdapter adapter = new RouteCollectionAdapter(context);
        return adapter;
    }

    private class RouteCollectionAdapter extends ArrayAdapter<Route> {
        RouteCollectionAdapter(Context context) {
            super(context, R.layout.route_list, routeCollection);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View routeView = convertView; // Check if we have a view
            if (routeView == null) {
                routeView = LayoutInflater.from(getContext()).inflate(R.layout.route_list, parent, false);
            }

            Route current = routeCollection.get(position);
            TextView routeText = (TextView) routeView.findViewById(R.id.routeListViewText);
            routeText.setText("Route: " + current.getName() + "\n(Total Distance: " +
                    current.getTotalDistanceKM() + " km)");

            return routeView;
        }
    }

    public int getListSize() {
        return routeCollection.size();
    }

    public int getHiddenListSize() {
        return hiddenRouteCollection.size();
    }

    public Route getLatestRoute() {
        if (getListSize() > 0) return routeCollection.get(routeCollection.size() - 1);
        else return null;
    }

    public Route getRouteAtIndex(int i) {
        return routeCollection.get(i);
    }

    public Route getHiddenRouteAtIndex(int i) {
        return hiddenRouteCollection.get(i);
    }

}