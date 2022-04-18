package ru.hse.rogue.model.npc.behaviour

import ru.hse.rogue.model.connection.ModelCharacterConnection
import ru.hse.rogue.model.map.Direction
import ru.hse.rogue.model.npc.behaviour.StunnedBehaviour.Companion.INITIAL_STUNNED_TURNS

/** Behaviour for stunned personage. He moves randomly every turn with duration [INITIAL_STUNNED_TURNS]*/
class StunnedBehaviour(private val delegateBehavior: Behaviour) : Behaviour {
    private var stunnedTurns = INITIAL_STUNNED_TURNS

    override fun doAnything(modelConnection: ModelCharacterConnection) {
        if (!isActive()) {
            return delegateBehavior.doAnything(modelConnection)
        }
        stunnedTurns -= 1
        for (i in (0..TIMES_TO_TRY)) {
            if (modelConnection.move(Direction.values().random())) {
                return
            }
        }
        for (direction in Direction.values()) {
            if (modelConnection.move(direction)) {
                return
            }
        }
    }

    /** Is effect active*/
    fun isActive(): Boolean {
        return stunnedTurns > 0
    }

    companion object {
        private const val INITIAL_STUNNED_TURNS = 5
        private const val TIMES_TO_TRY = 7
    }
}
