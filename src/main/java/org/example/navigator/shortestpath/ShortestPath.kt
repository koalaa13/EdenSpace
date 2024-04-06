package org.example.navigator.shortestpath

interface ShortestPath<T> {
    val distanceTo: Map<String, T?>
    val lastOnPathTo: Map<String, String?>

    fun getReachable(): Set<String> = distanceTo.keys

    fun getIntermediate(to: String): List<String> =
        buildList {
            var current = lastOnPathTo[to]
            while (current != null) {
                add(current)
                current = lastOnPathTo[current]
            }
        }.dropLast(1).reversed()
}