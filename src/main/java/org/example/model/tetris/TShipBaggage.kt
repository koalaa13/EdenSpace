package org.example.model.tetris

import org.example.model.PlacedFigure

class TShipBaggage(private val capacityX: Int, private val capacityY: Int) : IShipBaggage {
    var placedFigures: MutableList<PlacedFigure> = ArrayList()

    override fun getLoad(): MutableList<PlacedFigure> = placedFigures
    override fun setLoad(newLoad: MutableList<PlacedFigure>) {
        placedFigures = newLoad
    }

    override fun getCapacityX(): Int = capacityX

    override fun getCapacityY(): Int = capacityY

    override fun getArea() = capacityX * capacityY

    override fun getFreeSpace(): Int {
        return area - placedFigures.sumOf { it.figure.coords.size }
    }

    override fun getLoadConvexHullArea(): Int {
        if (placedFigures.isEmpty()) {
            return 0
        }

        val minX = placedFigures.minOf { it.figure.coords.minOf { it[0] } }
        val maxX = placedFigures.maxOf { it.figure.coords.maxOf { it[0] } }
        val minY = placedFigures.minOf { it.figure.coords.minOf { it[1] } }
        val maxY = placedFigures.maxOf { it.figure.coords.maxOf { it[1] } }

        return (maxX - minX) * (maxY - minY)
    }

    override fun clean() {
        placedFigures = ArrayList()
    }
}
