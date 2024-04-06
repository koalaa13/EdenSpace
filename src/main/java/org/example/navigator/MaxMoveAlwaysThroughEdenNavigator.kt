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
class MaxMoveAlwaysThroughEdenNavigator(graph: Graph) : AbstractNavigator(graph) {
    override fun buildShortestPaths(origin: String) = DijkstraFuelNumberOfEdges(origin, graph)

    override fun getPlanetsToVisit(currentPlanet: String, baggage: IShipBaggage): List<String>? {
        val shortestPath = buildShortestPaths(currentPlanet)
        val unexploredPlanet = (planetNames - knownPlanets.keys - setOf(EDEN, EARTH))
            .map { it to shortestPath.distanceTo[it] }
            .filter { it.second != null }
            .minByOrNull { it.second!! }
            ?.first

        if (unexploredPlanet != null) {
            if (baggage.freeSpace.toDouble() / baggage.area > 0.3 &&
                baggage.loadConvexHullArea.toDouble() / baggage.area < 0.8
            ) {
                return listOf(unexploredPlanet)
            }

            return listOf(EDEN, unexploredPlanet)
        }

        val bestKnownPlanet = knownPlanets
            .map { Triple(it.key, it.value, it.value.getHowManyCanAdd(baggage)) }
            .maxByOrNull { it.third }

        return when {
            bestKnownPlanet != null && bestKnownPlanet.second.getHowManyCanAdd(baggage) > 0 ->
                listOf(EDEN, bestKnownPlanet.first)

            currentPlanet != EDEN -> listOf(EDEN)
            else -> null
        }
    }
}