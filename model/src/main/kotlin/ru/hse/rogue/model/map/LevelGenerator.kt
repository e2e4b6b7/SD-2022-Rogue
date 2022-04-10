package ru.hse.rogue.model.map

import ru.hse.rogue.model.gameobject.FreeSpace
import ru.hse.rogue.model.gameobject.Wall
import ru.hse.rogue.model.gameobject.Character
import ru.hse.rogue.model.npc.NPC

object LevelGenerator {

    private const val MIN_ROOM_SIZE = 5
    private const val MAX_N_TRIES_TO_SPAWN_RANDOM = 3

    fun generateRandomLevel(enemies: Int, width: Int, height: Int): GameMap {
        val map = GameMap(width, height)
        buildWalls(map, enemies)
        return map
    }

    private fun spawnEnemies(widthLeft: Int, widthRight: Int,
                             heightLeft: Int, heightRight: Int, map: GameMap) {
        for (i in (0..MAX_N_TRIES_TO_SPAWN_RANDOM)) {
            val x = (widthLeft + 1 until  widthRight).random()
            val y = (heightLeft + 1 until  heightRight).random()
            if (map[x, y] is FreeSpace) {
                map[x, y] = Character( 100u)
            }
        }
    }

    private fun buildWalls(map: GameMap, enemiesToSpawn: Int) {
        val width = map.width
        val height = map.height
        buildInnerWalls(0, width - 1,
            0, height - 1,
            true, map, enemiesToSpawn)
        (0..width).forEach {x ->
            map[x, 0] = Wall
            map[x, height - 1] = Wall
        }
        (0..height).forEach {y ->
            map[0, y] = Wall
            map[width - 1, y] = Wall
        }
    }

    private fun needToSplit(width: Int, height: Int, splitVertically: Boolean, level: Int): Boolean {
        if (width < 2 * MIN_ROOM_SIZE + 2 && splitVertically ||
            height < 2 * MIN_ROOM_SIZE + 2 && !splitVertically) {
            return false
        }
        return (0 until level).random() % level != 0
    }

    private fun buildInnerWalls(widthLeft: Int, widthRight: Int,
                   heightLeft: Int, heightRight: Int,
                   splitVertically: Boolean, map: GameMap, enemiesToSpawn: Int, level: Int = 1) {
        if (!needToSplit(widthRight - widthLeft, heightRight - heightLeft, splitVertically, level)) {
            (0..enemiesToSpawn).forEach { _ ->
                spawnEnemies(widthLeft, widthRight, heightLeft, heightRight, map)
            }
            return
        }
        val enemiesToSpawnInFirst = (0..enemiesToSpawn).random()
        val enemiesToSpawnInSecond = enemiesToSpawn - enemiesToSpawnInFirst

        if (splitVertically) {
            val widthSplit = (widthLeft + 1 + MIN_ROOM_SIZE  until widthRight - MIN_ROOM_SIZE).random()
            (heightLeft  + 1 until heightRight).forEach {
                map[widthSplit, it] = Wall
            }
            buildInnerWalls(
                widthLeft, widthSplit,
                heightLeft, heightRight,
                false, map, enemiesToSpawnInFirst,
                level + 1
            )
            buildInnerWalls(
                widthSplit, widthRight,
                heightLeft, heightRight,
                false, map, enemiesToSpawnInSecond,
                level + 1
            )
        }
        else {
            val heightSplit = (heightLeft + 1 + MIN_ROOM_SIZE  until heightRight - MIN_ROOM_SIZE).random()
            (widthLeft  + 1 until widthRight).forEach {
                map[it, heightSplit] = Wall
            }
            buildInnerWalls(
                widthLeft, widthRight,
                heightLeft, heightSplit,
                true, map, enemiesToSpawnInFirst,
                level + 1
            )
            buildInnerWalls(
                widthLeft, widthRight,
                heightSplit, heightRight,
                true, map, enemiesToSpawnInSecond,
                level + 1
            )
        }
    }

}
