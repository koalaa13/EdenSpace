package org.example.stress.generator;

import org.example.model.Figure;

public interface FigureGenerator {
    Figure generateFigure(int boundingBoxMaxWidth, int boundingBoxMaxHeight);
}
