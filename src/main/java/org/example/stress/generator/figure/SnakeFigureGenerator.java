package org.example.stress.generator.figure;

import org.example.model.Figure;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

// Тактика генерации такая: выбирает изначальную клетку, которая сделать клеткой фигуры, а не пустой, и ходит
// на соседние, чтобы фигура получилась похожая на связную.
public class SnakeFigureGenerator extends FigureGenerator {
    private final int stepsCount;

    private static final List<List<Integer>> commonStepsOffsets = List.of(
            List.of(0, 1),
            List.of(1, 0),
            List.of(0, -1),
            List.of(-1, 0)
    );

    private static final List<List<Integer>> diagStepsOffsets = List.of(
            List.of(1, 1),
            List.of(-1, -1),
            List.of(1, -1),
            List.of(-1, 1)
    );

    private final List<List<Integer>> possibleOffsets;

    /**
     * @param stepsCount Сколько шагов сделает змейка, то есть максимальное количество клеточек, которое занимает фигура
     * @param withDiag   ходит ли змейка по диагонали
     */
    public SnakeFigureGenerator(int stepsCount, boolean withDiag) {
        this.stepsCount = stepsCount;
        this.possibleOffsets = new ArrayList<>();
        possibleOffsets.addAll(commonStepsOffsets);
        if (withDiag) {
            possibleOffsets.addAll(diagStepsOffsets);
        }
    }

    private boolean isInField(int x, int y, int w, int h) {
        return x >= 0 && x < w && y >= 0 && y < h;
    }

    private List<Integer> getNextPoint(int x, int y) {
        int offsetInd = random.nextInt(possibleOffsets.size());
        return List.of(
                x + possibleOffsets.get(offsetInd).get(0),
                y + possibleOffsets.get(offsetInd).get(1)
        );
    }

    @Override
    public Figure generateFigure(int boundingBoxMaxWidth, int boundingBoxMaxHeight) {
        int x = random.nextInt(boundingBoxMaxWidth);
        int y = random.nextInt(boundingBoxMaxHeight);
        List<List<Integer>> coords = new ArrayList<>();
        coords.add(List.of(x, y));
        for (int i = 0; i < stepsCount; ++i) {
            List<Integer> nextPoint = getNextPoint(x, y);
            if (!isInField(nextPoint.get(0), nextPoint.get(1), boundingBoxMaxWidth, boundingBoxMaxHeight)) {
                continue;
            }
            coords.add(nextPoint);
            x = nextPoint.get(0);
            y = nextPoint.get(1);
        }
        return new Figure(coords.stream().distinct().collect(Collectors.toList()));
    }
}
