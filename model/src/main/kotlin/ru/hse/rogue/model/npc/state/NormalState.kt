package ru.hse.rogue.model.npc.state

import ru.hse.rogue.model.gameobject.character.Character
import ru.hse.rogue.model.level.Mob
import ru.hse.rogue.model.npc.behaviour.CowardWalker
import ru.hse.rogue.model.npc.behaviour.StunnedBehaviour

/** State of mob which health is not lower than [healthThreshold]  */
class NormalState(private val healthThreshold: UInt) : State {

    override fun changeState(npc: Mob, player: Character) {
        if (npc.character.isStunned.get()) {
            npc.behaviour = StunnedBehaviour(npc.behaviour)
            npc.state = StunnedState(npc.state)
        } else if (npc.character.curHealth < healthThreshold) {
            npc.state = PanicState(npc.behaviour, healthThreshold)
            npc.behaviour = CowardWalker(player, 5)
        }
    }
}
