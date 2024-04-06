package org.example.stress.validator;

import org.example.model.Figure;
import org.example.model.PlacedFigure;

import java.util.*;

public class OverlayTetrisValidator implements TetrisValidator {
    private boolean hasOverlay(PlacedFigure f1, PlacedFigure f2) {
        Set<List<Integer>> points1 = new HashSet<>(f1.getFigure().getCoordsAsNonPrimitive());
        return f2.getFigure().getCoordsAsNonPrimitive().stream().anyMatch(points1::contains);
    }
    @Override
    public Optional<String> test(List<PlacedFigure> placedFigures, Map<String, Figure> figures) {
        for (int i = 0; i < placedFigures.size(); ++i) {
            for (int j = 0; j < i; ++j) {
                if (hasOverlay(placedFigures.get(i), placedFigures.get(j))) {
                    return Optional.of(String.format("Figures #%s and #%s has overlay", i, j));
                }
            }
        }
        return Optional.empty();
    }
}
