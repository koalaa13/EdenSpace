package org.example.model.tetris

import org.example.model.Figure
import org.example.model.PlacedFigure

class TPlanet : IPlanet {
    private var garbage: MutableList<Figure> = ArrayList()

    private fun upgradeLoad(currentBaggage: MutableList<PlacedFigure>): MutableList<PlacedFigure> {
        return ArrayList()
    }

    override fun getHowManyCanAdd(baggage: IShipBaggage): Int {
        return upgradeLoad(baggage.load).size - baggage.load.size
    }

    override fun makeOptimalLoad(baggage: IShipBaggage) {
        baggage.load = upgradeLoad(baggage.load)
    }

    override fun setGarbage(garbage: MutableList<Figure>) {
        this.garbage = garbage
    }

    override fun getGarbage(): MutableList<Figure> = garbage
}
