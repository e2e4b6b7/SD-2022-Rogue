package ru.hse.rogue.model.npc.behaviour

import ru.hse.rogue.model.connection.ModelCharacterConnection
import ru.hse.rogue.model.map.Direction

/** Behaviour for aggressive moving characters.
 * In every turn they check can they attack enemies and if not they move random */
object AggressiveRandomWalker : Behaviour {
    override fun doAnything(modelConnection: ModelCharacterConnection) {
        for (dir in Direction.values()) {
            if (modelConnection.move(dir))
                return
        }
        modelConnection.move(Direction.values().random())
    }
}
