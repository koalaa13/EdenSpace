package org.example.model.tetris;

import org.example.model.Figure;
import org.example.model.PlacedFigure;

import java.util.List;

public interface IPlanet {
    // Возвращает сколько кусков мусора можно уместить в заданный корабль с этой планеты,
    // ничего не меняя в их состояниях
    int getHowManyCanAdd(IShipBaggage baggage);

    // Загружает корабль
    void makeOptimalLoad(IShipBaggage baggage);

    // Обновляет информацию о мусоре на этой планете
    void setGarbage(List<Figure> garbage);

    // Возвращает весь мусор на этой планете
    List<Figure> getGarbage();
}
