package org.example.model;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.min;

// Фигура без оффсета, то есть примыкает к осям x=0 и y=0
public class Figure {
    private final int[][] coords;

    public Figure(int[][] coords) {
        this.coords = coords;
        normalize();
    }

    public Figure(List<List<Integer>> coords) {
        int n = coords.size();
        this.coords = new int[n][2];
        for (int i = 0; i < n; ++i) {
            this.coords[i][0] = coords.get(i).get(0);
            this.coords[i][1] = coords.get(i).get(1);
        }
        normalize();
    }

    private void normalize() {
        var minX = Integer.MAX_VALUE;
        var minY = Integer.MAX_VALUE;
        for (int i = 0; i < coords.length; i++) {
            minX = min(minX, coords[i][0]);
            minY = min(minY, coords[i][1]);
        }
        for (int i = 0; i < coords.length; i++) {
            coords[i][0] -= minX;
            coords[i][1] -= minY;
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

    public Figure rotated() {
        List<List<Integer>> newCoords = new ArrayList<>();
        for (var coord : coords) {
            newCoords.add(List.of(-coord[1], coord[0]));
        }
        return new Figure(newCoords);
    }
}
