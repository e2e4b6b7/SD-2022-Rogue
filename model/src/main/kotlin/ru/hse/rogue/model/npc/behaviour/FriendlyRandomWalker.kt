package ru.hse.rogue.model.npc.behaviour

import ru.hse.rogue.model.ModelConnection
import ru.hse.rogue.model.gameobject.Character
import ru.hse.rogue.model.map.Direction

object FriendlyRandomWalker: Behaviour {
    override fun doAnythingOrNotDo(modelConnection: ModelConnection, character: Character) {
        modelConnection.tryMoveCharacter(character.getId(), Direction.values().random())
    }
}
