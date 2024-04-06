package org.example.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Ship {
    private int fuelUsed;
    private int capacityX;
    private int capacityY;

    private Map<String, Figure> garbage;
    private PlanetInfo planet;

    public int getFuelUsed() {
        return fuelUsed;
    }

    public void setFuelUsed(int fuelUsed) {
        this.fuelUsed = fuelUsed;
    }

    public int getCapacityX() {
        return capacityX;
    }

    public void setCapacityX(int capacityX) {
        this.capacityX = capacityX;
    }

    public int getCapacityY() {
        return capacityY;
    }

    public void setCapacityY(int capacityY) {
        this.capacityY = capacityY;
    }

    public Map<String, Figure> getGarbage() {
        return garbage;
    }

    public void setGarbage(Map<String, List<List<Integer>>> garbage) {
        this.garbage = new HashMap<>();
        for (var e : garbage.entrySet()) {
            this.garbage.put(e.getKey(), new Figure(e.getValue()));
        }
    }

    public PlanetInfo getPlanet() {
        return planet;
    }

    public void setPlanet(PlanetInfo planet) {
        this.planet = planet;
    }
}
