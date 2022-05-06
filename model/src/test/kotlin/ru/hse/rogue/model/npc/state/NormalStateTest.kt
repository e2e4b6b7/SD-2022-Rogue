package ru.hse.rogue.model.npc.state

import org.junit.jupiter.api.Test
import ru.hse.rogue.model.connection.ModelCharacterConnection
import ru.hse.rogue.model.connection.ModelCharacterConnectionImpl
import ru.hse.rogue.model.gameobject.character.Character
import ru.hse.rogue.model.gameobject.character.CharacterImpl
import ru.hse.rogue.model.level.Level
import ru.hse.rogue.model.level.Mob
import ru.hse.rogue.model.map.GameMap
import ru.hse.rogue.model.npc.behaviour.*
import kotlin.test.*

class NormalStateTest {
    private lateinit var modelConnection: ModelCharacterConnection
    private lateinit var gameMap: GameMap
    private lateinit var level: Level
    private lateinit var char: Character
    private lateinit var mob: Mob
    private lateinit var playerChar: Character
    private lateinit var startBehaviour: Behaviour
    private val startHealth = 10u

    @BeforeTest
    fun testInit() {
        gameMap = GameMap(3, 3)
        char = CharacterImpl(startHealth)
        playerChar = CharacterImpl(startHealth)
        startBehaviour = FriendlyStander
        mob = Mob(char, startBehaviour, NormalState(State.DEFAULT_HEALTH_THRESH_HOLD))
        level = Level(
            gameMap, playerChar, mutableListOf(mob)
        )
        modelConnection = ModelCharacterConnectionImpl(level, char)
    }

    @Test
    fun `check behaviour change after nothing happened`() {
        gameMap[0, 0] = char
        repeat(10) {
            mob.state.changeState(mob, playerChar)
            mob.behaviour.doAnything(modelConnection)
            assertEquals(mob.behaviour, startBehaviour)
        }
    }

    @Test
    fun `check behaviour change after health decrease`() {
        gameMap[0, 0] = char
        assertEquals(mob.behaviour, startBehaviour)
        mob.character.healthDecrease(startHealth - (State.DEFAULT_HEALTH_THRESH_HOLD - 1u))
        repeat(10) {
            mob.state.changeState(mob, playerChar)
            mob.behaviour.doAnything(modelConnection)
            assertTrue(mob.behaviour is CowardWalker)
        }
    }
}
