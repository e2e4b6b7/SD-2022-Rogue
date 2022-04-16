package ru.hse.rogue.model.npc.behaviour

import ru.hse.rogue.model.connection.ModelCharacterConnection
import ru.hse.rogue.model.connection.ModelCharacterConnectionImpl
import ru.hse.rogue.model.gameobject.Arm
import ru.hse.rogue.model.gameobject.character.Character
import ru.hse.rogue.model.gameobject.character.CharacterImpl
import ru.hse.rogue.model.map.GameMap
import ru.hse.rogue.model.map.Position
import kotlin.test.*

class AggressiveStupidHunterTest : BehaviourTest {
    private lateinit var modelConnection: ModelCharacterConnection
    private lateinit var gameMap: GameMap
    private lateinit var char: Character
    private lateinit var playerChar: Character
    private lateinit var behaviour: Behaviour
    private val startHealth = 10u

    @BeforeTest
    fun testInit() {
        gameMap = GameMap(11, 11)
        char = CharacterImpl(startHealth)
        playerChar = CharacterImpl(startHealth)
        modelConnection = ModelCharacterConnectionImpl(gameMap, char)
        behaviour = AggressiveStupidHunter(playerChar, 9)
    }

    @Test
    override fun `nobody around`() {
        gameMap[5, 5] = char
        repeat(10) {
            behaviour.doAnything(modelConnection)
            assertEquals(Position(5, 5), gameMap.searchObject(char.id)?.second)
        }
    }

    @Test
    override fun `player far`() {
        gameMap[5, 5] = char
        gameMap[10, 10] = playerChar
        repeat(10) {
            behaviour.doAnything(modelConnection)
            assertEquals(Position(5, 5), gameMap.searchObject(char.id)?.second)
        }
    }

    @Test
    override fun `player near not in tight`() {
        gameMap[5, 5] = char
        gameMap[9, 9] = playerChar
        behaviour.doAnything(modelConnection)
        assertTrue(
            Position(6, 5) == gameMap.searchObject(char.id)?.second ||
                Position(5, 6) == gameMap.searchObject(char.id)?.second
        )
    }

    @Test
    override fun `player near in tight`() {
        gameMap[5, 5] = char
        val arm = Arm("", startHealth.toInt() + 1, "")
        char.pickInventory(arm)
        char.useInventory(arm.id)
        gameMap[5, 6] = playerChar
        behaviour.doAnything(modelConnection)
        assertFalse(playerChar.isAlive())
    }
}
