package org.example.api

import org.example.model.graph.Graph
import org.example.model.tetris.IShipBaggage

data class GameInfo(val graph: Graph, val shipBaggage: IShipBaggage, val startPlanet: String)