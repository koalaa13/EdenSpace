package org.example.model;

import java.util.List;

public class Figure {
    private final int[][] coords;

    public Figure(int[][] coords) {
        this.coords = coords;
    }

    public Figure(List<List<Integer>> coords) {
        int n = coords.size();
        this.coords = new int[n][2];
        for (int i = 0; i < n; ++i) {
            this.coords[i][0] = coords.get(i).get(0);
            this.coords[i][1] = coords.get(i).get(1);
        }
    }

    public int[][] getCoords() {
        return coords;
    }
}
