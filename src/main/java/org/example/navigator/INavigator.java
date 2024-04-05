package org.example.navigator;

import org.example.model.Figure;
import org.example.model.tetris.IPlanet;
import org.example.model.tetris.IShipBaggage;

import java.util.List;
import java.util.Map;

// Хранит IPlanet объекты, чтобы решать куда лететь
public interface INavigator {
    // Возвращает ход перемещения, который сейчас надо сделать с заданным имеющимся багажом
    List<String> getMove(String currentPlanet, IShipBaggage baggage);

    // Обновляет информацию о том какой мусор лежит на планете и возвращает ее
    IPlanet setPlanetGarbage(String planet, Map<String, Figure> garbage);
}
