package ru.hse.rogue.model.npc.behaviour

import ru.hse.rogue.model.connection.ModelCharacterConnection
import ru.hse.rogue.model.map.Position

/** Behaviour for aggressive characters, which does not moving */
object AggressiveStander: Behaviour {
    override fun doAnything(modelConnection: ModelCharacterConnection) {
        val charPos: Position = modelConnection.getSearchableWithPos(modelConnection.characterId)?.second ?: return
        val potentialVictims = modelConnection.getMap().getPotentialCharactersForAttack(charPos)
        if (potentialVictims.isNotEmpty())
            modelConnection.attack(potentialVictims.random())
    }
}
