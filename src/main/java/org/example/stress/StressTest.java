package org.example.stress;

import org.example.model.Figure;
import org.example.model.PlacedFigure;
import org.example.model.tetris.ISolver;
import org.example.model.tetris.TPlanet;
import org.example.model.tetris.TShipBaggage;
import org.example.stress.generator.figure.FigureGenerator;
import org.example.stress.generator.figure.SnakeFigureGenerator;
import org.example.stress.validator.OverlayTetrisValidator;
import org.example.stress.validator.TetrisValidator;
import org.example.visual.figure.ConsoleFigureVisualizer;
import org.example.visual.figure.FigureVisualizer;
import org.example.visual.ship.ConsoleShipVisualizer;
import org.example.visual.ship.ShipVisualizer;

import java.util.*;
import java.util.stream.Collectors;

public class StressTest {
    private static final FigureGenerator FIGURE_GENERATOR = new SnakeFigureGenerator(10, false);
    private static final FigureVisualizer FIGURE_VISUALIZER = new ConsoleFigureVisualizer();

    private static final ShipVisualizer SHIP_VISUALIZER = new ConsoleShipVisualizer();

    private static final List<TetrisValidator> VALIDATORS = List.of(
            new OverlayTetrisValidator()
    );

    private static void testCase() {
        int w = 10;
        int h = 10;
        int figuresCount = 10;
        System.out.println("FIGURES:");
        Map<String, Figure> figureMap = new HashMap<>();
        for (int i = 0; i < figuresCount; ++i) {
            Figure figure = FIGURE_GENERATOR.generateFigure(w, h);
            String name = UUID.randomUUID().toString();
            figureMap.put(name, figure);
            FIGURE_VISUALIZER.visualize(figure);
            System.out.println("----------------------");
        }
        System.out.println("FIGURES COUNT: " + figureMap.size());

        List<ISolver> solvers = getSolvers();
        for (int i = 0; i < solvers.size(); ++i) {
            List<PlacedFigure> placedFigures = testCaseSolver(solvers.get(i), w, h, figureMap);

            String validatorsErrorsAsString = VALIDATORS.stream()
                    .map(v -> v.test(placedFigures, figureMap))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.joining("\n"));
            if (!validatorsErrorsAsString.isEmpty()) {
                System.err.println("SOLVER #" + i);
                System.err.println("VALIDATORS ERROR");
                System.err.println(validatorsErrorsAsString);
                System.err.println("PLACEMENT:");
                SHIP_VISUALIZER.visualize(placedFigures, w, h);
                System.exit(1);
            }


            System.out.println("SOLVER #" + i);
            System.out.println("FIGURES PLACED: " + placedFigures.size());
            System.out.println("PLACEMENT:");
            SHIP_VISUALIZER.visualize(placedFigures, w, h);
            System.out.println("-------------------------");
        }
    }

    private static List<ISolver> getSolvers() {
        return TPlanet.Companion.getSolvers();
    }

    private static List<PlacedFigure> testCaseSolver(ISolver solver, int w, int h, Map<String, Figure> figureMap) {
        return solver.findOptimalLoad(new TShipBaggage(w, h), figureMap);
    }

    public static void main(String[] args) throws InterruptedException {
        for (int t = 0; t < 1; ++t) {
            System.out.println("TESTCASE #" + t);
            testCase();
//            Thread.sleep();
        }
    }
}
