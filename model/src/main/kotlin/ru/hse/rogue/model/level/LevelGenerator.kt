package ru.hse.rogue.model.level

import ru.hse.rogue.model.gameobject.*
import ru.hse.rogue.model.gameobject.character.CharacterImpl
import ru.hse.rogue.model.gameobject.character.ImmutableCharacter
import ru.hse.rogue.model.map.GameMap
import ru.hse.rogue.model.npc.behaviour.AggressiveStupidHunter
import ru.hse.rogue.model.npc.behaviour.Behaviour

/** Class for random generation of levels */
object LevelGenerator {

    private const val MIN_ROOM_SIZE = 5
    private const val MAX_N_TRIES_TO_SPAWN_RANDOM = 10
    private val RANGE_HEALTH = (10u..40u)
    private const val PLAYER_HEALTH = 100u

    /** Generate level with no more than [enemiesCount] enemies and map with sizes ([width], [height]) */
    fun generateRandomLevel(enemiesCount: Int, healthBonusCount: Int, width: Int, height: Int): Level {
        val map = GameMap(width, height)
        val player = CharacterImpl(PLAYER_HEALTH)
        val level = Level(map, player)
        buildLevel(level, enemiesCount)
        addSearchableToMap(level, player)
        (0 until healthBonusCount).forEach { _ ->
            addSearchableToMap(level, ExtraHealth(5))
        }
        addSearchableToMap(level, Cloth("Chain Mail Armor", 50, "Chain Mail Armor"))
        (0 until  2).forEach { _ ->
            addSearchableToMap(level, generateWeapon())
        }
        return level
    }

    private fun generateWeapon(): Arm {
        return listOf(
            Arm("Axe", 5, "Axe"),
            Arm("Sword", 7, "Sword"),
            Arm("Dagger", 3, "Dagger")
        ).random()
    }

    private fun generateCharacter(player: ImmutableCharacter): Pair<CharacterImpl, Behaviour> {
        val character = CharacterImpl(RANGE_HEALTH.random())
        val behaviour = Behaviour.createRandomBehaviour(player)
        if (behaviour is AggressiveStupidHunter) {
            val weapon = generateWeapon()
            character.pickInventory(weapon)
            character.useInventory(weapon.id)
        }
        return Pair(character, behaviour)
    }

    private fun tryAddCharacter(x: Int, y: Int, level: Level): Boolean {
        if (level.map[x, y].last() is FreeSpace) {
            val (character, behaviour) = generateCharacter(level.player)
            level.map[x, y] = character
            level.NPCCharacters.add(Pair(character, behaviour))
            return true
        }
        return false
    }

    private fun addSearchableToMap(level: Level, searchable: Searchable) {
        for (i in (0..MAX_N_TRIES_TO_SPAWN_RANDOM)) {
            val x = (1 until level.map.width).random()
            val y = (1 until level.map.height).random()
            if (level.map[x, y].last() is FreeSpace) {
                level.map[x, y] = searchable
                return
            }
        }
        for (x in (1 until level.map.width)) {
            for (y in (1 until level.map.height)) {
                if (level.map[x, y].last() is FreeSpace) {
                    level.map[x, y] = searchable
                    return
                }
            }
        }
    }

    private fun spawnEnemies(
        widthLeft: Int, widthRight: Int,
        heightLeft: Int, heightRight: Int, level: Level
    ) {
        for (i in (0..MAX_N_TRIES_TO_SPAWN_RANDOM)) {
            val x = (widthLeft + 1 until widthRight).random()
            val y = (heightLeft + 1 until heightRight).random()
            if (tryAddCharacter(x, y, level)) {
                return
            }
        }
        for (x in widthLeft + 1 until widthRight) {
            for (y in heightLeft + 1 until heightRight) {
                if (tryAddCharacter(x, y, level)) {
                    return
                }
            }
        }
    }

    private fun buildLevel(level: Level, enemiesToSpawn: Int) {
        val map = level.map
        val width = map.width
        val height = map.height
        buildInnerWalls(
            0, width - 1,
            0, height - 1,
            true, level, enemiesToSpawn
        )
        (0 until width).forEach { x ->
            map[x, 0] = Wall
            map[x, height - 1] = Wall
        }
        (0 until height).forEach { y ->
            map[0, y] = Wall
            map[width - 1, y] = Wall
        }
    }

    private fun needToSplit(width: Int, height: Int, splitVertically: Boolean, level: Int): Boolean {
        if (width < 2 * MIN_ROOM_SIZE + 2 && splitVertically ||
            height < 2 * MIN_ROOM_SIZE + 2 && !splitVertically
        ) {
            return false
        }
        return if (level > 5)
            (0 until level).random() % level == 0
        else true
    }

    private fun buildInnerWalls(
        widthLeft: Int, widthRight: Int,
        heightLeft: Int, heightRight: Int,
        splitVertically: Boolean, level: Level, enemiesToSpawn: Int,
        recursionDepth: Int = 1
    ) {
        if (!needToSplit(
                widthRight - widthLeft,
                heightRight - heightLeft,
                splitVertically, recursionDepth
            )
        ) {
            (0 until enemiesToSpawn).forEach { _ ->
                spawnEnemies(widthLeft, widthRight, heightLeft, heightRight, level)
            }
            return
        }
        val enemiesToSpawnInFirst = (0..enemiesToSpawn).random()
        val enemiesToSpawnInSecond = enemiesToSpawn - enemiesToSpawnInFirst

        if (splitVertically) {
            val widthSplit = (widthLeft + 1 + MIN_ROOM_SIZE until widthRight - MIN_ROOM_SIZE).random()
            (heightLeft + 1 until heightRight).forEach {
                level.map[widthSplit, it] = Wall
            }
            buildInnerWalls(
                widthLeft, widthSplit,
                heightLeft, heightRight,
                false, level, enemiesToSpawnInFirst,
                recursionDepth + 1
            )
            buildInnerWalls(
                widthSplit, widthRight,
                heightLeft, heightRight,
                false, level, enemiesToSpawnInSecond,
                recursionDepth + 1
            )
            for (heightCurrent in (heightLeft + 1 until heightRight)) {
                if (level.map[widthSplit - 1, heightCurrent].last() is FreeSpace &&
                    level.map[widthSplit + 1, heightCurrent].last() is FreeSpace
                ) {
                    level.map.pop(widthSplit, heightCurrent)
                    break
                }
            }
        } else {
            val heightSplit = (heightLeft + 1 + MIN_ROOM_SIZE until heightRight - MIN_ROOM_SIZE).random()
            (widthLeft + 1 until widthRight).forEach {
                level.map[it, heightSplit] = Wall
            }
            buildInnerWalls(
                widthLeft, widthRight,
                heightLeft, heightSplit,
                true, level, enemiesToSpawnInFirst,
                recursionDepth + 1
            )
            buildInnerWalls(
                widthLeft, widthRight,
                heightSplit, heightRight,
                true, level, enemiesToSpawnInSecond,
                recursionDepth + 1
            )
            for (widthCurrent in (widthLeft + 1 until widthRight)) {
                if (level.map[widthCurrent, heightSplit - 1].last() is FreeSpace &&
                    level.map[widthCurrent, heightSplit + 1].last() is FreeSpace
                ) {
                    level.map.pop(widthCurrent, heightSplit)
                    break
                }
            }
        }
    }
}
