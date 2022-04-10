package ru.hse.rogue.model.npc.behaviour

import ru.hse.rogue.model.connection.ModelCharacterConnection
import ru.hse.rogue.model.map.Direction

object FriendlyRandomWalker: Behaviour {
    override fun doAnything(modelConnection: ModelCharacterConnection) {
        modelConnection.move(Direction.values().random())
    }
}
