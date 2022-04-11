package ru.hse.rogue.model.connection

import ru.hse.rogue.model.gameobject.SearchId
import ru.hse.rogue.model.gameobject.character.Character
import ru.hse.rogue.model.map.*


class ModelCharacterConnectionImpl internal constructor(
    private val gameMap: GameMap,
    override val character: Character
) : ModelCharacterConnection {
    override val map: List<List<MapElement>> get() = gameMap.mapElements
    override fun move(direction: Direction) = gameMap.move(character.id, direction)
    override fun useInventory(inventoryId: SearchId) = gameMap.useInventory(character.id, inventoryId)
    override fun getSearchableWithPos(id: SearchId) = gameMap.searchObject(id)
    override fun attack(direction: Direction): Boolean = gameMap.attack(character.id, direction)
}
