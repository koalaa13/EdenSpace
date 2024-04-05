package org.example.api

import org.example.model.PlacedFigure
import org.example.model.PlanetInfo

class FakeIJson: IJson {
    override  fun getGameInfo(): GameInfo {
        TODO("Not yet implemented")
    }

    override fun move(trajectory: MutableList<String>?): PlanetInfo {
        TODO("Not yet implemented")
    }

    override fun load(newGarbage: MutableList<PlacedFigure>?) {
        TODO("Not yet implemented")
    }

}