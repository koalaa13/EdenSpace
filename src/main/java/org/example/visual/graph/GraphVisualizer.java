package org.example.visual.graph;

import org.example.model.graph.Graph;

import java.util.List;

public interface GraphVisualizer {
    void visualize(Graph graph, String current, List<String> path);
}
