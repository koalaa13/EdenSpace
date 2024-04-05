package org.example.model.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph {
    private final Map<String, List<Edge>> g;

    public Graph() {
        g = new HashMap<>();
    }

    public void addEdge(String v, String u, int w) {
        g.putIfAbsent(v, new ArrayList<>());
        g.get(v).add(new Edge(u, w));
    }

    public List<Edge> getEdgesFrom(String v) {
        return g.getOrDefault(v, new ArrayList<>());
    }

}
