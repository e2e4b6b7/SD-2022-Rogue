package ru.hse.rogue.model

import ru.hse.rogue.model.gameobject.*
import ru.hse.rogue.model.map.*

interface ModelConnection {
    fun getMap(): List<List<MapElement>>
    fun addGameObject(gameObject: GameObject, position: Position): Boolean

    fun moveCharacter(characterId: SearchId, direction: Direction): Boolean
    fun useArm(characterId: SearchId, armOfCharacterIndex: Int): Boolean
    fun getSearchableWithPos(id: SearchId): Pair<Searchable, Position>?
    fun attack(attackingCharacterId: SearchId, victimCharacterId: SearchId): Boolean
    fun wear(characterId: SearchId, clothOfCharacterIndex: Int): Boolean
}
