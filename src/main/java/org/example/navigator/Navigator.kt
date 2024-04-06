package org.example.navigator

import org.example.model.Figure
import org.example.model.graph.Graph
import org.example.model.tetris.IPlanet
import org.example.model.tetris.IShipBaggage
import org.example.model.tetris.TPlanet
import org.example.navigator.shortestpath.DijkstraFuelNumberOfEdges

private const val EDEN = "Eden"
private const val EARTH = "Earth"


class Navigator(private val graph: Graph) : INavigator {
    private val planetNames = DijkstraFuelNumberOfEdges(EARTH, graph).getReachable()

    /*
    Хранит только планеты, на которых точно знает весь мусор, не хранит Землю и Eden
    */
    private val knownPlanets = mutableMapOf<String, IPlanet>()

    override fun getMove(currentPlanet: String, baggage: IShipBaggage): List<String>? {
        val bestKnownPlanet = knownPlanets.maxByOrNull { it.value.getHowManyCanAdd(baggage) }
        val unexploredPlanet = planetNames.firstOrNull {
            it !in knownPlanets && it !in setOf(EDEN, EARTH)
        }

        val wantKnown = bestKnownPlanet != null && bestKnownPlanet.value.getHowManyCanAdd(baggage) > 0
        val wantUnexplored = unexploredPlanet != null

        val destination = when {
            wantUnexplored -> unexploredPlanet!!
            wantKnown -> bestKnownPlanet!!.key
            else -> return null
        }

        val currentPlanetShortestPaths = DijkstraFuelNumberOfEdges(currentPlanet, graph)
        val edenPlanetShortestPaths = DijkstraFuelNumberOfEdges(EDEN, graph)

        return currentPlanetShortestPaths.getIntermediate(EDEN) +
                EDEN +
                edenPlanetShortestPaths.getIntermediate(destination) +
                destination
    }

    override fun setPlanetGarbage(planetName: String, garbage: Map<String, Figure>): IPlanet {
        val planet = knownPlanets.getOrDefault(planetName, TPlanet())
        planet.garbage = garbage
        knownPlanets[planetName] = planet
        return planet
    }
}