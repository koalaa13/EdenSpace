package org.example.navigator

import org.example.model.Figure
import org.example.model.graph.Graph
import org.example.model.tetris.IPlanet
import org.example.model.tetris.IShipBaggage

class Navigator(graph: Graph) : INavigator {


    override fun getMove(baggage: IShipBaggage): List<String> {
        TODO("Not yet implemented")
    }

    override fun setPlanetGarbage(planet: String, garbage: List<Figure>): IPlanet {
        TODO("Not yet implemented")
    }
}