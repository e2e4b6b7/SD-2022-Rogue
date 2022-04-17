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
               // AggressiveRandomWalker,
                //AggressiveStander,
                // AggressiveStupidHunter(player, (2..5).random()),
                CowardWalker(player, (2..5).random()),
                //FriendlyRandomWalker,
                FriendlyStander
            ).random()
        }
    }
}
