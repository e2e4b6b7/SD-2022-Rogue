package ru.hse.rogue.model.npc.behaviour

import org.junit.jupiter.api.Test
import ru.hse.rogue.model.connection.ModelCharacterConnection
import ru.hse.rogue.model.connection.ModelCharacterConnectionImpl
import ru.hse.rogue.model.gameobject.character.Character
import ru.hse.rogue.model.gameobject.character.CharacterImpl
import ru.hse.rogue.model.map.GameMap
import ru.hse.rogue.model.map.Position
import kotlin.test.BeforeTest
import kotlin.test.assertEquals

internal class FriendlyStanderTest : BehaviourTest {
    private lateinit var modelConnection: ModelCharacterConnection
    private lateinit var gameMap: GameMap
    private lateinit var char: Character
    private lateinit var playerChar: Character
    private lateinit var behaviour: Behaviour
    private val startHealth = 10u

    @BeforeTest
    fun testInit() {
        gameMap = GameMap(3, 3)
        char = CharacterImpl(startHealth)
        playerChar = CharacterImpl(startHealth)
        modelConnection = ModelCharacterConnectionImpl(gameMap, char)
        behaviour = FriendlyStander
    }

    @Test
    override fun `nobody around`() {
        gameMap[0, 0] = char
        repeat(10) {
            behaviour.doAnything(modelConnection)
            assertEquals(Position(0, 0), gameMap.searchObject(char.id)?.second)
        }
    }

    @Test
    override fun `player far`() {
        gameMap[0, 0] = char
        gameMap[2, 2] = playerChar
        repeat(10) {
            behaviour.doAnything(modelConnection)
            assertEquals(Position(0, 0), gameMap.searchObject(char.id)?.second)
        }
    }

    @Test
    override fun `player near not in tight`() {
        gameMap[0, 0] = char
        gameMap[1, 1] = playerChar
        repeat(10) {
            behaviour.doAnything(modelConnection)
            assertEquals(Position(0, 0), gameMap.searchObject(char.id)?.second)
        }
    }

    @Test
    override fun `player near in tight`() {
        gameMap[0, 0] = char
        gameMap[1, 0] = playerChar
        repeat(10) {
            behaviour.doAnything(modelConnection)
            assertEquals(Position(0, 0), gameMap.searchObject(char.id)?.second)
        }
    }

}
