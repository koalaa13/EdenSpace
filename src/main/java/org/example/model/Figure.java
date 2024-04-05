package org.example.model;

import java.util.ArrayList;
import java.util.List;

public class Figure {
    private final int[][] coords;

    public Figure(String name, int[][] coords) {
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

    public List<List<Integer>> getCoordsAsNonPrimitive() {
        List<List<Integer>> arr = new ArrayList<>(this.coords.length);
        for (int[] p : this.coords) {
            arr.add(List.of(p[0], p[1]));
        }
        return arr;
    }

    public int[][] getCoords() {
        return coords;
    }
}
