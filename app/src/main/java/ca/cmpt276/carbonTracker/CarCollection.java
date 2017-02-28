package ca.cmpt276.carbonTracker;

import java.util.ArrayList;

/**
 * Created by song on 2017-02-27.
 */

public class CarCollection {
    private ArrayList<Car> carCollection;
    private ArrayList<Car> hiddenCarCollections;

    public CarCollection(){
        carCollection = new ArrayList<>();
        hiddenCarCollections = new ArrayList<>();
    }

    public void addCar(Car car){
        carCollection.add(car);
    }

    // Instead of removing the car from the list,
    // we just set it to hidden so the GUI won't display it as an option
    // (per Brian's advice on the Project page)
    public void hideCar(Car car){
        for (Car c : carCollection){
            if (c.getNickname().equals(car.getNickname())){
                c.setHidden(true);
                hiddenCarCollections.add(c);
                carCollection.remove(c);
            }
        }
    }

    public void editCar(Car car, int index){
        carCollection.remove(index);
        carCollection.add(index, car);
    }
}
