package ru.hse.rogue.model.level.builder

import ru.hse.rogue.model.gameobject.*
import ru.hse.rogue.model.gameobject.character.CharacterImpl
import ru.hse.rogue.model.level.*
import ru.hse.rogue.model.level.itemfactory.AbstractInventoryFactory
import ru.hse.rogue.model.level.mobfactory.AbstractMobFactory
import ru.hse.rogue.model.map.GameMap

class RandomLevelBuilder : LevelBuilder {
    companion object {
        private const val MIN_ROOM_SIZE = 5
        private const val MAX_N_TRIES_TO_SPAWN_RANDOM = 10
        private const val PLAYER_HEALTH = 100u
    }

    private lateinit var mobFactory: AbstractMobFactory
    private lateinit var inventoryFactory: AbstractInventoryFactory
    private var width: Int? = null
    private var height: Int? = null
    private var numOfEnemies: Int = 3
    private var numOfExtraHealth: Int = 6
    private var numOfCloths: Int = 3
    private var numOfArms: Int = 3

    fun setWidth(w: Int): RandomLevelBuilder {
        width = w
        return this
    }

    fun setHeight(h: Int): RandomLevelBuilder {
        height = h
        return this
    }

    fun setNumOfEnemies(enemies: Int): RandomLevelBuilder {
        numOfEnemies = enemies
        return this
    }

    fun setNumOfExtraHealth(extraHealths: Int): RandomLevelBuilder {
        numOfExtraHealth = extraHealths
        return this
    }

    fun setNumOfCloths(cloths: Int): RandomLevelBuilder {
        numOfCloths = cloths
        return this
    }

    fun setNumOfArms(arms: Int): RandomLevelBuilder {
        numOfArms = arms
        return this
    }

    override fun setMobFactory(factory: AbstractMobFactory): LevelBuilder {
        mobFactory = factory
        return this
    }

    override fun setInventoryFactory(factory: AbstractInventoryFactory): LevelBuilder {
        inventoryFactory = factory
        return this
    }

    override fun build(): Level {
        require(this::mobFactory.isInitialized) { "mob factory must be set" }
        require(this::inventoryFactory.isInitialized) { "inventory factory must be set" }

        val map = GameMap(
            requireNotNull(width) { "width must be set" },
            requireNotNull(height) { "height must be set" }
        )

        val player = CharacterImpl(PLAYER_HEALTH)
        val level = Level(map, player)
        buildRawLevel(level, numOfEnemies)

        addSearchableToMap(level, player)
        (0 until numOfExtraHealth).forEach {
            addSearchableToMap(level, inventoryFactory.createExtraHealth())
        }
        (0 until numOfArms).forEach {
            addSearchableToMap(level, inventoryFactory.createArm())
        }
        (0 until numOfCloths).forEach {
            addSearchableToMap(level, inventoryFactory.createCloth())
        }

        return level
    }

    private fun tryAddMob(x: Int, y: Int, level: Level, mob: Mob): Boolean {
        if (level.map[x, y].last() is FreeSpace) {
            level.map[x, y] = mob.character
            level.mobs.add(mob)
            return true
        }
        return false
    }

    private fun spawnMobs(
        widthLeft: Int, widthRight: Int,
        heightLeft: Int, heightRight: Int, level: Level
    ) {
        val mob = mobFactory.createRandomMob(level.player)
        for (i in (0..MAX_N_TRIES_TO_SPAWN_RANDOM)) {
            val x = (widthLeft + 1 until widthRight).random()
            val y = (heightLeft + 1 until heightRight).random()
            if (tryAddMob(x, y, level, mob)) {
                return
            }
        }
        for (x in widthLeft + 1 until widthRight) {
            for (y in heightLeft + 1 until heightRight) {
                if (tryAddMob(x, y, level, mob)) {
                    return
                }
            }
        }
    }

    private fun buildRawLevel(level: Level, enemiesToSpawn: Int) {
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
                spawnMobs(widthLeft, widthRight, heightLeft, heightRight, level)
            }
            return
        }
        val enemiesToSpawnInFirst = (0..enemiesToSpawn).random()
        val enemiesToSpawnInSecond = enemiesToSpawn - enemiesToSpawnInFirst

        if (splitVertically) {
            val widthSplit =
                (widthLeft + 1 + MIN_ROOM_SIZE until widthRight - MIN_ROOM_SIZE).random()
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
            val heightSplit =
                (heightLeft + 1 + MIN_ROOM_SIZE until heightRight - MIN_ROOM_SIZE).random()
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
}
