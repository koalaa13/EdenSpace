package org.example.api;

import org.example.model.PlacedFigure;
import org.example.model.PlanetInfo;
import org.example.model.graph.Graph;
import org.example.model.tetris.IShipBaggage;

import java.util.List;

public interface IJson {
    GameInfo getGameInfo();

    PlanetInfo move(List<String> trajectory);

    void load(List<PlacedFigure> newGarbage);
}

class GameInfo {
    Graph graph;
    IShipBaggage baggage;
}
