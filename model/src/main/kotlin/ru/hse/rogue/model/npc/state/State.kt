package ru.hse.rogue.model.npc.state

import ru.hse.rogue.model.gameobject.character.Character
import ru.hse.rogue.model.level.Mob

/** Interface that reacts on changes of mob's character state (health, for example) */
interface State {
    /** Change state of mob */
    fun changeState(npc: Mob, player: Character)

    companion object {
        const val DEFAULT_HEALTH_THRESH_HOLD = 10u
    }
}
