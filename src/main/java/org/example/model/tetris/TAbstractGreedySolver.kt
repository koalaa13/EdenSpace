package org.example.model.tetris

import org.example.model.Figure
import org.example.model.PlacedFigure

class TAbstractGreedySolver(val priorityFunc: (Int, Int, Int, Int) -> Int) : ISolver {
    private fun getPriority(pf: PlacedFigure): Int {
        val minX = pf.figure.coords.minOf { a -> a[0] }
        val maxX = pf.figure.coords.maxOf { a -> a[0] }
        val minY = pf.figure.coords.minOf { a -> a[1] }
        val maxY = pf.figure.coords.maxOf { a -> a[1] }
        return priorityFunc(minX, maxX, minY, maxY)
    }

    private fun findTransformation(currentBaggage: IShipBaggage, pf: PlacedFigure, grid: Grid): PlacedFigure? {
        for (i in 0..<currentBaggage.capacityX) {
            for (j in 0..<currentBaggage.capacityY) {
                var candidateFigure = pf.figure
                for (k in 0..3) {
                    var satisfy = true
                    for (coord in candidateFigure.coords) {
                        if (!grid.isFree(i + coord[0], j + coord[1])) {
                            satisfy = false
                            break
                        }
                    }
                    if (satisfy) {
                        return PlacedFigure(candidateFigure, i, j, pf.name)
                    }
                    candidateFigure = candidateFigure.rotated()
                }
            }
        }
        return null
    }

    override fun findOptimalLoad(
        currentBaggage: IShipBaggage,
        garbage: MutableMap<String, Figure>
    ): MutableList<PlacedFigure> {
        val allGarbage = garbage.map { (k, v) -> PlacedFigure(v, 0, 0, k) } + currentBaggage.load
        val sortedGarbage = allGarbage.sortedBy { pf -> getPriority(pf) }
        val grid = Grid(currentBaggage.capacityX, currentBaggage.capacityY)
        val result = ArrayList<PlacedFigure>()
        sortedGarbage.forEach { pf ->
            val figure = findTransformation(currentBaggage, pf, grid)
            if (figure != null) {
                for (coord in figure.coords) {
                    grid.setCell(coord.first, coord.second, pf.name)
                }
                result.add(figure)
            }
        }
        return result
    }
}