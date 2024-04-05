package org.example.model.tetris;

import org.example.model.Figure;
import org.example.model.PlacedFigure;

import java.util.List;

public interface IShipBaggage {
    List<PlacedFigure> getLoad();

    void clean();
}
