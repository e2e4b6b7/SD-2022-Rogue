package ru.hse.rogue.model.npc.behaviour

import ru.hse.rogue.model.connection.ModelCharacterConnection
import ru.hse.rogue.model.connection.ModelCharacterConnectionImpl
import ru.hse.rogue.model.gameobject.character.CloneableCharacter
import ru.hse.rogue.model.gameobject.character.ImmutableCharacter
import ru.hse.rogue.model.level.Mob
import ru.hse.rogue.model.npc.NPCController
import ru.hse.rogue.model.npc.state.NormalState
import ru.hse.rogue.model.npc.state.State
import kotlin.concurrent.thread

/** Interface that is responsible for what actions character will do*/
interface Behaviour {
    /** Make an action based on modelConnection */
    fun doAnything(modelConnection: ModelCharacterConnection)

    fun tryToSplit(modelConnection: ModelCharacterConnection) {
        val character = modelConnection.character
        if (character is CloneableCharacter) {
            if (character.needToClone()) {
                val cloneCharacter = character.clone()
                if (modelConnection.addGameObjectNearGameAnotherObject(character.id, cloneCharacter)) {
                    val newMob = Mob(cloneCharacter, this, NormalState(State.DEFAULT_HEALTH_THRESH_HOLD))
                    modelConnection.level.mobs.add(newMob)

                    thread(start = true, isDaemon = true) {
                        NPCController(
                            ModelCharacterConnectionImpl(
                                modelConnection.level, cloneCharacter
                            ), newMob, modelConnection.level.player
                        ).run()
                    }
                }
            }
        }
    }


    companion object {
        fun createRandomBehaviour(player: ImmutableCharacter): Behaviour {
            return mutableListOf(
                AggressiveStupidHunter(player, (5..8).random()),
                CowardWalker(player, (5..8).random()),
                FriendlyStander
            ).random()
        }
    }
}
