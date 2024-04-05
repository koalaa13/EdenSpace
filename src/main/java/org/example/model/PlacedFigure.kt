package org.example.model;

data class PlacedFigure(
    val figure: Figure,
    val offsetX: Int,
    val offsetY: Int,
    val name: String
) {
    val coords: List<Pair<Int, Int>> = figure.getCoordsAsNonPrimitive().map { it[0] + offsetX to it[1] + offsetY }
}
