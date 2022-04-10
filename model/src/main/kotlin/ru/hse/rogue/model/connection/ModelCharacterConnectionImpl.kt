package ru.hse.rogue.model.connection

import ru.hse.rogue.model.Game
import ru.hse.rogue.model.gameobject.SearchId
import ru.hse.rogue.model.gameobject.Searchable
import ru.hse.rogue.model.gameobject.character.Character
import ru.hse.rogue.model.map.*


class ModelCharacterConnectionImpl internal constructor(private val game: Game,
                                                        override val characterId: SearchId
) : ModelCharacterConnection {
    override fun getMap(): List<List<MapElement>> = game.map
    override fun move(direction: Direction) = game.moveCharacter(characterId, direction)
    override fun useInventory(inventoryId: SearchId): Boolean {
        TODO("Not yet implemented")
    }

    @Synchronized
    override fun getSearchableWithPos(id: SearchId): Pair<Searchable, Position>? {
        TODO("Not yet implemented")
    }

    @Synchronized
    override fun attack(direction: Direction): Boolean {
        TODO("Not yet implemented")
    }
}
