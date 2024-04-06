package org.example.navigator

import org.example.model.graph.Graph
import org.example.model.tetris.IShipBaggage
import org.example.navigator.shortestpath.DijkstraFuelNumberOfEdges

private const val EDEN = "Eden"
private const val EARTH = "Earth"

/*
Сначала посещает все планеты по одному разу, затем летает на ту, с которой можно вывезти больше всего мусора.
Всегда летает через Eden
*/
class Navigator(graph: Graph) : AbstractNavigator(graph) {
    override fun buildShortestPaths(origin: String) = DijkstraFuelNumberOfEdges(origin, graph)

    override fun getPlanetsToVisit(currentPlanet: String, baggage: IShipBaggage): List<String>? {
        println("baggage stats: ${baggage.freeSpace.toDouble() / baggage.area} ${baggage.loadConvexHullArea.toDouble() / baggage.area}")
        val shortestPath = buildShortestPaths(currentPlanet)
        val unexploredPlanet = (planetNames - knownPlanets.keys - setOf(EDEN, EARTH, currentPlanet))
            .map { it to shortestPath.distanceTo[it] }
            .filter { it.second != null }
            .minByOrNull { it.second!! }
            ?.first

        if (unexploredPlanet != null) {
            if (baggage.freeSpace.toDouble() / baggage.area > 1 &&
                baggage.loadConvexHullArea.toDouble() / baggage.area < 0
            ) {
                return listOf(unexploredPlanet)
            }

            return listOf(EDEN, unexploredPlanet)
        }

        val bestKnownPlanet = knownPlanets
            .map { Triple(it.key, it.value, it.value.getHowManyCanAdd(baggage)) }
            .maxByOrNull { it.third }

        return when {
            bestKnownPlanet != null && bestKnownPlanet.second.getHowManyCanAdd(baggage) > 0 -> {
                if (bestKnownPlanet.first != currentPlanet && baggage.freeSpace.toDouble() / baggage.area > 1 &&
                    baggage.loadConvexHullArea.toDouble() / baggage.area < 0
                ) {
                    listOf(bestKnownPlanet.first)
                } else {
                    listOf(EDEN, bestKnownPlanet.first)
                }
            }

            currentPlanet != EDEN -> listOf(EDEN)
            else -> null
        }
    }
}