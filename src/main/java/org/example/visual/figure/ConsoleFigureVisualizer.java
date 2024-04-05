package org.example.visual.figure;

import org.example.model.Figure;

public class ConsoleFigureVisualizer implements FigureVisualizer {
    @Override
    public void visualize(Figure figure) {
        int maxX = figure.getCoordsAsNonPrimitive().stream().map(p -> p.get(0)).max(Integer::compareTo).get() + 1;
        int maxY = figure.getCoordsAsNonPrimitive().stream().map(p -> p.get(1)).max(Integer::compareTo).get() + 1;
        boolean[][] print = new boolean[maxY][maxX];
        for (int[] p : figure.getCoords()) {
            print[p[1]][p[0]] = true;
        }
        for (int i = 0; i < maxY; ++i) {
            for (int j = 0; j < maxX; ++j) {
                if (print[i][j]) {
                    System.out.print('*');
                } else {
                    System.out.print('.');
                }
            }
            System.out.println();
        }
    }
}
