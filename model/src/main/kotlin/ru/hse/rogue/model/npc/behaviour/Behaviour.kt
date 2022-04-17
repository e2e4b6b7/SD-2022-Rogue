package ru.hse.rogue.model.npc.behaviour

import ru.hse.rogue.model.connection.ModelCharacterConnection
import ru.hse.rogue.model.gameobject.character.ImmutableCharacter

/** Interface that is responsible for what actions character will do*/
interface Behaviour {
    /** Make an action based on modelConnection */
    fun doAnything(modelConnection: ModelCharacterConnection)

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
