package org.example.stress;

import org.example.model.Figure;
import org.example.stress.generator.figure.DummyFigureGenerator;
import org.example.stress.generator.figure.FigureGenerator;
import org.example.stress.generator.figure.SnakeFigureGenerator;
import org.example.visual.figure.ConsoleFigureVisualizer;
import org.example.visual.figure.FigureVisualizer;

public class StressTest {
    public static void main(String[] args) throws InterruptedException {
        FigureGenerator figureGenerator = new SnakeFigureGenerator(10, false);
        FigureVisualizer visualizer = new ConsoleFigureVisualizer();
        while (true) {
            Figure figure = figureGenerator.generateFigure(4, 4);
            visualizer.visualize(figure);
            System.out.println("----------------------");
            Thread.sleep(3000);
        }
    }
}
