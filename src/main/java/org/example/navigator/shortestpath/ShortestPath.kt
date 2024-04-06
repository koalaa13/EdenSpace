package org.example.navigator.shortestpath

interface ShortestPath<T> {
    fun getDistanceTo(destination: String): T?
    fun getLastOnPathTo(destination: String): String?
    fun getReachable(): Set<String>

    fun getIntermediate(to: String): List<String> =
        buildList {
            var current = getLastOnPathTo(to)
            while (current != null) {
                add(current)
                current = getLastOnPathTo(current)
            }
        }.dropLast(1).reversed()
}