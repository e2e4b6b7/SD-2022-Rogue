package ru.hse.rogue.model

import ru.hse.rogue.model.connection.*
import ru.hse.rogue.model.gameobject.*
import ru.hse.rogue.model.gameobject.character.Character
import ru.hse.rogue.model.level.Level
import ru.hse.rogue.model.map.*
import ru.hse.rogue.model.npc.NPC
import ru.hse.rogue.model.npc.behaviour.AggressiveRandomWalker
import ru.hse.rogue.model.utils.isInBounds
import kotlin.concurrent.thread


class Game(private val level: Level) {
    val modelViewConnection: ModelViewConnection = ModelViewConnectionImpl(this)
    val playerCharacterModelConnection: ModelCharacterConnection =
        ModelCharacterConnectionImpl(this, level.player.id)
    val map: List<List<MapElement>>
        @Synchronized
        get() = level.map.mapElementsArray.asList().map { it.asList() }

    init {
        level.NPCCharacters.forEach {
            // FIX BEHAVIOUR
            thread(start = true, isDaemon = true){
                NPC(ModelCharacterConnectionImpl(this, it.id), AggressiveRandomWalker)
            }
        }
    }

    @Synchronized
    fun addGameObject(gameObject: GameObject, position: Position): Boolean {
        if (position.isInBounds(level.map.width, level.map.height))
            throw IndexOutOfBoundsException("Position coordinates out of map bounds")
        if (level.map[position].last() !is Character) {
            level.map[position] = gameObject
            return true
        }
        return false
    }

    @Synchronized
    fun moveCharacter(characterId: SearchId, direction: Direction): Boolean {
        val character = level.map.searchObject(characterId)?.first ?: return false
        if (character !is Character)
            return false
        val curPos = level.map.searchObject(characterId)?.second ?: return false
        val newPos = when (direction) {
            Direction.LEFT -> Position(curPos.x - 1, curPos.y)
            Direction.RIGHT -> Position(curPos.x + 1, curPos.y)
            Direction.UP -> Position(curPos.x, curPos.y + 1)
            Direction.DOWN -> Position(curPos.x, curPos.y - 1)
        }
        if (!newPos.isInBounds(level.map.width, level.map.height))
            return false

        when (val curObjectInNewPos = level.map[newPos].last()) {
            is Character -> return false
            is Wall -> return false
            is Inventory -> {
                character.pickInventory(curObjectInNewPos)
                level.map.pop(newPos)
            }
        }

        level.map[newPos] = level.map.pop(curPos)
        return true
    }

    @Synchronized
    fun useInventory(characterId: SearchId, inventoryId: SearchId): Boolean {
        val character = level.map.searchObject(characterId)?.first ?: return false
        if (character !is Character)
            return false
        character.useInventory()
        return true
    }
}
