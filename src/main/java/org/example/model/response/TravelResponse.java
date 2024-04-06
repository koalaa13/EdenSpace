package org.example.model.response;

import org.example.model.Figure;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TravelResponse {
    private Map<String, Figure> planetGarbage;

    private Map<String, Figure> shipGarbage;

    public Map<String, Figure> getPlanetGarbage() {
        return planetGarbage;
    }

    public void setPlanetGarbage(Map<String, List<List<Integer>>> planetGarbage) {
        this.planetGarbage = new HashMap<>();
        for (var gi : planetGarbage.entrySet()) {
            this.planetGarbage.put(gi.getKey(), new Figure(gi.getValue()));
        }
    }

    public Map<String, Figure> getShipGarbage() {
        return shipGarbage;
    }

    public void setShipGarbage(Map<String, List<List<Integer>>> shipGarbage) {
        this.shipGarbage = new HashMap<>();
        for (var gi : shipGarbage.entrySet()) {
            this.shipGarbage.put(gi.getKey(), new Figure(gi.getValue()));
        }
    }
}
