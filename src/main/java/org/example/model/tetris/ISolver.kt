package org.example.model.tetris

import org.example.model.Figure
import org.example.model.PlacedFigure

interface ISolver {
    fun findOptimalLoad(currentBaggage: IShipBaggage, garbage: MutableMap<String, Figure>): MutableList<PlacedFigure>
}
