package org.example.api.fake

import org.example.api.GameInfo
import org.example.api.IJson
import org.example.model.PlacedFigure
import org.example.model.PlanetInfo
import org.example.model.graph.Graph
import org.example.model.tetris.TShipBaggage

private const val EDEN = "Eden"
private const val EARTH = "Earth"
private const val ONE = "One"
private const val TWO = "Two"
private const val THREE = "Three"
private const val EDGE_WEIGHT = 1
private const val CAPACITY_X = 1
private const val CAPACITY_Y = 1

/*
Eden <-> Earth <-> One <-> Two <-> Three
*/
private fun getInitialMap(): Map<String, MutableMap<String, Int>> = mapOf(
    EDEN to mutableMapOf(EARTH to EDGE_WEIGHT),
    EARTH to mutableMapOf(EDEN to EDGE_WEIGHT, ONE to EDGE_WEIGHT),
    ONE to mutableMapOf(EARTH to EDGE_WEIGHT, TWO to EDGE_WEIGHT),
    TWO to mutableMapOf(ONE to EDGE_WEIGHT, THREE to EDGE_WEIGHT),
    THREE to mutableMapOf(TWO to EDGE_WEIGHT)
)

class FivePlanetsBambooFakeJson : IJson {
    private val graph = getInitialMap()
    private var currentPlanet = EARTH
    private var score = 0

    override fun getGameInfo(): GameInfo {
        val graph = Graph().apply {
            for (from in graph.keys) {
                graph[from]?.forEach { (to, w) ->
                    addEdge(from, to, w)
                }
            }
        }

        return GameInfo(graph, TShipBaggage(CAPACITY_X, CAPACITY_Y))
    }

    override fun move(trajectory: List<String>): PlanetInfo {
        require(trajectory.isNotEmpty())
        for (to in trajectory) {
            require(to in graph[currentPlanet]!!)
            currentPlanet = to
            if (currentPlanet == EDEN) {
                score += 0
                TODO("add score correctly")
                TODO("clean ship")
            }
        }

        return TODO()
    }

    override fun load(newGarbage: List<PlacedFigure>) {
        TODO("Not yet implemented")
    }

}