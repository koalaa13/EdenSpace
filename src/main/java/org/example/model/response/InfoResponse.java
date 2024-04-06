package org.example.model.response;

import org.example.model.Ship;

import java.util.List;

public class InfoResponse {
    private List<List<Object>> universe;

    private Ship ship;

    public List<List<Object>> getUniverse() {
        return universe;
    }

    public void setUniverse(List<List<Object>> universe) {
        this.universe = universe;
    }

    public Ship getShip() {
        return ship;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }
}
