package org.example.model.graph;

import org.example.model.Figure;
import org.example.model.tetris.IPlanet;
import org.example.model.tetris.IShipBaggage;

import java.util.List;

// Хранит IPlanet объекты, чтобы решать куда лететь
public interface INavigator {
    // Возвращает ход перемещения, который сейчас надо сделать с заданным имеющимся багажом
    List<String> getMove(IShipBaggage baggage);

    // Обновляет информацию о том какой мусор лежит на планете и возвращает ее
    IPlanet setPlanetGarbage(String planet, List<Figure> garbage);
}
