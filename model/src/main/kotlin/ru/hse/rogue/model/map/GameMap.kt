package ru.hse.rogue.model.map

import ru.hse.rogue.model.gameobject.*
import ru.hse.rogue.model.gameobject.character.Character
import ru.hse.rogue.model.utils.applyDirection
import ru.hse.rogue.model.utils.isInBounds


typealias MapElement = List<GameObject>
typealias MutableMapElement = MutableList<GameObject>

/** Position with coordinates ([x], [y])*/
data class Position(val x: Int, val y: Int)

/** Class for representing game map with ([width], [height]) shape */
class GameMap(val width: Int, val height: Int) {
    private val searchableObjectsMap = mutableMapOf<SearchId, Pair<Searchable, Position>>()

    /** Map. In every position there are a stack of [GameObject] */
    val mapElements: List<List<MutableMapElement>> = Array(width) {
        Array(height) {
            mutableListOf<GameObject>().apply { add(FreeSpace) }
        }.asList()
    }.asList()

    /** Get [MapElement] from ([x], [y]) */
    operator fun get(x: Int, y: Int): MapElement = mapElements[x][y]
    operator fun get(position: Position) = this[position.x, position.y]

    /** Add [gameObject] to position ([x], [y]). Append [gameObject] to the top of stack */
    operator fun set(x: Int, y: Int, gameObject: GameObject) {
        if (gameObject is Searchable) {
            assert(gameObject.id !in searchableObjectsMap)
            searchableObjectsMap[gameObject.id] = Pair(gameObject, Position(x, y))
        }
        mapElements[x][y].add(gameObject)
    }

    operator fun set(position: Position, gameObject: GameObject) {
        this[position.x, position.y] = gameObject
    }

    /** Remove gameObject from top of the stack on position ([x], [y]) */
    fun pop(x: Int, y: Int): GameObject {
        val gameObject = mapElements[x][y].removeLast()
        if (gameObject is Searchable) {
            searchableObjectsMap.remove(gameObject.id)
        }
        return gameObject
    }

    fun pop(position: Position) = this.pop(position.x, position.y)

    /** Search for [Searchable] object from [id] */
    fun searchObject(id: SearchId): Pair<Searchable, Position>? {
        return searchableObjectsMap[id]
    }

    @Synchronized
    fun attack(characterId: SearchId, direction: Direction): Boolean {
        val (character, pos) = searchObject(characterId) ?: return false
        if (character !is Character)
            throw RuntimeException("Searchable object with given id isn't Character")
        val dirPos = pos.applyDirection(direction)
        if (!dirPos.isInBounds(width, height))
            return false
        val dirObject = this[dirPos].last()
        if (dirObject is Character) {
            character.attack(dirObject)
            if (!dirObject.isAlive())
                this.pop(dirPos)
            return true
        }
        return false
    }

    @Synchronized
    fun useInventory(characterId: SearchId, inventoryId: SearchId): Boolean {
        val character = searchObject(characterId)?.first ?: return false
        if (character !is Character)
            return false
        val inventory = searchObject(inventoryId)?.first ?: return false
        if (inventory !is Inventory)
            return false
        character.useInventory(inventory)
        return true
    }

    @Synchronized
    fun move(characterId: SearchId, direction: Direction): Boolean {
        val (character, pos) = searchObject(characterId) ?: return false
        if (character !is Character)
            throw RuntimeException("Searchable object with given id isn't Character")

        val dirPos = pos.applyDirection(direction)
        if (!dirPos.isInBounds(width, height))
            return false
        val dirObject = this[dirPos].last()
        if (!dirPos.isInBounds(width, height) || dirObject is Character || dirObject is Wall)
            return false

        if (dirObject is Inventory) {
            character.pickInventory(dirObject)
            this.pop(dirPos)
        }
        this[dirPos] = this.pop(pos)
        return true
    }
}

