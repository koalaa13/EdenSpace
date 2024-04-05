package org.example.model;

import java.util.Map;

public class PlanetInfoDTO {
    private String name;
    private Map<String, Figure> garbage;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Figure> getGarbage() {
        return garbage;
    }

    public void setGarbage(Map<String, Figure> garbage) {
        this.garbage = garbage;
    }
}
