package ru.hse.rogue.model.connection

import ru.hse.rogue.model.Game
import ru.hse.rogue.model.map.GameMap
import ru.hse.rogue.model.map.MapElement

class ModelViewConnectionImpl internal constructor(private val gameMap: GameMap) : ModelViewConnection {
    override fun getMap(): List<List<MapElement>> = gameMap.mapElements
}
