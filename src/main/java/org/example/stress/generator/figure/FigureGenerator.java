package org.example.stress.generator.figure;

import org.example.model.Figure;

import java.util.Random;

public abstract class FigureGenerator {
    protected final Random random;

    protected FigureGenerator() {
        random = new Random(
                1337228L
//                System.currentTimeMillis()
        );
    }

    public abstract Figure generateFigure(int boundingBoxMaxWidth, int boundingBoxMaxHeight);
}
