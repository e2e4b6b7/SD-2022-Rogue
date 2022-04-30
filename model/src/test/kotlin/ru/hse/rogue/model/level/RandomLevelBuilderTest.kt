package ru.hse.rogue.model.level

import org.junit.jupiter.api.Test
import ru.hse.rogue.model.gameobject.FreeSpace
import ru.hse.rogue.model.gameobject.Wall
import ru.hse.rogue.model.gameobject.character.CharacterImpl
import ru.hse.rogue.model.level.builder.RandomLevelBuilder
import ru.hse.rogue.model.level.inventoryfactory.MedievalInventoryFactory
import ru.hse.rogue.model.level.mobfactory.AnimalFactory
import ru.hse.rogue.model.map.Position
import kotlin.test.assertEquals

internal class RandomLevelBuilderTest {

    private val dts = listOf(Pair(1, 0), Pair(-1, 0), Pair(0, -1), Pair(0, 1))

    private fun dfs(pos: Position, visited: Array<Array<Boolean>>, level: Level): Int {
        if (pos.x < 0 || pos.x >= level.map.width ||
            pos.y < 0 || pos.y >= level.map.height ||
            visited[pos.y][pos.x] || level.map[pos].last() is Wall) {
            return 0
        }
        visited[pos.y][pos.x] = true
        var visitedEnemies = if (level.map[pos].last() is CharacterImpl) 1 else 0
        for (dt in dts) {
            visitedEnemies += dfs(Position(pos.x + dt.first, pos.y + dt.second), visited, level)
        }
        return visitedEnemies
    }

    private fun printMapToConsole(level: Level) {
        val map = level.map
        (0 until map.height).forEach { y ->
            (0 until map.width).forEach { x ->
                if (map[x, y].last() is Wall) {
                    print('#')
                }
                else if (map[x, y].last() is CharacterImpl) {
                    print('*')
                }
                else {
                    print(' ')
                }
            }
            println()
        }
    }

    @Test
    fun testGenerator() {
        (0..10).forEach { enemiesCount ->
            (20..30).forEach { width ->
                (20..30).forEach { height ->
                    val level = RandomLevelBuilder()
                        .setNumOfEnemies(enemiesCount)
                        .setNumOfExtraHealth(5)
                        .setWidth(width)
                        .setHeight(height)
                        .setMobFactory(AnimalFactory)
                        .setInventoryFactory(MedievalInventoryFactory)
                        .build()
                    val visited = Array(height) {
                        Array(width) {
                            false
                        }
                    }
                    var needToBreak = false
                    for (x in (1 until level.map.width)) {
                        for (y in (1 until level.map.height)) {
                            if (level.map[x, y].last() is FreeSpace) {
                                assertEquals(enemiesCount + 1, dfs(Position(x, y), visited, level))
                                needToBreak = true
                                break
                            }
                        }
                        if (needToBreak) {
                            break
                        }
                    }
                }
            }
        }
    }

}
