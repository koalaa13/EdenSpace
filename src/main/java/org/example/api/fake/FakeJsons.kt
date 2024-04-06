package org.example.api.fake

private const val EDEN = "Eden"
private const val EARTH = "Earth"
private const val ONE = "One"
private const val TWO = "Two"
private const val THREE = "Three"

/*
Eden <-> Earth <-> One <-> Two <-> Three
*/
private fun getFivePlanetsBamboo() = mapOf(
    EDEN to mutableMapOf(EARTH to 100),
    EARTH to mutableMapOf(EDEN to 100, ONE to 1),
    ONE to mutableMapOf(EARTH to 1, TWO to 1),
    TWO to mutableMapOf(ONE to 1, THREE to 1),
    THREE to mutableMapOf(TWO to 1)
)

private fun getFigures1() = mutableMapOf<String, ServerFigureInfo>().apply {
    put("0", ServerFigureInfo(listOf(listOf(1, 1)), ONE))
    put("1", ServerFigureInfo(listOf(listOf(1, 1)), TWO))
    put("2", ServerFigureInfo(listOf(listOf(1, 1)), THREE))
}

private fun getFigures2() = mutableMapOf<String, ServerFigureInfo>().apply {
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


object FakeJsons {
    fun get1() = FivePlanetsBambooFakeJson(getFivePlanetsBamboo(), getFigures1())
    fun get2() = FivePlanetsBambooFakeJson(getFivePlanetsBamboo(), getFigures2())
}