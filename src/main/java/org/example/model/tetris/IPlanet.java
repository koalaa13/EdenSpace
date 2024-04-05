package org.example.model.tetris;

import org.example.model.Figure;
import org.example.model.PlacedFigure;

import java.util.List;

public interface IPlanet {
    float calcScore(IShipBaggage baggage);

    void makeOptimalLoad(IShipBaggage baggage);

    void setGarbage(List<Figure> garbage);

    List<Figure> getGarbage();
}
