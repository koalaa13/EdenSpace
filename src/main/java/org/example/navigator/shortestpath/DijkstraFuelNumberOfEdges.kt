package org.example.navigator.shortestpath

import org.example.model.graph.Graph
import java.util.TreeSet

class DijkstraFuelNumberOfEdges(private val origin: String, graph: Graph) :
    ShortestPath<DijkstraFuelNumberOfEdges.Distance> {
    data class Distance(val fuel: Int, val numberOfEdges: Int) : Comparable<Distance> {
        override fun compareTo(other: Distance) =
            if (fuel != other.fuel) {
                fuel.compareTo(other.fuel)
            } else {
                numberOfEdges.compareTo(other.numberOfEdges)
            }

        fun plusEdge(fuelWeight: Int): Distance = Distance(fuel + fuelWeight, numberOfEdges + 1)
    }


    override val distanceTo: Map<String, Distance>
    override val lastOnPathTo: Map<String, String>

    init {
        distanceTo = mutableMapOf(origin to Distance(0, 0))
        lastOnPathTo = mutableMapOf()
        val heap: TreeSet<Pair<Distance, String>> =
            TreeSet<Pair<Distance, String>> { p1, p2 ->
                if (p1.first != p2.first)
                    p1.first.compareTo(p2.first)
                else
                    p1.second.compareTo(p2.second)
            }
        heap.add(distanceTo[origin]!! to origin)

        while (heap.isNotEmpty()) {
            val (dist, node) = heap.pollFirst()!!
            for (e in graph.getEdgesFrom(node)) {
                val currentDistance = distanceTo[e.key]
                val newDistance = dist.plusEdge(e.value)
                if (currentDistance != null && currentDistance <= newDistance) {
                    continue
                }
                if (currentDistance != null) {
                    heap.remove(currentDistance to e.key)
                }
                distanceTo[e.key] = newDistance
                lastOnPathTo[e.key] = node
                heap.add(newDistance to e.key)
            }
        }
    }
}