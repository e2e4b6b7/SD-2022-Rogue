package ru.hse.rogue.model.connection

import ru.hse.rogue.model.gameobject.*
import ru.hse.rogue.model.gameobject.character.ImmutableCharacter
import ru.hse.rogue.model.map.*
import ru.hse.rogue.model.utils.Immutable2DArray

interface ModelViewConnection {
    val map: Immutable2DArray<out MapElement>
}

interface ModelCharacterConnection : ModelViewConnection {
    val character: ImmutableCharacter
    fun move(direction: Direction): Boolean
    fun useInventory(inventoryId: SearchId): Boolean
    fun disableInventory(inventorId: SearchId): Boolean
    fun getSearchableWithPos(id: SearchId): Pair<Searchable, Position>?
    fun attack(direction: Direction): Boolean
}

interface ModelScenarioControllerConnection : ModelViewConnection {
    fun getSearchableWithPos(id: SearchId): Pair<Searchable, Position>?
    fun addGameObject(gameObject: GameObject, position: Position): Boolean
}
