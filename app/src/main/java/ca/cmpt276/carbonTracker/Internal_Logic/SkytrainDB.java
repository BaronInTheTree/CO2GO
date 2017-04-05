package ca.cmpt276.carbonTracker.Internal_Logic;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kyle on 4/4/2017.
 */

public class SkytrainDB {

    private List<Double> expoLineDistances;
    private List<Double> milleniumLineDistances;
    private List<Double> canadaLineDistances;
    private List<String> expoLineStations;
    private List<String> milleniumLineStations;
    private List<String> canadaLineStations;
    private final double KM_PER_MINUTE = 0.75; // Skytrain average velocity

    public SkytrainDB() {
        expoLineDistances = new ArrayList<>();
        milleniumLineDistances = new ArrayList<>();
        canadaLineDistances = new ArrayList<>();
        expoLineStations = new ArrayList<>();
        milleniumLineStations = new ArrayList<>();
        canadaLineStations = new ArrayList<>();
        setupExpoLine();
        setupMilleniumLine();
        setupCanadaLine();
    }

    private void setupExpoLine() {
        expoLineDistances.add(2 * KM_PER_MINUTE);
        expoLineDistances.add(1 * KM_PER_MINUTE);
        expoLineDistances.add(1 * KM_PER_MINUTE);
        expoLineDistances.add(2 * KM_PER_MINUTE);
        expoLineDistances.add(3 * KM_PER_MINUTE);
        expoLineDistances.add(3 * KM_PER_MINUTE);
        expoLineDistances.add(1 * KM_PER_MINUTE);
        expoLineDistances.add(2 * KM_PER_MINUTE);
        expoLineDistances.add(2 * KM_PER_MINUTE);
        expoLineDistances.add(1 * KM_PER_MINUTE);
        expoLineDistances.add(2 * KM_PER_MINUTE);
        expoLineDistances.add(3 * KM_PER_MINUTE);
        expoLineDistances.add(2 * KM_PER_MINUTE);
        expoLineDistances.add(4 * KM_PER_MINUTE);
        expoLineDistances.add(1 * KM_PER_MINUTE);
        expoLineDistances.add(3 * KM_PER_MINUTE);
        expoLineDistances.add(3 * KM_PER_MINUTE);
        expoLineDistances.add(1 * KM_PER_MINUTE);
        expoLineDistances.add(2 * KM_PER_MINUTE);

        expoLineStations.add("Waterfront");
        expoLineStations.add("Burrard");
        expoLineStations.add("Stadium - Chinatown");
        expoLineStations.add("Main");
        expoLineStations.add("Nanaimo");
        expoLineStations.add("29th Avenue");
        expoLineStations.add("Joyce");
        expoLineStations.add("Patterson");
        expoLineStations.add("Metrotown");
        expoLineStations.add("Royal Oak");
        expoLineStations.add("Edmonds");
        expoLineStations.add("22nd Street");
        expoLineStations.add("New Westminster");
        expoLineStations.add("Columbia");
        expoLineStations.add("Scott Road");
        expoLineStations.add("Gateway");
        expoLineStations.add("Surrey Central");
        expoLineStations.add("King George");
    }

    private void setupMilleniumLine() {
        milleniumLineDistances.add(2 * KM_PER_MINUTE);
        milleniumLineDistances.add(3 * KM_PER_MINUTE);
        milleniumLineDistances.add(2 * KM_PER_MINUTE);
        milleniumLineDistances.add(2 * KM_PER_MINUTE);
        milleniumLineDistances.add(2 * KM_PER_MINUTE);
        milleniumLineDistances.add(1 * KM_PER_MINUTE);
        milleniumLineDistances.add(3 * KM_PER_MINUTE);
        milleniumLineDistances.add(1 * KM_PER_MINUTE);
        milleniumLineDistances.add(3 * KM_PER_MINUTE);
        milleniumLineDistances.add(1 * KM_PER_MINUTE);

        milleniumLineStations.add("Lougheed Town Centre");
        milleniumLineStations.add("Production Way");
        milleniumLineStations.add("Lake City");
        milleniumLineStations.add("Sperling");
        milleniumLineStations.add("Holdom");
        milleniumLineStations.add("Brentwood Town Centre");
        milleniumLineStations.add("Gilmore");
        milleniumLineStations.add("Rupert");
        milleniumLineStations.add("Renfrew");
        milleniumLineStations.add("Commercial - Broadway");
        milleniumLineStations.add("VCC/Clark");
    }

    private void setupCanadaLine() {
        canadaLineDistances.add(2 * KM_PER_MINUTE);
        canadaLineDistances.add(2 * KM_PER_MINUTE);
        canadaLineDistances.add(2 * KM_PER_MINUTE);
        canadaLineDistances.add(1 * KM_PER_MINUTE);
        canadaLineDistances.add(2 * KM_PER_MINUTE);
        canadaLineDistances.add(3 * KM_PER_MINUTE);
        canadaLineDistances.add(2 * KM_PER_MINUTE);
        canadaLineDistances.add(3 * KM_PER_MINUTE);
        canadaLineDistances.add(2 * KM_PER_MINUTE);
        canadaLineDistances.add(2 * KM_PER_MINUTE);
        canadaLineDistances.add(2 * KM_PER_MINUTE);
        canadaLineDistances.add(2 * KM_PER_MINUTE);
        canadaLineDistances.add(9 * KM_PER_MINUTE);
        canadaLineDistances.add(2 * KM_PER_MINUTE);
        canadaLineDistances.add(2 * KM_PER_MINUTE);

        canadaLineStations.add("Waterfront");
        canadaLineStations.add("Vancouver City Centre");
        canadaLineStations.add("Yaletown-Roundhouse");
        canadaLineStations.add("Olympic Village");
        canadaLineStations.add("Broadway - City Hall");
        canadaLineStations.add("King Edward");
        canadaLineStations.add("Oakridge - 41st");
        canadaLineStations.add("Langara - 49th");
        canadaLineStations.add("Marine Drive");
        canadaLineStations.add("Bridgeport");
        canadaLineStations.add("Aberdeen");
        canadaLineStations.add("Landsdowne");
        canadaLineStations.add("Richmond - Brighouse");
        canadaLineStations.add("VCC/Templeton");
        canadaLineStations.add("Sea Island");
        canadaLineStations.add("YVR - Airport");
    }

    public List<Double> getExpoLineDistances() {
        return expoLineDistances;
    }

    public List<Double> getMilleniumLineDistances() {
        return milleniumLineDistances;
    }

    public List<Double> getCanadaLineDistances() {
        return canadaLineDistances;
    }

    public List<String> getExpoLineStations() {
        return expoLineStations;
    }

    public List<String> getMilleniumLineStations() {
        return milleniumLineStations;
    }

    public List<String> getCanadaLineStations() {
        return canadaLineStations;
    }

    public int getDistanceStations_KM(int startIndex, int endIndex, ArrayList<Double> lineDistances) {
        int distanceKM = 0;
        if (endIndex - startIndex > 0) {
            for (int i = startIndex; i < endIndex; i++) {
                distanceKM += lineDistances.get(i);
            }
        }
        else if (endIndex - startIndex < 0) {
            for (int i = endIndex - 1; i >= startIndex; i--) {
                distanceKM += lineDistances.get(i);
            }
        }
        return distanceKM;
    }


}
