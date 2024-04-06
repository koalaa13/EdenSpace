package org.example.navigator

import org.example.model.Figure
import org.example.model.graph.Graph
import org.example.model.tetris.IPlanet
import org.example.model.tetris.IShipBaggage
import org.example.model.tetris.TPlanet
import org.example.navigator.shortestpath.BfsNumberOfEdges
import org.example.navigator.shortestpath.ShortestPath


abstract class AbstractNavigator(graph: Graph) : INavigator {
    protected val state = NavigatorState(graph)

    protected abstract fun getPlanetsToVisit(currentPlanet: String, baggage: IShipBaggage): List<String>?

    protected abstract fun buildShortestPaths(origin: String): ShortestPath<*>

    override fun getMove(currentPlanet: String, baggage: IShipBaggage): List<String>? {
        val planetsToVisit = getPlanetsToVisit(currentPlanet, baggage) ?: return null
        return buildList {
            var currentPlanet = currentPlanet
            for (next in planetsToVisit) {
                val trajectory = buildShortestPaths(currentPlanet).getIntermediate(next) + next
                state.graph.getEdgesFrom(currentPlanet)[trajectory[0]] =
                    state.graph.getEdgesFrom(currentPlanet)[trajectory[0]]!! + 10
                for (i in 1 until trajectory.size) {
                    state.graph.getEdgesFrom(trajectory[i - 1])[trajectory[i]] =
                        state.graph.getEdgesFrom(trajectory[i - 1])[trajectory[i]]!! + 10
                }
                addAll(trajectory)
                currentPlanet = next
            }
        }
    }

    override fun setPlanetGarbage(planetName: String, garbage: Map<String, Figure>): IPlanet {
        val planet = state.knownPlanets.getOrDefault(planetName, TPlanet())
        planet.garbage = garbage
        state.knownPlanets[planetName] = planet
        return planet
    }
}