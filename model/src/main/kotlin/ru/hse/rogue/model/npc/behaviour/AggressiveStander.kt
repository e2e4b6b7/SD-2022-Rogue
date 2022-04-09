package ru.hse.rogue.model.npc.behaviour

import ru.hse.rogue.model.ModelConnection
import ru.hse.rogue.model.gameobject.Character
import ru.hse.rogue.model.map.Position

object AggressiveStander: Behaviour {
    override fun doAnythingOrNotDo(modelConnection: ModelConnection, character: Character) {
        val charPos: Position = modelConnection.getSearchablePos(character.getId()) ?: return
        val potentialVictims = modelConnection.getMap().getPotentialCharactersForAttack(charPos)
        if (potentialVictims.isNotEmpty())
            modelConnection.tryAttack(character.getId(), potentialVictims.random().getId())
    }
}
