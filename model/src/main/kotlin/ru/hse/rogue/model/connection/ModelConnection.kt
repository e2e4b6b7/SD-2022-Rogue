package ru.hse.rogue.model.connection

import ru.hse.rogue.model.gameobject.*
import ru.hse.rogue.model.map.*

interface ModelViewConnection {
    fun getMap(): List<List<MapElement>>
}

interface ModelCharacterConnection: ModelViewConnection {
    val characterId: SearchId
    fun move(direction: Direction): Boolean
    fun useInventory(inventoryId: SearchId): Boolean
    fun getSearchableWithPos(id: SearchId): Pair<Searchable, Position>?
    fun attack(direction: Direction): Boolean
    fun attack(victimCharacterId: SearchId): Boolean
}

interface ModelScenarioControllerConnection: ModelViewConnection {
    fun getSearchableWithPos(id: SearchId): Pair<Searchable, Position>?
    fun addGameObject(gameObject: GameObject, position: Position): Boolean
}
