package org.example.model.tetris

import org.example.model.PlacedFigure

class TShipBaggage : IShipBaggage {
    var placedFigures: MutableList<PlacedFigure> = ArrayList()

    override fun getLoad(): MutableList<PlacedFigure> = placedFigures

    override fun clean() {
        placedFigures = ArrayList()
    }
}
