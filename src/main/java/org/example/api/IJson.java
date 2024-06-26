package org.example.api;

import org.example.model.PlacedFigure;
import org.example.model.PlanetInfo;

import java.util.List;

public interface IJson {
    // Отравляет GET HTTP запрос и возвращает все, что необходимо из результата.
    // Вызывается только 1 раз за игру!!!
    GameInfo getGameInfo();

    // Получает всю необходимую инфу и отправляет HTTP запрос о перемещении.
    // Возвращает всю необходимую инфу из результата
    MoveInfo move(List<String> trajectory);

    // Получает всю необходимую инфу и отправляет HTTP запрос о загрузке
    void load(List<PlacedFigure> newGarbage);
}

