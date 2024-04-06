package org.example.model.graph;

import java.util.HashMap;
import java.util.Map;

public class Graph {
    private final Map<String, Map<String, Integer>> g;

    public Graph() {
        g = new HashMap<>();
    }

    public void addEdge(String v, String u, int w) {
        g.putIfAbsent(v, new HashMap<>());
        g.get(v).put(u, w);
    }

    public int getVertexesCount() {
        return g.size();
    }

    public Map<String, Integer> getEdgesFrom(String v) {
        return g.getOrDefault(v, new HashMap<>());
    }

    public Map<String, Map<String, Integer>> getAllEdges() {
        return g;
    }
}
