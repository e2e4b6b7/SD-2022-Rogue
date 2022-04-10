package ru.hse.rogue.model.connection

import ru.hse.rogue.model.Game
import ru.hse.rogue.model.map.GameMap
import ru.hse.rogue.model.map.MapElement

class ModelViewConnectionImpl internal constructor(private val game: Game) : ModelViewConnection {
    override fun getMap(): List<List<MapElement>> = game.map
}
