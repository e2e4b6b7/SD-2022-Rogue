package ru.hse.rogue.model.npc

import ru.hse.rogue.model.ModelConnection
import ru.hse.rogue.model.gameobject.Arm
import ru.hse.rogue.model.map.Position
import ru.hse.rogue.model.npc.behaviour.Behaviour
import ru.hse.rogue.model.gameobject.character.Character
import ru.hse.rogue.model.utils.SleepingTimer

class NPC(private val modelConnection: ModelConnection,
          private val behaviour: Behaviour,
          startPosition: Position,
          maxHealth: UInt = 100u,
          arm: Arm? = null,
          sleepTimeInMs: Long = 30) {
    private val character = Character(maxHealth)
    private val sleepingTimer = SleepingTimer(sleepTimeInMs)
    private val isCharacterAddedToMap: Boolean
    init {
        if (arm != null)
            character.pick(arm)
        isCharacterAddedToMap = modelConnection.addGameObject(character, startPosition)
    }
    fun run() {
        if (isCharacterAddedToMap) {
            while (character.isAlive()) {
                behaviour.doAnything(modelConnection, character)
                sleepingTimer.await()
            }
        }
    }
}
