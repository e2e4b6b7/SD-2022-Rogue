package ru.hse.rogue.model.npc

import ru.hse.rogue.model.connection.ModelCharacterConnection
import ru.hse.rogue.model.npc.behaviour.Behaviour
import ru.hse.rogue.model.utils.SleepingTimer

/** Class for representing NPC with [behaviour]. It takes information about game from [ModelCharacterConnection]*/
class NPC(private val modelConnection: ModelCharacterConnection,
          private val behaviour: Behaviour,
          sleepTimeInMs: Long = 30) {
    private val sleepingTimer = SleepingTimer(sleepTimeInMs)
    fun run() {
        while (true) {
            if (modelConnection.getSearchableWithPos(modelConnection.characterId) != null)
                break
            behaviour.doAnything(modelConnection)
            sleepingTimer.await()
        }
    }
}
