package org.example.model;

import java.util.ArrayList;
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

    public Figure shift(int deltaX, int deltaY) {
        List<List<Integer>> newCoords = new ArrayList<>();
        for (var coord : coords) {
            List<Integer> inner = new ArrayList<>();
            inner.add(coord[0] + deltaX);
            inner.add(coord[1] + deltaY);
            newCoords.add(inner);
        }
        return new Figure(newCoords);
    }

    public int[][] getCoords() {
        return coords;
    }

    public Figure rotate() {
        List<List<Integer>> newCoords = new ArrayList<>();
        for (var coord : coords) {
            newCoords.add(List.of(-coord[1], coord[0]));
        }
        return new Figure(newCoords);
    }
}
