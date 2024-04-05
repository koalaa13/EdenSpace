package org.example.model.graph;

import org.example.model.Figure;
import org.example.model.PlacedFigure;
import org.example.model.tetris.IPlanet;
import org.example.model.tetris.IShipBaggage;

import java.util.List;

public interface IGraph {
    List<String> getMove(IShipBaggage baggage);

    IPlanet setPlanetGarbage(String planet, List<Figure> garbage);
}
