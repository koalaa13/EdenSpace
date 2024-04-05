package org.example.model;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.min;

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

    public List<List<Integer>> getCoordsAsNonPrimitive() {
        List<List<Integer>> arr = new ArrayList<>(this.coords.length);
        for (int[] p : this.coords) {
            arr.add(List.of(p[0], p[1]));
        }
        return arr;
    }

    public Figure shifted(int deltaX, int deltaY) {
        List<List<Integer>> newCoords = new ArrayList<>();
        for (var coord : coords) {
            List<Integer> inner = new ArrayList<>();
            inner.add(coord[0] + deltaX);
            inner.add(coord[1] + deltaY);
            newCoords.add(inner);
        }
        return new Figure(newCoords);
    }

    public void normalize() {
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
