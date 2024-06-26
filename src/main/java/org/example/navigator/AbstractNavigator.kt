package org.example.navigator

import org.example.model.Figure
import org.example.model.graph.Graph
import org.example.model.tetris.IPlanet
import org.example.model.tetris.IShipBaggage
import org.example.model.tetris.TPlanet
import org.example.navigator.shortestpath.BfsNumberOfEdges
import org.example.navigator.shortestpath.DijkstraFuelNumberOfEdges
import org.example.navigator.shortestpath.ShortestPath

private const val EDEN = "Eden"

abstract class AbstractNavigator(protected val graph: Graph) : INavigator {
    protected val planetNames = BfsNumberOfEdges(EDEN, graph).getReachable()

    /*
    Хранит только планеты, на которых точно знает весь мусор, не хранит Землю и Eden
    */
    protected val knownPlanets = mutableMapOf<String, IPlanet>()

    protected abstract fun getPlanetsToVisit(currentPlanet: String, baggage: IShipBaggage, i: Int): List<String>?

    protected abstract fun buildShortestPaths(origin: String, i: Int): ShortestPath<*>

    override fun getMove(currentPlanet: String, baggage: IShipBaggage, it: Int): List<String>? {
        val planetsToVisit = getPlanetsToVisit(currentPlanet, baggage, it) ?: return null
        println("From $currentPlanet want to visit ${planetsToVisit.joinToString()}")
        return buildList {
            var currentPlanet = currentPlanet
            for (next in planetsToVisit) {
                val trajectory = buildShortestPaths(currentPlanet, it).getIntermediate(next) + next
                graph.getEdgesFrom(currentPlanet)[trajectory[0]] =
                    graph.getEdgesFrom(currentPlanet)[trajectory[0]]!! + 10
                for (i in 1 until trajectory.size) {
                    graph.getEdgesFrom(trajectory[i - 1])[trajectory[i]] =
                        graph.getEdgesFrom(trajectory[i - 1])[trajectory[i]]!! + 10
                }
                addAll(trajectory)
                currentPlanet = next
            }
        }
    }

    override fun setPlanetGarbage(planetName: String, garbage: Map<String, Figure>): IPlanet {
        val planet = knownPlanets.getOrDefault(planetName, TPlanet())
        planet.garbage = garbage
        knownPlanets[planetName] = planet
        return planet
    }
}