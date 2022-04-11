package ru.hse.rogue.model.connection

import ru.hse.rogue.model.gameobject.GameObject
import ru.hse.rogue.model.gameobject.SearchId
import ru.hse.rogue.model.map.GameMap
import ru.hse.rogue.model.map.Position

class ModelScenarioControllerConnectionImpl internal constructor(
    private val gameMap: GameMap
) : ModelScenarioControllerConnection {
    override fun getSearchableWithPos(id: SearchId) = gameMap.searchObject(id)

    override fun addGameObject(gameObject: GameObject, position: Position): Boolean {
        TODO()
    }

    override val map get() = gameMap.mapElements
}
