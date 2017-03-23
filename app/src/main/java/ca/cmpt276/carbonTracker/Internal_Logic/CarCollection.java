package ca.cmpt276.carbonTracker.Internal_Logic;

import java.util.ArrayList;
import java.util.List;


/**
 * The CarCollection class is the basis for storing three car collections: a visible (main)
 * car collection, a hidden car collection which stores the cars the user deletes, and also a special
 * UI collection where the basic details are shown in the SelectTransportationActivity so the user
 * can tell easily which car they made if they have multiple.
 *
 * @author Team Teal
 */
public class CarCollection {
    private List<Car> carCollection;
    private List<Car> hiddenCarCollection;
    private List<String> uiCollection;

    public CarCollection() {
        carCollection = new ArrayList<>();
        hiddenCarCollection = new ArrayList<>();
        uiCollection = new ArrayList<>();
    }

    public void addCar(Car car) {
        Car clonedCar = null;
        try {
            clonedCar = (Car) car.clone();
        } catch (CloneNotSupportedException e) {
        }

        carCollection.add(clonedCar);
    }

    public void addHiddenCar(Car car) {
        Car clonedCar = null;
        try {
            clonedCar = (Car) car.clone();
        } catch (CloneNotSupportedException e) {
        }

        hiddenCarCollection.add(clonedCar);
    }

    // Instead of removing the car from the list,
    // we just set it to hidden so the GUI won't display it as an option
    // (per Brian's advice on the Project page)
    public void hideCar(Car car) {
        for (Car c : carCollection) {
            if (c.getNickname().equals(c.getNickname())) {
                c.setHidden(true);
                hiddenCarCollection.add(c);
            }
        }
    }

    public void editCar(Car car, int index) {
        Car clonedCar = null;
        try {
            clonedCar = (Car) car.clone();
        } catch (CloneNotSupportedException e) {
        }
        carCollection.remove(index);
        carCollection.add(index, clonedCar);
    }

    public void removeCar(int index) {
        carCollection.remove(index);
    }
    public void removeHiddenCar(int index) {
        hiddenCarCollection.remove(index);
    }

    public List<String> getUICollection() {
        uiCollection.clear();
        for (Car car : carCollection) {
            uiCollection.add(car.getBasicInfo());
        }
        return uiCollection;
    }

    public void setHiddenCarCollection(List<Car> cars) {
        hiddenCarCollection = cars;
    }

    public int getListSize() {
        return carCollection.size();
    }
    public int getHiddenListSize() {
        return hiddenCarCollection.size();
    }

    public Car getLatestCar() {
        if (getListSize() > 0) return carCollection.get(carCollection.size() - 1);
        else return null;
    }

    public Car getCarAtIndex(int index) {
        return carCollection.get(index);
    }
    public Car getHiddenCarAtIndex(int index) {
        return hiddenCarCollection.get(index);
    }


    public int getIndexOfCar(String nickname) {
        int index = 0;
        for (int i = 0; i < carCollection.size(); i++) {
            if (nickname.equals(carCollection.get(i).getNickname())) {
                index = i;
            }
        }
        return index;
    }
}