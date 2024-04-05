package org.example.stress;

import org.example.model.Figure;
import org.example.stress.generator.DummyFigureGenerator;
import org.example.stress.generator.FigureGenerator;
import org.example.visual.figure.ConsoleFigureVisualizer;
import org.example.visual.figure.FigureVisualizer;

public class StressTest {
    public static void main(String[] args) throws InterruptedException {
        FigureGenerator figureGenerator = new DummyFigureGenerator();
        FigureVisualizer visualizer = new ConsoleFigureVisualizer();
        while (true) {
            Figure figure = figureGenerator.generateFigure(4, 4);
            visualizer.visualize(figure);
            System.out.println("----------------------");
            Thread.sleep(3000);
        }
    }
}
