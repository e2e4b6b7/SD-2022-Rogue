package ru.hse.rogue.model.connection

import ru.hse.rogue.model.gameobject.*
import ru.hse.rogue.model.gameobject.character.Character
import ru.hse.rogue.model.map.*

interface ModelViewConnection {
    val map: List<List<MapElement>>
}

interface ModelCharacterConnection : ModelViewConnection {
    // TODO make immutable
    val character: Character
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
