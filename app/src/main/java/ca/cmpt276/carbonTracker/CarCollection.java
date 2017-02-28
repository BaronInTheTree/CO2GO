package ca.cmpt276.carbonTracker;

import java.util.ArrayList;

/**
 * Created by song on 2017-02-27.
 */

public class CarCollection {
    private ArrayList<Car> carCollection;

    public CarCollection(){
        carCollection = new ArrayList<>();
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
                c.setIsHidden(true);
            }
        }
    }

    // When editing a car, create new Car from dropdown menu selections and delete old car.
    public void deleteCar(Car car){
        for (Car c : carCollection){
            if (c.getNickname().equals(car.getNickname())){
                carCollection.remove(c);
            }
        }
    }
}
