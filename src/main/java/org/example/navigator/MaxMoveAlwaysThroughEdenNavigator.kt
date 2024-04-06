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
    override fun buildShortestPaths(origin: String) = DijkstraFuelNumberOfEdges(origin, state.graph)

    override fun getPlanetsToVisit(currentPlanet: String, baggage: IShipBaggage): List<String>? {
        val unexploredPlanet = state.planetNames.firstOrNull {
            it !in state.knownPlanets && it !in setOf(EDEN, EARTH)
        }
        if (unexploredPlanet != null) {
            return listOf(EDEN, unexploredPlanet)
        }

        val bestKnownPlanet = state.knownPlanets.maxByOrNull { it.value.getHowManyCanAdd(baggage) }

        return when {
            bestKnownPlanet != null && bestKnownPlanet.value.getHowManyCanAdd(baggage) > 0 ->
                listOf(EDEN, bestKnownPlanet.key)

            currentPlanet != EDEN -> listOf(EDEN)
            else -> null
        }
    }
}