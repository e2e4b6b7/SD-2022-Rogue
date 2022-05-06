package ru.hse.rogue.model.npc.state

import ru.hse.rogue.model.gameobject.character.Character
import ru.hse.rogue.model.level.Mob
import ru.hse.rogue.model.npc.behaviour.StunnedBehaviour

/** State of mob which is stunned
 *  If mob isn't stunned, its state become [previousState]
 **/
class StunnedState(private val previousState: State) : State {

    override fun changeState(npc: Mob, player: Character) {
        if (npc.behaviour is StunnedBehaviour) {
            if (!(npc.behaviour as StunnedBehaviour).isActive()) {
                npc.character.isStunned.set(false)
                npc.state = previousState
            }
        }
    }
}
