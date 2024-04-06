package org.example.navigator.shortestpath;

import org.example.model.graph.Graph

class BfsNumberOfEdges(origin: String, graph: Graph) : ShortestPath<Int> {
    override val distanceTo: Map<String, Int>
    override val lastOnPathTo: Map<String, String>

    init {
        distanceTo = mutableMapOf(origin to 0)
        lastOnPathTo = mutableMapOf()
        val queue = ArrayDeque<String>().apply { add(origin) }
        while (queue.isNotEmpty()) {
            val node = queue.removeFirst()
            val newDistance = distanceTo[node]!! + 1
            for (e in graph.getEdgesFrom(node)) {
                if (e.to in distanceTo) {
                    continue
                }
                distanceTo[e.to] = newDistance
                queue.add(e.to)
            }
        }
    }
}
