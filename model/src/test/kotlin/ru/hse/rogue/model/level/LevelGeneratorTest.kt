package ru.hse.rogue.model.level

import org.junit.jupiter.api.Test
import ru.hse.rogue.model.gameobject.Wall
import ru.hse.rogue.model.gameobject.character.Character

class LevelGeneratorTest {

    private fun printMapToConsole(level: Level) {
        val map = level.map
        (0 until map.height).forEach { y ->
            (0 until map.width).forEach { x ->
                if (map[x, y].last() is Wall) {
                    print('#')
                }
                if (map[x, y].last() is Character) {
                    print('*')
                }
            }
            println()
        }
    }

    @Test
    private fun testGen() {
        val level = LevelGenerator.generateRandomLevel(1, 10, 10)
        printMapToConsole(level)
    }

}

fun main() {
    val level = LevelGenerator.generateRandomLevel(1, 10, 10)
    val test = LevelGeneratorTest()
    test.printMapToConsole(level)
}
