package org.example.model.tetris;

import org.example.model.Figure;
import org.example.model.PlacedFigure;

import java.util.List;

public interface IPlanet {
    float calcScore(IShipBaggage baggage);

    // Загружает корабль
    void makeOptimalLoad(IShipBaggage baggage);

    // Обновляет информацию о мусоре на этой планете
    void setGarbage(List<Figure> garbage);

    // Возвращает весь мусор на этой планете
    List<Figure> getGarbage();
}
