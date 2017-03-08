package ca.cmpt276.carbonTracker;

import java.util.ArrayList;
import java.util.List;

/**
 * CarCollection class
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
        carCollection.add(car);
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
        carCollection.remove(index);
        carCollection.add(index, car);
    }

    public void removeCar(int index) {
        carCollection.remove(index);
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

    public Car getLatestCar() {
        if (getListSize() > 0) return carCollection.get(carCollection.size() - 1);
        else return null;
    }

    public Car getCarAtIndex(int index) {
        return carCollection.get(index);
    }
}