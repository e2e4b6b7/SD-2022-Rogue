package ru.hse.rogue.model.npc.behaviour

import ru.hse.rogue.model.ModelConnection
import ru.hse.rogue.model.gameobject.character.Character
import ru.hse.rogue.model.map.Position

object AggressiveStander: Behaviour {
    override fun doAnything(modelConnection: ModelConnection, character: Character) {
        val charPos: Position = modelConnection.getSearchableWithPos(character.id)?.second ?: return
        val potentialVictims = modelConnection.getMap().getPotentialCharactersForAttack(charPos)
        if (potentialVictims.isNotEmpty())
            modelConnection.attack(character.id, potentialVictims.random())
    }
}
