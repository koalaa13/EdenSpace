package org.example.api.fake

import org.example.api.GameInfo
import org.example.api.IJson
import org.example.model.Figure
import org.example.model.PlacedFigure
import org.example.model.PlanetInfo
import org.example.model.graph.Graph
import org.example.model.tetris.TShipBaggage
import org.example.visual.ship.ConsoleShipVisualizer

private const val EDEN = "Eden"
private const val EARTH = "Earth"
private const val ONE = "One"
private const val TWO = "Two"
private const val THREE = "Three"
private const val EDGE_WEIGHT = 1
private const val CAPACITY_X = 3
private const val CAPACITY_Y = 3

private const val SHIP = "Ship"

private data class ServerFigureInfo(
    val shape: List<List<Int>>,
    // Planet name, SHIP or null
    var location: String,
)

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

private fun getInitialFigures(): MutableMap<String, ServerFigureInfo> = mutableMapOf<String, ServerFigureInfo>().apply {
    val planets = setOf(ONE, TWO, THREE)
    val shapes = listOf(
        listOf(listOf(1, 1), listOf(1, 2), listOf(2, 2)),
        listOf(listOf(1, 1), listOf(1, 2), listOf(2, 2)),
        listOf(listOf(1, 1), listOf(1, 2), listOf(2, 2)),
        listOf(listOf(1, 1), listOf(1, 2), listOf(2, 2)),

        listOf(listOf(1, 1), listOf(1, 2), listOf(1, 3)),
    )

    for (planet in planets) {
        for (i in shapes.indices) {
            put("{$planet}_$i", ServerFigureInfo(shapes[i], planet))
        }
    }
}

private val shipVisualizer = ConsoleShipVisualizer()

class FivePlanetsBambooFakeJson : IJson {
    private val graph = getInitialMap()

    private val existingFigures = getInitialFigures()
    private var currentPlanet = EARTH
    private var score = 0

    private fun getFiguresOn(planetName: String): List<Map.Entry<String, ServerFigureInfo>> {
        return existingFigures.entries.filter { it.value.location == planetName }
    }

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
        println("[fake] <score: $score> move: ${trajectory.joinToString()}")

        require(trajectory.isNotEmpty())
        for (to in trajectory) {
            require(to in graph[currentPlanet]!!)
            currentPlanet = to
            if (currentPlanet == EDEN) {
                existingFigures.iterator().run {
                    while (hasNext()) {
                        val figurePosition = next()
                        if (figurePosition.value.location == SHIP) {
                            remove()
                            score++
                        }
                    }
                }
            }
        }

        return PlanetInfo().apply {
            name = currentPlanet
            garbage = getFiguresOn(currentPlanet).associate { (name, info) -> name to Figure(info.shape) }
        }
    }

    override fun load(newGarbage: List<PlacedFigure>) {
        println("[fake] <score: $score> load:")
        shipVisualizer.visualize(newGarbage, CAPACITY_X, CAPACITY_Y)

        require(currentPlanet != EDEN)
        for (figure in existingFigures.values) {
            if (figure.location == SHIP) {
                figure.location = currentPlanet
            }
        }
        for (figure in newGarbage) {
            val figureInfo = existingFigures[figure.name]
            require(figureInfo != null)
            require(figureInfo.location == currentPlanet)
            figureInfo.location = SHIP
        }
    }
}