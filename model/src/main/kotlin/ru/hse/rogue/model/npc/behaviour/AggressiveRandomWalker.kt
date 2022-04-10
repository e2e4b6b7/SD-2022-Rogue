package ru.hse.rogue.model.npc.behaviour

import ru.hse.rogue.model.connection.ModelCharacterConnection
import ru.hse.rogue.model.map.Direction
import ru.hse.rogue.model.map.Position

/** Behaviour for aggressive moving characters.
 * In every turn they check can they attack enemies and if not they move random */
object AggressiveRandomWalker: Behaviour {
    override fun doAnything(modelConnection: ModelCharacterConnection) {
        val charPos: Position = modelConnection.getSearchableWithPos(modelConnection.characterId)?.second ?: return
        val potentialVictims = modelConnection.getMap().getPotentialCharactersForAttack(charPos)
        if (potentialVictims.isNotEmpty())
            modelConnection.attack(potentialVictims.random())
        else
            modelConnection.move(Direction.values().random())
    }
}
