package org.example.service;

import org.example.model.Figure;
import org.example.model.PlacedFigure;
import org.example.model.graph.Graph;
import org.example.model.response.InfoResponse;
import org.example.model.tetris.IShipBaggage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    private void dfs(String v, Set<String> used, Graph graph, List<String> planetsOrder) {
        used.add(v);
        planetsOrder.add(v);
        for (var to : graph.getEdgesFrom(v).keySet()) {
            if (!used.contains(to)) {
                used.add(to);
                dfs(to, used, graph, planetsOrder);
            }
        }
    }

    public List<String> getPlanetsOrder(Graph graph) {
        int n = graph.getVertexesCount();
        Set<String> used = new HashSet<>();
        String s = "Earth";
        List<String> order = new ArrayList<>();
        dfs(s, used, graph, order);
        return order;
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

    public Double calcFillPercentage(IShipBaggage shipBaggage) {
        return calcFillPercentage(shipBaggage.getLoad(), shipBaggage.getCapacityX(), shipBaggage.getCapacityY());
    }
}
