package org.example.navigator

import org.example.model.Figure
import org.example.model.graph.Graph
import org.example.model.tetris.IPlanet
import org.example.model.tetris.IShipBaggage
import org.example.model.tetris.TPlanet
import org.example.navigator.shortestpath.DijkstraFuelNumberOfEdges

private const val EDEN = "Eden"
private const val EARTH = "Earth"

/*
Сначала посещает все планеты по одному разу, затем летает на ту, с которой можно вывезти больше всего мусора.
Всегда летает через Eden
*/
class MaxMoveAlwaysThroughEdenNavigator(private val graph: Graph) : INavigator {
    private val planetNames = DijkstraFuelNumberOfEdges(EARTH, graph).getReachable()

    /*
    Хранит только планеты, на которых точно знает весь мусор, не хранит Землю и Eden
    */
    private val knownPlanets = mutableMapOf<String, IPlanet>()

    private fun buildPath(currentPlanet: String, destination: String): List<String> {
        val currentPlanetShortestPaths = DijkstraFuelNumberOfEdges(currentPlanet, graph)
        if (destination == EDEN) {
            return currentPlanetShortestPaths.getIntermediate(EDEN) + EDEN
        }

        val edenPlanetShortestPaths = DijkstraFuelNumberOfEdges(EDEN, graph)
        return currentPlanetShortestPaths.getIntermediate(EDEN) +
                EDEN +
                edenPlanetShortestPaths.getIntermediate(destination) +
                destination
    }

    override fun getMove(currentPlanet: String, baggage: IShipBaggage): List<String>? {
        val unexploredPlanet = planetNames.firstOrNull {
            it !in knownPlanets && it !in setOf(EDEN, EARTH)
        }
        if (unexploredPlanet != null) {
            return buildPath(currentPlanet, unexploredPlanet)
        }

        val bestKnownPlanet = knownPlanets.maxByOrNull { it.value.getHowManyCanAdd(baggage) }

        val destination = when {
            bestKnownPlanet != null && bestKnownPlanet.value.getHowManyCanAdd(baggage) > 0 -> bestKnownPlanet.key
            currentPlanet != EDEN -> EDEN
            else -> return null
        }
        return buildPath(currentPlanet, destination)
    }

    override fun setPlanetGarbage(planetName: String, garbage: Map<String, Figure>): IPlanet {
        val planet = knownPlanets.getOrDefault(planetName, TPlanet())
        planet.garbage = garbage
        knownPlanets[planetName] = planet
        return planet
    }
}