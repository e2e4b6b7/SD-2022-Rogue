package ru.hse.rogue.model.connection

import ru.hse.rogue.model.Game
import ru.hse.rogue.model.gameobject.*
import ru.hse.rogue.model.map.*

class ModelScenarioControllerConnectionImpl internal constructor(private val gameMap: GameMap) :
    ModelScenarioControllerConnection {
    override fun getSearchableWithPos(id: SearchId) = gameMap.searchObject(id)

    override fun addGameObject(gameObject: GameObject, position: Position): Boolean {
        TODO()
    }

    override fun getMap(): List<List<MapElement>> = gameMap.mapElements
}
