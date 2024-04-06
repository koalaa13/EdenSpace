package org.example.navigator

import org.example.model.graph.Graph
import org.example.model.tetris.IPlanet
import org.example.navigator.shortestpath.BfsNumberOfEdges

private const val EARTH = "Earth"

class NavigatorState(val graph: Graph) {
    val planetNames = BfsNumberOfEdges(EARTH, graph).getReachable()

    /*
    Хранит только планеты, на которых точно знает весь мусор, не хранит Землю и Eden
    */
    val knownPlanets = mutableMapOf<String, IPlanet>()
}