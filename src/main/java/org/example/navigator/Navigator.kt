package org.example.navigator

import org.example.model.graph.Graph
import org.example.model.tetris.IShipBaggage
import org.example.model.tetris.TShipBaggage
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
            if (baggage.freeSpace.toDouble() / baggage.area > 0.8 &&
                baggage.loadConvexHullArea.toDouble() / baggage.area < 0.2
            ) {
                return listOf(unexploredPlanet)
            }

            return listOf(EDEN, unexploredPlanet)
        }

        if (baggage.freeSpace.toDouble() / baggage.area > 0.8 &&
            baggage.loadConvexHullArea.toDouble() / baggage.area < 0.2
        ) {
            val nearestKnownPlanet = knownPlanets
                .filter { it.value.howMuchCanFillPercentage(baggage) >= 0.1 }
                .map { Triple(it.key, it.value, shortestPath.distanceTo[it.key]!!) }
                .minByOrNull { it.third }
                ?.first

            if (nearestKnownPlanet != null) {
                return listOf(nearestKnownPlanet)
            }
        }

        val emptyBaggage = TShipBaggage(baggage.capacityX, baggage.capacityY)
        val bestKnownPlanet = knownPlanets
            .map { Triple(it.key, it.value, it.value.getHowManyCanAdd(emptyBaggage)) }
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