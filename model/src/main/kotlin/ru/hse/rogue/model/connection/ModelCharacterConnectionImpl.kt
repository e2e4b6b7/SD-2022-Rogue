package ru.hse.rogue.model.connection

import ru.hse.rogue.model.gameobject.GameObject
import ru.hse.rogue.model.gameobject.SearchId
import ru.hse.rogue.model.gameobject.character.Character
import ru.hse.rogue.model.level.Level
import ru.hse.rogue.model.map.Direction


class ModelCharacterConnectionImpl internal constructor(
    override val level: Level,
    private val _character: Character
) : ModelCharacterConnection {
    override val map get() = level.map.mapElements
    override val character = _character
    override fun move(direction: Direction) = level.map.move(_character.id, direction)
    override fun useInventory(inventoryId: SearchId) = _character.useInventory(inventoryId)
    override fun disableInventory(inventorId: SearchId) = _character.disableInventory(inventorId)
    override fun getSearchableWithPos(id: SearchId) = level.map.searchObject(id)
    override fun attack(direction: Direction): Boolean = level.map.attack(_character.id, direction)
    override fun addGameObjectNearGameAnotherObject(
        gameObjectId: SearchId,
        gameObjectForAddition: GameObject
    ): Boolean =
        level.map.addGameObjectNearGameAnotherObject(gameObjectId, gameObjectForAddition)
}
