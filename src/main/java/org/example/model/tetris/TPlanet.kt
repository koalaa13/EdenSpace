package org.example.model.tetris

import org.example.model.Figure
import org.example.model.PlacedFigure
import org.example.service.UtilService

class TPlanet : IPlanet {
    private var garbage: MutableMap<String, Figure> = HashMap()

    companion object {
        private val solvers: List<ISolver> = listOf(
            TAbstractGreedySolver { _, minX, maxX, minY, maxY ->
                (maxY - minY + maxX - minX).toDouble()
            },  // By perimetr
            TAbstractGreedySolver { _, minX, maxX, minY, maxY ->
                (maxY - minY) * (maxX - minX).toDouble()
            },  // By area
            TAbstractGreedySolver { sz, minX, maxX, minY, maxY ->
                sz.toDouble() / ((maxY - minY) * (maxX - minX))
            },  // By density
            TAbstractGreedySolver { sz, minX, maxX, minY, maxY ->
                sz.toDouble()
            },  // By size
            TAbstractGreedySolver { _, minX, maxX, minY, maxY ->
                -(maxY - minY + maxX - minX).toDouble()
            },  // From big to small
            TAbstractGreedySolver { _, minX, maxX, minY, maxY ->
                -(maxY - minY) * (maxX - minX).toDouble()
            },
            TAbstractGreedySolver { sz, minX, maxX, minY, maxY ->
                -sz.toDouble() / ((maxY - minY) * (maxX - minX))
            },
            TAbstractGreedySolver { sz, minX, maxX, minY, maxY ->
                -sz.toDouble()
            },
        )

        fun getSolvers(): List<ISolver> {
            return solvers;
        }
    }

    private fun findOptimalLoad(currentBaggage: IShipBaggage): MutableList<PlacedFigure> {
        return solvers
            .map { solver -> solver.findOptimalLoad(currentBaggage, garbage) }
            .maxBy { pfs -> UtilService.getInstance().calcFillPercentage(pfs, currentBaggage.capacityX, currentBaggage.capacityY) }
    }

    override fun getHowManyCanAdd(baggage: IShipBaggage): Int {
        return findOptimalLoad(baggage).size - baggage.load.size
    }

    override fun howMuchCanFillPercentage(baggage: IShipBaggage): Double {
        val optimal = findOptimalLoad(baggage)
        return UtilService.getInstance().calcFillPercentage(optimal, baggage.capacityX, baggage.capacityY) - UtilService.getInstance().calcFillPercentage(baggage)
    }

    override fun howManyCanAddCell(baggage: IShipBaggage): Int {
        val optimal = findOptimalLoad(baggage)
        return optimal.sumOf { it.figure.coords.size } - baggage.load.sumOf { it.figure.coords.size }
    }

    override fun makeOptimalLoad(baggage: IShipBaggage) {
        val brought = baggage.load.toList()
        baggage.load = findOptimalLoad(baggage)
        for (figure in baggage.load) {
            garbage.remove(figure.name)
        }
        for (figure in brought) {
            if (figure.name !in baggage.load.map { it.name }) {
                garbage.put(figure.name, figure.figure)
            }
        }
    }

    override fun setGarbage(garbage: MutableMap<String, Figure>) {
        this.garbage = garbage
    }

    override fun getGarbage(): MutableMap<String, Figure> = garbage
}
