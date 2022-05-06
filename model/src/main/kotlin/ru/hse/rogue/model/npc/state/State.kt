package ru.hse.rogue.model.npc.state

import ru.hse.rogue.model.gameobject.character.Character
import ru.hse.rogue.model.level.Mob

interface State {

    fun changeState(npc: Mob, player: Character)

    companion object {
        const val DEFAULT_HEALTH_THRESH_HOLD = 10u
    }
}
