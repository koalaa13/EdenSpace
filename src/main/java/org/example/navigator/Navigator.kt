package org.example.navigator

import org.example.model.Figure
import org.example.model.graph.Graph
import org.example.model.tetris.IPlanet
import org.example.model.tetris.IShipBaggage
import org.example.model.tetris.TPlanet

private const val EDEN = "Eden"
private const val EARTH = "Earth"


class Navigator(graph: Graph) : INavigator {
    private val nextOnThePathToEden = computeNextPlanetOnThePathToEden(graph)
    private val planetNames = nextOnThePathToEden.keys

    /*
    Хранит только планеты, на которых точно знает весь мусор, не хранит Землю и Eden
    */
    private val knownPlanets = mutableMapOf<String, IPlanet>()

    // едем на планету, про которую ничего не знаем?
    private var willExplore = true

    private fun buildPathToEden(origin: String): List<String> = buildList {
        var planet = origin
        while (planet != EDEN) {
            planet = nextOnThePathToEden[planet]!!
            add(planet)
        }
    }

    private fun buildPath(origin: String, destination: String): List<String> {
        return buildPathToEden(origin) + buildPathToEden(destination).dropLast(1).reversed() + destination
    }

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

        return buildPath(currentPlanet, destination)
    }

    override fun setPlanetGarbage(planetName: String, garbage: Map<String, Figure>): IPlanet {
        val planet = knownPlanets.getOrDefault(planetName, TPlanet())
        planet.garbage = garbage
        knownPlanets[planetName] = planet
        return planet
    }
}