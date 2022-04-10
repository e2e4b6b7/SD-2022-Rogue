package ru.hse.rogue.model.connection

import ru.hse.rogue.model.gameobject.*
import ru.hse.rogue.model.map.*
import ru.hse.rogue.model.utils.isInBounds
import ru.hse.rogue.model.gameobject.character.Character


class ModelConnectionImpl internal constructor(private val gameMap: GameMap) {
    @Synchronized
    fun moveCharacter(characterId: SearchId, direction: Direction): Boolean {
        val character = gameMap.searchObject(characterId)?.first ?: return false
        if (character !is Character)
            return false
        val curPos = getSearchableWithPos(characterId)?.second ?: return false
        val newPos = when (direction) {
                Direction.LEFT -> Position(curPos.x - 1, curPos.y)
                Direction.RIGHT -> Position(curPos.x + 1, curPos.y)
                Direction.UP -> Position(curPos.x, curPos.y + 1)
                Direction.DOWN -> Position(curPos.x, curPos.y - 1)
            }
        if (!newPos.isInBounds(gameMap.width, gameMap.height))
            return false

        when (val curObjectInNewPos = gameMap[newPos].last()) {
            is Character -> return false
            is Wall -> return false
            is Inventory -> {
                character.pick(curObjectInNewPos)
                gameMap.pop(newPos)
            }
        }

        gameMap[newPos] = gameMap.pop(curPos)
        return true
    }

    override fun useArm(characterId: SearchId, armOfCharacterIndex: Int): Boolean {
        val character = gameMap.searchObject(characterId)?.first ?: return false
        if (character !is Character)
            return false
        if (armOfCharacterIndex !in (0..character.arms.size)) {
            throw RuntimeException("Try to use arm by index, that out of arm list bound")
        }
        character.useArm(armOfCharacterIndex)
        return true
    }

    @Synchronized
    override fun getSearchableWithPos(id: SearchId): Pair<Searchable, Position>? = gameMap.searchObject(id)

    @Synchronized
    override fun attack(attackingCharacterId: SearchId, victimCharacterId: SearchId): Boolean {
        val attackingCharacter = gameMap.searchObject(attackingCharacterId)?.first ?: return false
        val victimCharacter = gameMap.searchObject(victimCharacterId)?.first ?: return false
        if (attackingCharacter !is Character || victimCharacter !is Character) {
            return false
        }
        attackingCharacter.attack(victimCharacter)
        return true
    }

    override fun wear(characterId: SearchId, clothOfCharacterIndex: Int): Boolean {
        val character = gameMap.searchObject(characterId)?.first ?: return false
        if (character !is Character)
            return false
        if (clothOfCharacterIndex !in (0..character.clothes.size)) {
            throw RuntimeException("Try to use cloth by index, that out of cloth list bound")
        }
        character.wear(clothOfCharacterIndex)
        return true
    }
}
