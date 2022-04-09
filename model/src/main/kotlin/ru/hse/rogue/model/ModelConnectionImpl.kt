package ru.hse.rogue.model

import ru.hse.rogue.model.gameobject.*
import ru.hse.rogue.model.map.*


class ModelConnectionImpl internal constructor(private val gameMap: GameMap) : ModelConnection {
    @Synchronized
    override fun getMap(): Array<Array<MapElement>> = gameMap.mapElementsArray

    @Synchronized
    override fun addGameObject(gameObject: GameObject, position: Position): Boolean {
        if (position.isInBounds(gameMap.width, gameMap.height))
            throw IndexOutOfBoundsException("Position coordinates out of map bounds")
        if (gameMap[position] !is Character) {
            gameMap[position] = gameObject
            return true
        }
        return false
    }

    @Synchronized
    override fun tryMoveCharacter(characterId: SearchId, direction: Direction): Boolean {
        val character = gameMap.searchObject(characterId)?.first ?: return false
        if (character !is Character)
            return false
        val curPos = getSearchablePos(characterId) ?: return false
        val newPos = when (direction) {
                Direction.LEFT -> Position(curPos.x - 1, curPos.y)
                Direction.RIGHT -> Position(curPos.x + 1, curPos.y)
                Direction.UP -> Position(curPos.x, curPos.y + 1)
                Direction.DOWN -> Position(curPos.x, curPos.y - 1)
            }
        if (!newPos.isInBounds(gameMap.width, gameMap.height))
            return false

        when (val curObjectInNewPos = gameMap[newPos]) {
            is Character -> return false
            is Wall -> return false
            is Pickable -> {
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
    override fun getSearchablePos(id: SearchId): Position? = gameMap.searchObject(id)?.second

    @Synchronized
    override fun tryAttack(attackingCharacterId: SearchId, victimCharacterId: SearchId): Boolean {
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

    @Synchronized
    override fun getPotentialCharactersForAttack(position: Position): List<SearchId> {
        val map = gameMap.mapElementsArray
        if (map.isEmpty() || map[0].isEmpty())
            throw RuntimeException("Game map is empty")
        val res = mutableListOf<SearchId>()
        for ((x, y) in listOf(Pair(position.x - 1, 0), Pair(position.x + 1, 0),
            Pair(0, position.y + 1), Pair(0, position.y - 1)
        )) {
            if (!Position(x, y).isInBounds(map[0].size, map.size)) {
                val gameObject = map[y][x].peek()
                if (gameObject is Character) {
                    res.add(gameObject.getId())
                }
            }
        }
        return res
    }
}
