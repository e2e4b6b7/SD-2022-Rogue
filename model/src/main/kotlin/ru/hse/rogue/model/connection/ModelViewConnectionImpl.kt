package ru.hse.rogue.model.connection

import ru.hse.rogue.model.map.GameMap
import ru.hse.rogue.model.map.MapElement

class ModelViewConnectionImpl internal constructor(private val gameMap: GameMap) : ModelViewConnection {
    override val map: List<List<MapElement>>
        get() = gameMap.mapElements
}
