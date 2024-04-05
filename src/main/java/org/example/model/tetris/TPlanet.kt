package org.example.model.tetris

import org.example.model.Figure
import org.example.model.PlacedFigure

class TPlanet : IPlanet {
    private var garbage: MutableMap<String, Figure> = HashMap()

    companion object {
        private val solvers: List<ISolver> = listOf(
                TAbstractGreedySolver { minX, maxX, minY, maxY -> (maxY - minY + maxX - minX) },  // By perimetr
                TAbstractGreedySolver { minX, maxX, minY, maxY -> (maxY - minY) * (maxX - minX) },  // By area
                TAbstractGreedySolver { minX, maxX, minY, maxY -> -(maxY - minY + maxX - minX) },  // From big to small
                TAbstractGreedySolver { minX, maxX, minY, maxY -> -(maxY - minY) * (maxX - minX) },
        )
        fun getSolvers(): List<ISolver> {
            return solvers;
        }
    }

    private fun findOptimalLoad(currentBaggage: IShipBaggage): MutableList<PlacedFigure> {
        return solvers
            .map { solver -> solver.findOptimalLoad(currentBaggage, garbage) }
            .maxBy { pfs -> pfs.size }
    }

    override fun getHowManyCanAdd(baggage: IShipBaggage): Int {
        return findOptimalLoad(baggage).size - baggage.load.size
    }

    override fun makeOptimalLoad(baggage: IShipBaggage) {
        baggage.load = findOptimalLoad(baggage)
        for (figure in baggage.load) {
            garbage.remove(figure.name)
        }
    }

    override fun setGarbage(garbage: MutableMap<String, Figure>) {
        this.garbage = garbage
    }

    override fun getGarbage(): MutableMap<String, Figure> = garbage
}
