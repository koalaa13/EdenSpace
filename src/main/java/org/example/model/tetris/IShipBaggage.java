package org.example.model.tetris;

import org.example.model.Figure;
import org.example.model.PlacedFigure;

import java.util.List;

public interface IShipBaggage {
    // Возвращает расположение мусора внутри корабля
    List<PlacedFigure> getLoad();

    // Полностью очищает мусор внутри корабля
    void clean();
}
