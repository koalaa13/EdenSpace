package org.example.model.response;

import org.example.model.Figure;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TravelResponse {
    private Map<String, Figure> planetGarbage;

    public Map<String, Figure> getPlanetGarbage() {
        return planetGarbage;
    }

    public void setPlanetGarbage(Map<String, List<List<Integer>>> planetGarbage) {
        this.planetGarbage = new HashMap<>();
        for (var gi : planetGarbage.entrySet()) {
            this.planetGarbage.put(gi.getKey(), new Figure(gi.getValue()));
        }
    }
}
