package org.example.model.graph;

public class Edge {
    private final String to;

    private final int fuelWeight;

    public Edge(String to, int fuelWeight) {
        this.to = to;
        this.fuelWeight = fuelWeight;
    }

    public String getTo() {
        return to;
    }

    public int getFuelWeight() {
        return fuelWeight;
    }
}
