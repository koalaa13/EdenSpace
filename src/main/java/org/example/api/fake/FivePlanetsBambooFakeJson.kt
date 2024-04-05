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
private const val CAPACITY_X = 1
private const val CAPACITY_Y = 1

private const val SHIP = "Ship"

private val FIGURE = listOf(listOf(1, 1))

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

/*
from_one on One, from_two on Two, from_three on Three
*/
// Planet name, SHIP or null
private fun getInitialFigurePositions(): MutableMap<String, String?> = mutableMapOf(
    "from_one" to ONE,
    "from_two" to TWO,
    "from_three" to THREE
)

private val shipVisualizer = ConsoleShipVisualizer()

class FivePlanetsBambooFakeJson : IJson {
    private val graph = getInitialMap()
    private val figurePositions = getInitialFigurePositions()
    private var currentPlanet = EARTH
    private var score = 0

    private fun getFiguresOn(planetName: String): List<String> {
        return figurePositions.entries.filter { it.value == planetName }.map { it.key }
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
            currentPlanet = to
            if (currentPlanet == EDEN) {
                figurePositions.iterator().run {
                    while (hasNext()) {
                        val figurePosition = next()
                        if (figurePosition.value == SHIP) {
                            remove()
                            score++
                        }
                    }
                }
            }
        }

        return PlanetInfo().apply {
            name = currentPlanet
            garbage = getFiguresOn(currentPlanet).associate { figureName ->
                figureName to Figure(FIGURE)
            }
        }
    }

    override fun load(newGarbage: List<PlacedFigure>) {
        println("[fake] load:")
        shipVisualizer.visualize(newGarbage, CAPACITY_X, CAPACITY_Y)

        require(currentPlanet != EDEN)
        for (figureName in figurePositions.keys) {
            if (figurePositions[figureName] == SHIP) {
                figurePositions[figureName] = currentPlanet
            }
        }
        for (figure in newGarbage) {
            val name = figure.name
            require(figurePositions[name]!! == currentPlanet)
            figurePositions[name] = SHIP
        }
    }
}