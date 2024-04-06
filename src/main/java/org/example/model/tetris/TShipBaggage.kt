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

    override fun getFreeSpace(): Int {
        return capacityX * capacityY - placedFigures.sumOf { it.figure.coords.size }
    }

    override fun clean() {
        placedFigures = ArrayList()
    }
}
