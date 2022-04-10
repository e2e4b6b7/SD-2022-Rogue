package ru.hse.rogue.model.npc.behaviour

import ru.hse.rogue.model.ModelConnection
import ru.hse.rogue.model.gameobject.character.Character
import ru.hse.rogue.model.map.Direction

object FriendlyRandomWalker: Behaviour {
    override fun doAnything(modelConnection: ModelConnection, character: Character) {
        modelConnection.moveCharacter(character.id, Direction.values().random())
    }
}
