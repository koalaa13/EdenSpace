package org.example.navigator

import org.example.model.Figure
import org.example.model.graph.Graph
import org.example.model.tetris.IPlanet
import org.example.model.tetris.TPlanet
import org.example.navigator.shortestpath.BfsNumberOfEdges
import org.example.navigator.shortestpath.ShortestPath

private const val EARTH = "Earth"

abstract class AbstractNavigator(protected val graph: Graph) : INavigator {
    protected val planetNames = BfsNumberOfEdges(EARTH, graph).getReachable()

    /*
    Хранит только планеты, на которых точно знает весь мусор, не хранит Землю и Eden
    */
    protected val knownPlanets = mutableMapOf<String, IPlanet>()

    abstract fun buildShortestPaths(origin: String): ShortestPath<*>

    protected fun buildShortestTrajectory(from: String, to: String): List<String> =
        buildShortestPaths(from).getIntermediate(to) + to

    override fun setPlanetGarbage(planetName: String, garbage: Map<String, Figure>): IPlanet {
        val planet = knownPlanets.getOrDefault(planetName, TPlanet())
        planet.garbage = garbage
        knownPlanets[planetName] = planet
        return planet
    }
}