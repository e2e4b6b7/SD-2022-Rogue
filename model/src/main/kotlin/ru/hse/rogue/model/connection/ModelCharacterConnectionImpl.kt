package ru.hse.rogue.model.connection

import ru.hse.rogue.model.gameobject.SearchId
import ru.hse.rogue.model.gameobject.character.*
import ru.hse.rogue.model.map.*


class ModelCharacterConnectionImpl internal constructor(
    private val gameMap: GameMap,
    private val _character: Character
) : ModelCharacterConnection {
    override val map get() = gameMap.mapElements
    override val character = _character
    override fun move(direction: Direction) = gameMap.move(_character.id, direction)
    override fun useInventory(inventoryId: SearchId) = _character.useInventory(inventoryId)
    override fun disableInventory(inventorId: SearchId) = _character.disableInventory(inventorId)
    override fun getSearchableWithPos(id: SearchId) = gameMap.searchObject(id)
    override fun attack(direction: Direction): Boolean = gameMap.attack(_character.id, direction)
}
