package org.example.stress.validator;

import org.example.model.Figure;
import org.example.model.PlacedFigure;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface TetrisValidator {
    /**
     *
     * @param placedFigures фигуры, как мы их расставили
     * @param figures фигуры как они изначально были
     * @return
     */
    Optional<String> test(List<PlacedFigure> placedFigures, Map<String, Figure> figures);
}
