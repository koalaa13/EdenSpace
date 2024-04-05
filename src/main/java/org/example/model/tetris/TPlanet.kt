package org.example.model.tetris

import org.example.model.Figure
import org.example.model.PlacedFigure

class TPlanet : IPlanet {
    private var garbage: MutableList<Figure> = ArrayList()

    private fun upgradeLoad(currentBaggage: IShipBaggage): MutableList<PlacedFigure> {
        val allGarbage = garbage + currentBaggage.load.map { p -> p.figure }
        val sortedGarbage = allGarbage.sortedBy { f ->
            val minX = f.coords.minOf { a -> a[0] }
            val maxX = f.coords.maxOf { a -> a[0] }
            val minY = f.coords.minOf { a -> a[1] }
            val maxY = f.coords.maxOf { a -> a[1] }
            maxY - minY + maxX - minX
        }
        val grid = Array(currentBaggage.capacityX) { Array(currentBaggage.capacityY) { "" } }
        val result = ArrayList<PlacedFigure>()
        sortedGarbage.forEach { f ->
            for (i in 0..<currentBaggage.capacityX) {
                for (j in 0..<currentBaggage.capacityY) {
                    for (k in 0..3) {
                        val rotatedFigure = f // Need to rotate
                        var satisfy = true
                        for (coord in rotatedFigure.coords) {
                            if (grid[i + coord[0]][j + coord[1]] != "") {
                                satisfy = false
                                break
                            }
                        }
                        if (satisfy) {
                            for (coord in rotatedFigure.coords) {
                                grid[i + coord[0]][j + coord[1]] = "1"
                            }
                            result.add(PlacedFigure(rotatedFigure, "1"))
                            break
                        }
                    }
                }
            }
        }
        return ArrayList()
    }

    override fun getHowManyCanAdd(baggage: IShipBaggage): Int {
        return upgradeLoad(baggage).size - baggage.load.size
    }

    override fun makeOptimalLoad(baggage: IShipBaggage) {
        baggage.load = upgradeLoad(baggage)
    }

    override fun setGarbage(garbage: MutableList<Figure>) {
        this.garbage = garbage
    }

    override fun getGarbage(): MutableList<Figure> = garbage
}
