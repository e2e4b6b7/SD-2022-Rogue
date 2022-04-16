package ru.hse.rogue.model.connection

import ru.hse.rogue.model.map.GameMap

class ModelViewConnectionImpl internal constructor(private val gameMap: GameMap) : ModelViewConnection {
    override val map get() = gameMap.mapElements
}
