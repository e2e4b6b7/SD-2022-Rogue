package ru.hse.rogue.model.map

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import ru.hse.rogue.model.gameobject.*
import ru.hse.rogue.model.gameobject.character.*
import kotlin.test.*

internal class GameMapTest {
    private lateinit var gameMap: GameMap
    private lateinit var char: Character
    private lateinit var victimChar: Character
    private val startHealth = 10u

    @BeforeTest
    fun testInit() {
        gameMap = GameMap(3, 3)
        char = CharacterImpl(startHealth)
        victimChar = CharacterImpl(startHealth)
    }

    @Test
    fun `get test`() {
        assertEquals(1, gameMap[0, 0].size)
        assertEquals(FreeSpace, gameMap[0, 0].last())
    }

    @Test
    fun `set and pop test`() {
        gameMap[0, 0] = Wall
        assertEquals(2, gameMap[0, 0].size)
        assertEquals(FreeSpace, gameMap[0, 0][0])
        assertEquals(Wall, gameMap[0, 0][1])
        gameMap.pop(0, 0)
        assertEquals(1, gameMap[0, 0].size)
        assertEquals(FreeSpace, gameMap[0, 0].last())
    }

    @Test
    fun `search object which is on map `() {
        val extraHealth = ExtraHealth(1)
        gameMap[0, 0] = extraHealth
        val objAndPos = gameMap.searchObject(extraHealth.id)
        assertNotNull(objAndPos)
        assertEquals(extraHealth.id, objAndPos.first.id)
        assertEquals(Position(0, 0), objAndPos.second)
    }

    @Test
    fun `search object which isn't on map `() {
        assertNull(gameMap.searchObject(SearchId.randomUUID()))
    }

    @Test
    fun `attack by searchable, but not character`() {
        val extraHealth = ExtraHealth(1)
        gameMap[0, 0] = extraHealth
        assertThrows<Exception> { gameMap.attack(extraHealth.id, Direction.RIGHT) }
    }

    @Test
    fun `attack by not exist character`() {
        assertFalse(gameMap.attack(SearchId.randomUUID(), Direction.RIGHT))
    }

    @Test
    fun `attack out of map bounds`() {
        gameMap[0, 0] = char
        assertFalse(gameMap.attack(char.id, Direction.LEFT))
    }

    @Test
    fun `attack not character`() {
        gameMap[0, 0] = char
        assertFalse(gameMap.attack(char.id, Direction.RIGHT))
    }

    @Test
    fun `attack character and not kill`() {
        val arm = Arm("stick", startHealth.toInt() - 1, "")
        char.pickInventory(arm)
        char.useInventory(arm.id)
        gameMap[0, 0] = char
        gameMap[1, 0] = victimChar
        assertTrue(gameMap.attack(char.id, Direction.RIGHT))
        assertTrue(victimChar.isAlive())
    }

    @Test
    fun `attack character and kill`() {
        val arm = Arm("stick", startHealth.toInt() + 1, "")
        char.pickInventory(arm)
        char.useInventory(arm.id)
        gameMap[0, 0] = char
        gameMap[1, 0] = victimChar
        assertTrue(gameMap.attack(char.id, Direction.RIGHT))
        assertFalse(victimChar.isAlive())
        assertNull(gameMap.searchObject(victimChar.id))
    }
}
