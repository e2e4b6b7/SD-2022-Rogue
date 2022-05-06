package ru.hse.rogue.model.npc.state

import ru.hse.rogue.model.gameobject.character.Character
import ru.hse.rogue.model.level.Mob
import ru.hse.rogue.model.npc.behaviour.Behaviour
import ru.hse.rogue.model.npc.behaviour.StunnedBehaviour

/** State of mob which health is not higher than [healthThreshold]
 *  If health become higher, state changes to normal and behaviour becomes [oldBehaviour]
 **/
class PanicState(private val oldBehaviour: Behaviour, private val healthThreshold: UInt) : State {

    override fun changeState(npc: Mob, player: Character) {
        if (npc.character.isStunned.get()) {
            npc.behaviour = StunnedBehaviour(npc.behaviour)
            npc.state = StunnedState(npc.state)
        } else if (npc.character.curHealth > healthThreshold) {
            npc.state = NormalState(healthThreshold)
            npc.behaviour = oldBehaviour
        }
    }
}
