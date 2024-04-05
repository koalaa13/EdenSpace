package org.example.visual.ship;

import org.example.model.PlacedFigure;

import java.util.Arrays;
import java.util.List;

public class ConsoleShipVisualizer implements ShipVisualizer {
    public static final String ANSI_RESET = "\u001B[0m";

    private static final String[] ANSI_COLORS = new String[]{
            "\u001B[30m",
            "\u001B[31m",
            "\u001B[32m",
            "\u001B[33m",
            "\u001B[34m",
            "\u001B[35m",
            "\u001B[36m",
            "\u001B[37m"
    };

    private static final char EMPTY_FIELD_CHAR = '.';

    @Override
    public void visualize(List<PlacedFigure> placedFigures, int baggageWidth, int baggageHeight) {
        char[][] field = new char[baggageHeight][baggageWidth];
        for (int i = 0; i < baggageHeight; ++i) {
            Arrays.fill(field[i], EMPTY_FIELD_CHAR);
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
                if (field[i][j] == EMPTY_FIELD_CHAR) {
                    System.out.print(field[i][j]);
                } else {
                    int colorInd = (field[i][j] - 'A') % ANSI_COLORS.length;
                    System.out.print(ANSI_COLORS[colorInd] + field[i][j] + ANSI_RESET);
                }
            }
            System.out.println();
        }
    }
}
