package org.example.model.tetris

import org.example.model.PlacedFigure

class TShipBaggage(val capacityX: Int, val capacityY: Int) : IShipBaggage {
    var placedFigures: MutableList<PlacedFigure> = ArrayList()

    override fun getLoad(): MutableList<PlacedFigure> = placedFigures
    override fun setLoad(newLoad: MutableList<PlacedFigure>) {
        placedFigures = newLoad
    }

    override fun clean() {
        placedFigures = ArrayList()
    }
}
