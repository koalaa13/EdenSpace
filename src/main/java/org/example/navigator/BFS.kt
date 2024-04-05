package org.example.navigator

import org.example.model.graph.Graph

private const val EDEN = "Eden"

fun computeNextPlanetOnThePathToEden(graph: Graph): Map<String, String> {
    val result = mutableMapOf(EDEN to EDEN)
    val queue = ArrayDeque<String>().apply { add(EDEN) }
    while (queue.isNotEmpty()) {
        val planet = queue.removeFirst()
        for (e in graph.getEdgesFrom(planet)) {
            if (e.to in result) {
                continue
            }
            result[e.to] = planet
            queue.add(e.to)
        }
    }
    return result
}