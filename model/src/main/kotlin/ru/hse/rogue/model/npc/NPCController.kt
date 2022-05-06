package ru.hse.rogue.model.npc

import ru.hse.rogue.model.connection.ModelCharacterConnection
import ru.hse.rogue.model.gameobject.character.Character
import ru.hse.rogue.model.level.Mob
import ru.hse.rogue.model.utils.SleepingTimer

/** Class for representing [npc]. It takes information about game from [ModelCharacterConnection]*/
class NPCController(
    private val modelConnection: ModelCharacterConnection,
    private val npc: Mob,
    private val player: Character,
    sleepTimeMillis: Long = 1000
) {
    private val sleepingTimer = SleepingTimer(sleepTimeMillis)
    fun run() {
        while (npc.character.isAlive()) {
            npc.character.passiveRegeneration()
            npc.state.changeState(npc, player)
            npc.behaviour.doAnything(modelConnection)
            npc.behaviour.tryToSplit(modelConnection)
            sleepingTimer.await()
        }
    }
}
