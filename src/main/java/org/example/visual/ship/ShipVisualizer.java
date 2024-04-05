package org.example.visual.ship;

import org.example.model.PlacedFigure;

import java.util.List;

public interface ShipVisualizer {
    void visualize(List<PlacedFigure> placedFigures, int baggageWidth, int baggageHeight);
}
