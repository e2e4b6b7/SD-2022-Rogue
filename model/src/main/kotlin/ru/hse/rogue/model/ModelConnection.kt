package ru.hse.rogue.model

import ru.hse.rogue.model.gameobject.*
import ru.hse.rogue.model.gameobject.Character
import ru.hse.rogue.model.map.*

interface ModelConnection {
    fun getMap(): Array<Array<MapElement>>
    fun addGameObject(gameObject: GameObject, position: Position): Boolean

    fun tryMoveCharacter(characterId: SearchId, direction: Direction): Boolean
    fun useArm(characterId: SearchId, armOfCharacterIndex: Int): Boolean
    fun getSearchablePos(id: SearchId): Position?
    fun tryAttack(attackingCharacterId: SearchId, victimCharacterId: SearchId): Boolean
    fun wear(characterId: SearchId, clothOfCharacterIndex: Int): Boolean

    fun getPotentialCharactersForAttack(position: Position): List<SearchId>
}
