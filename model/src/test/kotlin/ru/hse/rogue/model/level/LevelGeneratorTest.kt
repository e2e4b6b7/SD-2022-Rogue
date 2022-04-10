package ru.hse.rogue.model.level

import org.junit.jupiter.api.Test
import ru.hse.rogue.model.gameobject.Wall
import ru.hse.rogue.model.gameobject.character.Character

internal class LevelGeneratorTest {

    private fun printMapToConsole(level: Level) {
        val map = level.map
        (0 until map.height).forEach { y ->
            (0 until map.width).forEach { x ->
                if (map[x, y].last() is Wall) {
                    print('#')
                }
                else if (map[x, y].last() is Character) {
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
    fun testGen() {
        val level = LevelGenerator.generateRandomLevel(1, 15, 15)
        printMapToConsole(level)
    }

}
