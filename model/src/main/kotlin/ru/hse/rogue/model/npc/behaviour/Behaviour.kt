package ru.hse.rogue.model.npc.behaviour

import ru.hse.rogue.model.connection.ModelCharacterConnection
import ru.hse.rogue.model.connection.ModelCharacterConnectionImpl
import ru.hse.rogue.model.gameobject.character.CloneableCharacter
import ru.hse.rogue.model.gameobject.character.ImmutableCharacter
import ru.hse.rogue.model.level.Mob
import ru.hse.rogue.model.npc.NPCController
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
                    modelConnection.level.mobs.add(Mob(cloneCharacter, this))
                    thread(start = true, isDaemon = true) {
                        NPCController(
                            ModelCharacterConnectionImpl(
                                modelConnection.level, cloneCharacter
                            ), this
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
