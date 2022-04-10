package ru.hse.rogue.model.npc.behaviour

import ru.hse.rogue.model.ModelConnection
import ru.hse.rogue.model.gameobject.character.Character
import ru.hse.rogue.model.gameobject.SearchId
import ru.hse.rogue.model.map.MapElement
import ru.hse.rogue.model.map.Position
import ru.hse.rogue.model.utils.isInBounds

interface Behaviour {
    fun doAnything(modelConnection: ModelConnection, character: Character)
    fun List<List<MapElement>>.getPotentialCharactersForAttack(position: Position): List<SearchId> {
        if (this.isEmpty() || this[0].isEmpty())
            throw RuntimeException("Game map is empty")
        val res = mutableListOf<SearchId>()
        for ((x, y) in listOf(Pair(position.x - 1, 0), Pair(position.x + 1, 0),
            Pair(0, position.y + 1), Pair(0, position.y - 1)
        )) {
            if (!Position(x, y).isInBounds(this[0].size, this.size)) {
                val gameObject = this[y][x].last()
                if (gameObject is Character) {
                    res.add(gameObject.id)
                }
            }
        }
        return res
    }
}
