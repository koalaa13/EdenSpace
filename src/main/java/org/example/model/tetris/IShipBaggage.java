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

    // Возвращает площадь (пустого) багажа
    int getArea();

    // Возвращает число пустых клеточек
    int getFreeSpace();

    // Возвращает число пустых клеточек
    int getBusySpace();

    // Возвращает размер прямоугольника, являющегося выпуклой оболочкой мусора внутри
    int getLoadConvexHullArea();

    // Полностью очищает мусор внутри корабля
    void clean();
}
