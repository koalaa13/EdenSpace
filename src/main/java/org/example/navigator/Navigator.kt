package org.example.navigator

import org.example.model.graph.Graph
import org.example.model.tetris.IShipBaggage
import org.example.model.tetris.TShipBaggage
import org.example.navigator.shortestpath.BfsNumberOfEdges
import org.example.navigator.shortestpath.DijkstraFuelNumberOfEdges
import org.example.navigator.shortestpath.ShortestPath
import kotlin.math.ceil

private const val EDEN = "Eden"
private const val EARTH = "Earth"

/*
Сначала посещает все планеты по одному разу, затем летает на ту, с которой можно вывезти больше всего мусора.
Всегда летает через Eden
*/
class Navigator(graph: Graph) : AbstractNavigator(graph) {
    override fun buildShortestPaths(origin: String, i: Int) = DijkstraFuelNumberOfEdges(origin, graph)

    override fun getPlanetsToVisit(currentPlanet: String, baggage: IShipBaggage, i: Int): List<String>? {
        println("baggage stats: ${baggage.freeSpace.toDouble() / baggage.area} ${baggage.loadConvexHullArea.toDouble() / baggage.area}")
        val shortestPath = buildShortestPaths(currentPlanet, i)
        val unexploredPlanet = (planetNames - knownPlanets.keys - setOf(EDEN, EARTH, currentPlanet))
            .map { it to shortestPath.distanceTo[it] }
            .filter { it.second != null }
            .minByOrNull { it.second!! }
            ?.first

        if (unexploredPlanet != null) {
            if (baggage.freeSpace.toDouble() / baggage.area > 0.4) {
                return listOf(unexploredPlanet)
            }

            return listOf(EDEN, unexploredPlanet)
        }

        if (baggage.busySpace > 0) {
            val needNew = ceil(baggage.area * 0.05).toInt()
            val nearestKnownPlanet = knownPlanets
                .filter { it.value.howManyCanAddCell(baggage) >= needNew }
                .map { Triple(it.key, it.value, shortestPath.distanceTo[it.key]!!) }
                .minByOrNull { it.third }
                ?.first

            if (nearestKnownPlanet != null) {
                return listOf(nearestKnownPlanet)
            }
        }

        val needNewFromEmpty = ceil(baggage.area * 0.3).toInt()
        val emptyBaggage = TShipBaggage(baggage.capacityX, baggage.capacityY)
        val shortestPathFromEden = buildShortestPaths(EDEN, i)
        val nearestToEdenKnownPlanet = knownPlanets
            .filter { it.value.howManyCanAddCell(emptyBaggage) >= needNewFromEmpty }
            .map { Triple(it.key, it.value, shortestPathFromEden.distanceTo[it.key]!!) }
            .minByOrNull { it.third }
            ?.first

        if (nearestToEdenKnownPlanet != null) {
            return listOf(EDEN, nearestToEdenKnownPlanet)
        }

        val bestKnownPlanet = knownPlanets
            .map { Triple(it.key, it.value, it.value.howManyCanAddCell(emptyBaggage)) }
            .maxByOrNull { it.third }

        if (bestKnownPlanet != null && bestKnownPlanet.third > 0) {
            return listOf(EDEN, bestKnownPlanet.first)
        }

        if (currentPlanet != EDEN) {
            return listOf(EDEN)
        }

        return null
    }
}