package org.example.model.tetris

import org.example.model.Figure
import org.example.model.PlacedFigure

class TPlanet : IPlanet {
    var garbage: MutableList<Figure> = ArrayList()

    private fun upgradeLoad(baggage: IShipBaggage): MutableList<PlacedFigure> {
        return ArrayList()
    }

    override fun getHowManyCanAdd(baggage: IShipBaggage): Int {
        TODO("Not yet implemented")
    }

    override fun makeOptimalLoad(baggage: IShipBaggage) {
        TODO("Not yet implemented")
    }

    override fun setGarbage(garbage: MutableList<Figure>) {
        this.garbage = garbage
    }

    override fun getGarbage(): MutableList<Figure> = garbage
}
