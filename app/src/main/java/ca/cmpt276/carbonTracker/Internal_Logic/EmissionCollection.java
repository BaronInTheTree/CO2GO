package ca.cmpt276.carbonTracker.Internal_Logic;

import com.example.sasha.carbontracker.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by song on 2017-03-27.
 */

public class EmissionCollection {

    private HashMap<String, Float> emissionTransportationModes;
    private Float[] emissions;
    private String[] transportationModes;

    public EmissionCollection(List<DayData> dayDateList){
        emissionTransportationModes = new HashMap<String, Float>();
        updateEmissionTransportMode(dayDateList);
        emissions = new Float[emissionTransportationModes.size()];
        transportationModes = new String[emissionTransportationModes.size()];
        setEmissions();
        setEmissionTransportModes();
    }

    public void setEmissions(){
        int index=0;
        for(Float num:emissionTransportationModes.values()){
            emissions[index] = num;
            index++;
        }
    }

    public void setEmissionTransportModes(){
        Set<String> set = emissionTransportationModes.keySet();
        Iterator it =set.iterator();
        int index =0;
        while (it.hasNext()){
            transportationModes[index]=(String)it.next();
            index++;
        }
    }

    public Float[] getEmissions(){
        return emissions;
    }

    public String[] getTransportationModes(){
        return transportationModes;
    }


    public void updateEmissionTransportMode(List<DayData> dayDateList) {
        for (DayData data: dayDateList){
            float emission=0;
            for (Journey journey:data.getJourneyList()){
                if (journey.getType()== Journey.Type.CAR){
                    String carName = journey.getTransportation().getNickname();
                    if (!emissionTransportationModes.containsKey(carName)) {
                        emissionTransportationModes.put(carName,journey.getEmissions());
                    }
                    else {
                        emission = emissionTransportationModes.get(carName);
                        emission += journey.getEmissions();
                        emissionTransportationModes.put(carName,emission);
                    }
                }
                else if (journey.getType()== Journey.Type.BUS){
                    if (!emissionTransportationModes.containsKey("Bus")){
                        emissionTransportationModes.put("Bus", journey.getEmissions())
                    }
                    else{
                        emission = emissionTransportationModes.get("Bus");
                        emission += journey.getEmissions();
                        emissionTransportationModes.put("Bus",emission);
                    }
                }
                else if (journey.getType()== Journey.Type.SKYTRAIN) {
                    if (!emissionTransportationModes.containsKey("Skytrain")){
                        emissionTransportationModes.put("Skytrain",journey.getEmissions());
                    }
                    else{
                        emission = emissionTransportationModes.get("Skytrain");
                        emission += journey.getEmissions();
                        emissionTransportationModes.put("Skytrain",emission);
                    }
                }
                else {
                    if (!emissionTransportationModes.containsKey("WalkingBiking")){
                        emissionTransportationModes.put("WalkingBiking",journey.getEmissions());
                    }
                    else{
                        emission = emissionTransportationModes.get("WalkingBiking");
                        emission += journey.getEmissions();
                        emissionTransportationModes.put("WalkingBiking",emission);
                    }
                }
            }

            for (Utility utility:data.getUtilityList()) {
                if (utility.isElectricity()) {
                    if (!emissionTransportationModes.containsKey("Electricity")) {
                        emissionTransportationModes.put("Electricity", utility.getCO2PerDayPerPerson());
                    } else {
                        emission = emissionTransportationModes.get("Electricity");
                        emission += utility.getCO2PerDayPerPerson();
                        emissionTransportationModes.put("Electricity", emission);
                    }
                } else {
                    if (!emissionTransportationModes.containsKey("Gas")) {
                        emissionTransportationModes.put("Gas", utility.getCO2PerDayPerPerson());
                    } else {
                        emission = emissionTransportationModes.get("Gas");
                        emission += utility.getCO2PerDayPerPerson();
                        emissionTransportationModes.put("Gas", emission);
                    }
                }
            }
        }
    }


    public static HashMap<String, Float> updateEmissionRouteMode(List<DayData> dayDateList) {
        HashMap<String, Float> emissionRouteModes = new HashMap<String, Float>();
        // Todo: function to be implemented.

        return emissionRouteModes;
    }


}
