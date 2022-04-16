package ru.hse.rogue.model.npc.behaviour

import ru.hse.rogue.model.connection.ModelCharacterConnection
import ru.hse.rogue.model.gameobject.character.ImmutableCharacter
import ru.hse.rogue.model.utils.directionsTo
import ru.hse.rogue.model.utils.manhattanDistanceTo

class AggressiveStupidHunter(
    private val playerCharacter: ImmutableCharacter,
    private val sensitiveManhattanDistance: Int
) : Behaviour {
    override fun doAnything(modelConnection: ModelCharacterConnection) {
        val playerPos = modelConnection.getSearchableWithPos(playerCharacter.id)?.second?.copy() ?: return
        val hunterPos = modelConnection.getSearchableWithPos(modelConnection.character.id)?.second?.copy() ?: return
        val manhattanDistanceToPlayer = hunterPos.manhattanDistanceTo(playerPos)
        if (sensitiveManhattanDistance >= manhattanDistanceToPlayer) {
            if (manhattanDistanceToPlayer == 1) {
                for (dir in hunterPos.directionsTo(playerPos)) {
                    if (modelConnection.attack(dir))
                        return
                }
            }
            for (dir in hunterPos.directionsTo(playerPos)) {
                if (modelConnection.move(dir))
                    return
            }
        }
    }
}
