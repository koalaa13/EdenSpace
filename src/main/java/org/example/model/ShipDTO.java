package org.example.model;

import java.util.Map;

public class ShipDTO {
    private int fuelUsed;
    private int capacityX;
    private int capacityY;

    private Map<String, Figure> garbage;
    private PlanetInfoDTO planet;

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

    public void setGarbage(Map<String, Figure> garbage) {
        this.garbage = garbage;
    }

    public PlanetInfoDTO getPlanet() {
        return planet;
    }

    public void setPlanet(PlanetInfoDTO planet) {
        this.planet = planet;
    }
}
