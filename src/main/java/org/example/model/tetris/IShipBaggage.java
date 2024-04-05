package org.example.model.tetris;

import org.example.model.Figure;
import org.example.model.PlacedFigure;

import java.util.List;

public interface IShipBaggage {
    // Возвращает расположение мусора внутри корабля
    List<PlacedFigure> getLoad();

    void setLoad(List<PlacedFigure> newLoad);

    int getCapacityX();

    int getCapacityY();

    // Полностью очищает мусор внутри корабля
    void clean();
}
