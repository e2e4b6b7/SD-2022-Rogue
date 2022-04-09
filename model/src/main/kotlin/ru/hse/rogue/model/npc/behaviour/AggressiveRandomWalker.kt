package ru.hse.rogue.model.npc.behaviour

import ru.hse.rogue.model.ModelConnection
import ru.hse.rogue.model.gameobject.Character
import ru.hse.rogue.model.map.Direction
import ru.hse.rogue.model.map.Position

object AggressiveRandomWalker: Behaviour {
    override fun doAnythingOrNotDo(modelConnection: ModelConnection, character: Character) {
        val charPos: Position = modelConnection.getSearchablePos(character.getId()) ?: return
        val potentialVictims = modelConnection.getPotentialCharactersForAttack(charPos)
        if (potentialVictims.isNotEmpty())
            modelConnection.tryAttack(character.getId(), potentialVictims.random())
        else
            modelConnection.tryMoveCharacter(character.getId(), Direction.values().random())
    }
}
