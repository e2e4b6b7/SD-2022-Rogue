package ru.hse.rogue.model.npc.behaviour

import ru.hse.rogue.model.connection.ModelCharacterConnection
import ru.hse.rogue.model.map.Direction
import ru.hse.rogue.model.map.Position

/** Behaviour for aggressive characters, which does not moving */
object AggressiveStander: Behaviour {
    override fun doAnything(modelConnection: ModelCharacterConnection) {
        for (dir in Direction.values()) {
            if (modelConnection.move(dir))
                return
        }
    }
}
