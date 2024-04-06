package org.example.stress.validator;

import org.example.model.Figure;
import org.example.model.PlacedFigure;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ContainsSuchFigureValidator implements TetrisValidator {
    private boolean figureEqualsWithoutRotate(Figure f1, Figure f2) {
        return f1.getCoordsAsNonPrimitive().equals(f2.getCoordsAsNonPrimitive());
    }

    private boolean figureEqualsWithRotate(Figure f1, Figure f2) {
        boolean eq = false;
        for (int i = 0; i < 4; ++i) {
            f1.normalize();
            f2.normalize();
            if (figureEqualsWithoutRotate(f1, f2)) {
                eq = true;
            }
            f2 = f2.rotated();
            f2.normalize();
        }
        return eq;
    }

    @Override
    public Optional<String> test(List<PlacedFigure> placedFigures, Map<String, Figure> figures) {
        for (PlacedFigure placedFigure : placedFigures) {
            if (!figures.containsKey(placedFigure.getName())) {
                return Optional.of(String.format("No such figure with name %s in garbage", placedFigure.getName()));
            }
            if (!figureEqualsWithRotate(
                    new Figure(placedFigure.getFigure()),
                    new Figure(figures.get(placedFigure.getName())))
            ) {
                return Optional.of(String.format("Figures with id %s are not equals in PlacedFigures and given Figures", placedFigure.getName()));
            }
        }
        return Optional.empty();
    }
}
