package org.example.model;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PlanetInfo {
    private String name;
    private Map<String, Figure> garbage;

    public PlanetInfo() {
    }

    public PlanetInfo(String name, Map<String, Figure> garbage) {
        this.name = name;
        this.garbage = garbage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Figure> getGarbage() {
        return garbage;
    }

    public List<PlacedFigure> getGarbageAsList() {
        return garbage.entrySet().stream().map(entry -> new PlacedFigure(entry.getValue(), entry.getKey()))
                .collect(Collectors.toList());
    }

    public void setGarbage(Map<String, Figure> garbage) {
        this.garbage = garbage;
    }
}
