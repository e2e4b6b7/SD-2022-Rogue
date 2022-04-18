package ru.hse.rogue.model.npc.behaviour

import ru.hse.rogue.model.connection.ModelCharacterConnection
import ru.hse.rogue.model.gameobject.character.ImmutableCharacter
import ru.hse.rogue.model.map.Direction
import ru.hse.rogue.model.utils.*

class CowardWalker(
    private val playerCharacter: ImmutableCharacter,
    private val sensitiveManhattanDistance: Int
) : Behaviour {
    override fun doAnything(modelConnection: ModelCharacterConnection) {
        val playerPos = modelConnection.getSearchableWithPos(playerCharacter.id)?.second?.copy() ?: return
        val hunterPos = modelConnection.getSearchableWithPos(modelConnection.character.id)?.second?.copy() ?: return
        val manhattanDistanceToPlayer = hunterPos.manhattanDistanceTo(playerPos)
        if (sensitiveManhattanDistance >= manhattanDistanceToPlayer) {
            val dirsToPlayer = hunterPos.directionsTo(playerPos)
            for (dir in Direction.values().filter { it !in dirsToPlayer }) {
                if (modelConnection.move(dir))
                    return
            }
        }
    }
}
