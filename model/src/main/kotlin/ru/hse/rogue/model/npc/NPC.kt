package ru.hse.rogue.model.npc

import ru.hse.rogue.model.connection.ModelCharacterConnection
import ru.hse.rogue.model.npc.behaviour.Behaviour
import ru.hse.rogue.model.npc.behaviour.StunnedBehaviour
import ru.hse.rogue.model.utils.SleepingTimer

/** Class for representing NPC with [behaviour]. It takes information about game from [ModelCharacterConnection]*/
class NPC(
    private val modelConnection: ModelCharacterConnection,
    private var behaviour: Behaviour,
    sleepTimeMillis: Long = 1000
) {
    private val sleepingTimer = SleepingTimer(sleepTimeMillis)
    fun run() {
        while (modelConnection.character.isAlive()) {
            if (behaviour is StunnedBehaviour) {
                if (!(behaviour as StunnedBehaviour).isActive()) {
                    modelConnection.character.isStunned.set(false)
                }
            } else if (modelConnection.character.isStunned.get()) {
                behaviour = StunnedBehaviour(behaviour)
            }
            behaviour.doAnything(modelConnection)
            sleepingTimer.await()
        }
    }
}
