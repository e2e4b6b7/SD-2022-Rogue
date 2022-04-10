package ru.hse.rogue.model.connection

import ru.hse.rogue.model.Game
import ru.hse.rogue.model.gameobject.*
import ru.hse.rogue.model.map.MapElement
import ru.hse.rogue.model.map.Position

class ModelScenarioControllerConnectionImpl internal constructor(private val game: Game) :
    ModelScenarioControllerConnection {
    override fun getSearchableWithPos(id: SearchId): Pair<Searchable, Position>? {
        TODO("Not yet implemented")
    }

    override fun addGameObject(gameObject: GameObject, position: Position): Boolean {
        return game.addGameObject(gameObject, position)
    }

    override fun getMap(): List<List<MapElement>> = game.map
}
