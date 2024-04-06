package org.example.service;

import org.example.model.Figure;
import org.example.model.PlacedFigure;
import org.example.model.graph.Graph;
import org.example.model.response.InfoResponse;

import java.util.List;

public class UtilService {
    private static UtilService INSTANCE;

    public static UtilService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UtilService();
        }
        return INSTANCE;
    }

    private UtilService() {
    }
    public Graph buildGraph(InfoResponse universeResponse) {
        Graph graph = new Graph();
        for (List<Object> e : universeResponse.getUniverse()) {
            graph.addEdge((String) e.get(0), (String) e.get(1), (Integer) e.get(2));
        }
        return graph;
    }

    public Double calcFillPercentage(List<PlacedFigure> placedFigures, int baggageWidth, int baggageHeight) {
        int filledCount = placedFigures.stream()
                .map(PlacedFigure::getFigure)
                .map(Figure::getCoords)
                .mapToInt(ps -> ps.length)
                .sum();
        int all = baggageHeight * baggageWidth;
        return (double) filledCount / (double) all * 100.0;
    }
}
