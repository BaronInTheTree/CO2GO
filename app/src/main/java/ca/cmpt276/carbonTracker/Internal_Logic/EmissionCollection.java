package ca.cmpt276.carbonTracker.Internal_Logic;

import com.example.sasha.carbontracker.R;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by song on 2017-03-27.
 */

public class EmissionCollection {

    private final String[] allModes = {"Bus","Skytrain","WalkBike","Electricity","Gas"};

    private HashMap<String, Float> emissionTransportationModes;
    private Float[] emissionsTransport;
    private String[] transportationModes;

    private HashMap<String, Float> emissionRouteModes;
    private Float[] emissionsRoute;
    private String[] routeModes;

    public EmissionCollection(List<DayData> dayDateList){
        emissionTransportationModes = new HashMap<String, Float>();
        updateEmissionTransportMode(dayDateList);
        emissionsTransport = new Float[emissionTransportationModes.size()];
        transportationModes = new String[emissionTransportationModes.size()];

        emissionRouteModes = new HashMap<String, Float>();
        updateEmissionRouteMode(dayDateList);
        emissionsRoute = new Float[emissionRouteModes.size()];
        routeModes = new String[emissionRouteModes.size()];

        setEmissions();
        setEmissionModes();
    }

    public void setEmissions(){
        // set emissionsTransport array
        int index=0;
        for(Float num:emissionTransportationModes.values()){
            emissionsTransport[index] = num;
            index++;
        }

        // set emissionsRoute array
        index =0;
        for (Float num:emissionRouteModes.values()){
            emissionsRoute[index] = num;
            index++;
        }
    }

    public void setEmissionModes(){
        // set transportationModes array
        int index =0;
        Set<String> set = emissionTransportationModes.keySet();
        Iterator it =set.iterator();
        while (it.hasNext()){
            transportationModes[index]=(String)it.next();
            index++;
        }

        // set routeModes array
        index =0;
        set = emissionRouteModes.keySet();
        it =set.iterator();
        while (it.hasNext()){
            routeModes[index]=(String)it.next();
            index++;
        }
    }

    public Float[] getEmissionsTransport(){
        return emissionsTransport;
    }

    public String[] getTransportationModes(){
        return transportationModes;
    }

    public Float[] getEmissionsRoute(){ return emissionsRoute; }

    public String[] getRouteModes(){ return routeModes; }

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
                    if (!emissionTransportationModes.containsKey(allModes[0])){
                        emissionTransportationModes.put(allModes[0], journey.getEmissions());
                    }
                    else{
                        emission = emissionTransportationModes.get(allModes[0]);
                        emission += journey.getEmissions();
                        emissionTransportationModes.put(allModes[0],emission);
                    }
                }
                else if (journey.getType()== Journey.Type.SKYTRAIN) {
                    if (!emissionTransportationModes.containsKey(allModes[1])){
                        emissionTransportationModes.put(allModes[1],journey.getEmissions());
                    }
                    else{
                        emission = emissionTransportationModes.get(allModes[1]);
                        emission += journey.getEmissions();
                        emissionTransportationModes.put(allModes[1],emission);
                    }
                }
                else {
                    if (!emissionTransportationModes.containsKey(allModes[2])){
                        emissionTransportationModes.put(allModes[2],journey.getEmissions());
                    }
                    else{
                        emission = emissionTransportationModes.get(allModes[2]);
                        emission += journey.getEmissions();
                        emissionTransportationModes.put(allModes[2],emission);
                    }
                }
            }

            emission =0;
            for (Utility utility:data.getUtilityList()) {
                if (utility.isElectricity()) {
                    if (!emissionTransportationModes.containsKey(allModes[3])) {
                        emissionTransportationModes.put(allModes[3], utility.getCO2PerDayPerPerson());
                    } else {
                        emission = emissionTransportationModes.get(allModes[3]);
                        emission += utility.getCO2PerDayPerPerson();
                        emissionTransportationModes.put(allModes[3], emission);
                    }
                } else {
                    if (!emissionTransportationModes.containsKey(allModes[4])) {
                        emissionTransportationModes.put(allModes[4], utility.getCO2PerDayPerPerson());
                    } else {
                        emission = emissionTransportationModes.get(allModes[4]);
                        emission += utility.getCO2PerDayPerPerson();
                        emissionTransportationModes.put(allModes[4], emission);
                    }
                }
            }
        }
    }


    public void updateEmissionRouteMode(List<DayData> dayDateList) {
        for (DayData data: dayDateList){
            float emission=0;
            for (Journey journey:data.getJourneyList()){
                if (!emissionRouteModes.containsKey(journey.getRoute().getName())){
                    emissionRouteModes.put(journey.getRoute().getName(),journey.getEmissions());
                }
                else {
                    emission = emissionRouteModes.get(journey.getRoute().getName());
                    emission += journey.getEmissions();
                    emissionRouteModes.put(journey.getRoute().getName(),emission);
                }
            }

            emission =0;
            for (Utility utility:data.getUtilityList()) {
                if (utility.isElectricity()) {
                    if (!emissionRouteModes.containsKey(allModes[3])) {
                        emissionRouteModes.put(allModes[3], utility.getCO2PerDayPerPerson());
                    } else {
                        emission = emissionRouteModes.get(allModes[3]);
                        emission += utility.getCO2PerDayPerPerson();
                        emissionRouteModes.put(allModes[3], emission);
                    }
                } else {
                    if (!emissionRouteModes.containsKey(allModes[4])) {
                        emissionRouteModes.put(allModes[4], utility.getCO2PerDayPerPerson());
                    } else {
                        emission = emissionRouteModes.get(allModes[4]);
                        emission += utility.getCO2PerDayPerPerson();
                        emissionRouteModes.put(allModes[4], emission);
                    }
                }
            }
        }
    }
}
