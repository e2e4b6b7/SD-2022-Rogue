package ru.hse.rogue.model.npc.behaviour

import ru.hse.rogue.model.connection.ModelCharacterConnection
import ru.hse.rogue.model.map.Direction

/** Behaviour for friendly random moving characters */
object FriendlyRandomWalker : Behaviour {
    override fun doAnything(modelConnection: ModelCharacterConnection) {
        modelConnection.move(Direction.values().random())
    }
}
