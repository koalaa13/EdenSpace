package org.example.model.response;

import org.example.model.ShipDTO;

import java.util.List;

public class InfoResponse {
    private List<List<Object>> universe;

    private ShipDTO shipDTO;

    public List<List<Object>> getUniverse() {
        return universe;
    }

    public void setUniverse(List<List<Object>> universe) {
        this.universe = universe;
    }

    public ShipDTO getShip() {
        return shipDTO;
    }

    public void setShip(ShipDTO shipDTO) {
        this.shipDTO = shipDTO;
    }
}
