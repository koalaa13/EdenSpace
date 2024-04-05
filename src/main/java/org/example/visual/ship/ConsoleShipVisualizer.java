package org.example.visual.ship;

import org.example.model.PlacedFigure;

import java.util.Arrays;
import java.util.List;

public class ConsoleShipVisualizer implements ShipVisualizer {

    @Override
    public void visualize(List<PlacedFigure> placedFigures, int baggageWidth, int baggageHeight) {
        char[][] field = new char[baggageHeight][baggageWidth];
        for (int i = 0; i < baggageHeight; ++i) {
            Arrays.fill(field[i], '.');
        }
        int figureSym = 0;
        for (PlacedFigure placedFigure : placedFigures) {
            for (int[] p : placedFigure.getFigure().getCoords()) {
                field[p[0]][p[1]] = (char) (figureSym + 'A');
            }
            figureSym = (figureSym + 1) % 26;
        }
        for (int i = 0; i < baggageHeight; ++i) {
            for (int j = 0; j < baggageWidth; ++j) {
                System.out.print(field[i][j]);
            }
            System.out.println();
        }
    }
}
