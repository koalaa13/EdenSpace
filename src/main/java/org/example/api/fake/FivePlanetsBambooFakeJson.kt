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
private const val CAPACITY_X = 3
private const val CAPACITY_Y = 3

private const val SHIP = "Ship"

data class ServerFigureInfo(
    val shape: List<List<Int>>,
    // Planet name, SHIP or null
    var location: String,
)

private val shipVisualizer = ConsoleShipVisualizer()

class FivePlanetsBambooFakeJson(
    private val graph: Map<String, MutableMap<String, Int>>,
    private val existingFigures: MutableMap<String, ServerFigureInfo>,
) : IJson {
    private var currentPlanet = EARTH
    private var score = 0
    private var fuelSpent = 0

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
        println("[fake] move: ${trajectory.joinToString()}")

        require(trajectory.isNotEmpty())
        for (to in trajectory) {
            require(to in graph[currentPlanet]!!)
            fuelSpent += graph[currentPlanet]!![to]!!
            graph[currentPlanet]!![to] = graph[currentPlanet]!![to]!! + 10
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

        println("[fake] score: $score, $fuelSpent")
        return PlanetInfo().apply {
            name = currentPlanet
            garbage = getFiguresOn(currentPlanet).associate { (name, info) -> name to Figure(info.shape) }
        }
    }

    override fun load(newGarbage: List<PlacedFigure>) {
        println("[fake] load:")
        shipVisualizer.visualize(newGarbage, CAPACITY_X, CAPACITY_Y)

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

        println("[fake] score: $score, $fuelSpent")
    }
}