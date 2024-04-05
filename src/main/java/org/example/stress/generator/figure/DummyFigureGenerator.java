package org.example.stress.generator.figure;

import org.example.model.Figure;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DummyFigureGenerator extends FigureGenerator {
    public Figure generateFigure(int boundingBoxMaxWidth, int boundingBoxMaxHeight) {
        int width = random.nextInt(boundingBoxMaxWidth) + 1;
        int height = random.nextInt(boundingBoxMaxHeight) + 1;
        List<List<Integer>> coords = new ArrayList<>();
        boolean filled = false;
        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                if (random.nextBoolean()) {
                    filled = true;
                    coords.add(List.of(j, i));
                }
            }
        }
        // Если сгенерировалась пустая, то добавим точку просто
        if (!filled) {
            coords.add(List.of(0, 0));
        }
        return new Figure(coords);
    }
}
