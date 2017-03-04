package ca.cmpt276.carbonTracker;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kyle on 3/3/2017.
 *
 * Note: Will refactor update methods below before iteration ends
 *
 */

public class CarData {
    private List<Car> carDataList;
    private List<String> makeList;
    private List<String> modelList;
    private List<String> yearList;
    private List<Car> carMatchList;
    private List<String> specsList;

    public CarData(){
        carDataList = new ArrayList<>();
        makeList = new ArrayList<>();
        modelList = new ArrayList<>();
        yearList = new ArrayList<>();
        carMatchList = new ArrayList<>();
        specsList = new ArrayList<>();
        initializeModelList();
        initializeYearList();
        initializeSpecsList();
    }

    public List<Car> getCarDataList() {
        return carDataList;
    }

    public List<String> getMakeList() {
        return makeList;
    }

    public List<String> getModelList() {
        return modelList;
    }

    public List<String> getYearList() {
        return yearList;
    }

    public List<String> getSpecsList() {
        return specsList;
    }

    public List<Car> getCarMatchList() {
        return carMatchList;
    }

    public void initializeMakeList(){
        for (Car car : carDataList){
            int counter = 0;

            for (String make : makeList){
                if (car.getMake().equals(make)){
                    counter++;
                }
            }
            // Avoiding duplicates in list
            if (counter < 1){
                makeList.add(car.getMake());
            }
        }
        for (String make : makeList){
            System.out.println("Make: " + make);
        }
    }

    public void initializeModelList(){
        modelList.add("Select Model");
    }

    public void initializeYearList(){
        yearList.add("Select Year");
    }

    public void initializeSpecsList(){
        specsList.add("Select Specifications");
    }

    public void updateModelList(String make){
        boolean makeFound = false;

        modelList.clear();

        for (int i = 0; i < carDataList.size(); i++){
            if (carDataList.get(i).getMake().equals(make)){
                int counter = 0;
                for (String model : modelList){
                    if (carDataList.get(i).getModel().equals(model)){
                        counter++;
                    }
                }
                // Avoiding duplicates in list
                if (counter < 1){
                    modelList.add(carDataList.get(i).getModel());
                }
                makeFound = true;
            }

            // Else if reached end of sections of selected Make in data, exit loop
            else if (makeFound == true){
                makeFound = false;
                i = carDataList.size();
            }
        }
        for (String model : modelList){
            System.out.println("Model: " + model);
        }
        yearList.clear();
        specsList.clear();
        initializeYearList();
        initializeSpecsList();
    }

    public void updateYearList(String make, String model){
        boolean makeModelFound = false;

        yearList.clear();

        for (int i = 0; i < carDataList.size(); i++){
            if (carDataList.get(i).getMake().equals(make) &&
                    carDataList.get(i).getModel().equals(model)){
                int counter = 0;
                for (String year : yearList){
                    if (("" + carDataList.get(i).getYear()).equals(year)){
                        counter++;
                    }
                }
                // Avoiding duplicates in list
                if (counter < 1){
                    yearList.add("" + carDataList.get(i).getYear());
                }
                makeModelFound = true;
            }

            // Else if reached end of sections of selected Make in data, exit loop
            else if (makeModelFound == true){
                makeModelFound = false;
                i = carDataList.size();
            }
        }
        for (String year : yearList){
            System.out.println("Year: " + year);
        }
        specsList.clear();
        initializeSpecsList();
    }

    public void updateSpecsList(String make, String model, String year){
        boolean makeModelYearFound = false;

        specsList.clear();

        for (int i = 0; i < carDataList.size(); i++){
            if (carDataList.get(i).getMake().equals(make)
                    && carDataList.get(i).getModel().equals(model)
                    && ("" + carDataList.get(i).getYear()).equals(year)){
                Car car = carDataList.get(i);
                specsList.add(car.getTrany() + "\n"
                        + car.getCylinders() + "C\n"
                        + car.getDisplacement() + "L\n"
                        + "Fuel: " + car.getFuelType());
                makeModelYearFound = true;
            }

            // Else if reached end of sections of selected Make in data, exit loop
            else if (makeModelYearFound == true){
                makeModelYearFound = false;
                i = carDataList.size();
            }
        }
        for (String specs : specsList){
            System.out.println("Specs: " + year);
        }
    }

}

