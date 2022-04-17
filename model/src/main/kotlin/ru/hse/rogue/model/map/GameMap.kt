package ru.hse.rogue.model.map

import ru.hse.rogue.model.gameobject.*
import ru.hse.rogue.model.gameobject.character.CharacterImpl
import ru.hse.rogue.model.utils.*


typealias MapElement = List<GameObject>
typealias MutableMapElement = MutableList<GameObject>

/** Position with coordinates ([x], [y])*/
data class Position(val x: Int, val y: Int)

/** Class for representing game map with ([width], [height]) shape */
class GameMap(val width: Int, val height: Int) {
    private val searchableObjectsMap = mutableMapOf<SearchId, Pair<Searchable, Position>>()

    /** Map. In every position there are a stack of [GameObject] */
    val mapElements: Immutable2DArray<MutableMapElement> = Immutable2DArray(width, height) { _, _ ->
        mutableListOf<GameObject>().apply { add(FreeSpace) }
    }

    /** Get [MapElement] from ([x], [y]) */
    operator fun get(x: Int, y: Int): MapElement = mapElements[x, y]
    operator fun get(position: Position) = this[position.x, position.y]

    /** Add [gameObject] to position ([x], [y]). Append [gameObject] to the top of stack */
    operator fun set(x: Int, y: Int, gameObject: GameObject) {
        if (gameObject is Searchable) {
            assert(gameObject.id !in searchableObjectsMap)
            searchableObjectsMap[gameObject.id] = Pair(gameObject, Position(x, y))
        }
        mapElements[x, y].add(gameObject)
    }

    operator fun set(position: Position, gameObject: GameObject) {
        this[position.x, position.y] = gameObject
    }

    /** Remove gameObject from top of the stack on position ([x], [y]) */
    fun pop(x: Int, y: Int): GameObject {
        val gameObject = mapElements[x, y].removeLast()
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
        if (character !is CharacterImpl)
            throw RuntimeException("Searchable object with given id isn't Character")
        val dirPos = pos.applyDirection(direction)
        if (!dirPos.isInBounds(width, height))
            return false
        val dirObject = this[dirPos].last()
        if (dirObject is CharacterImpl) {
            character.attack(dirObject)
            if (!dirObject.isAlive())
                character.experienceIncrease(CharacterImpl.EXP_PER_KILL)
                this.pop(dirPos)
            return true
        }
        return false
    }

    @Synchronized
    fun move(characterId: SearchId, direction: Direction): Boolean {
        val (character, pos) = searchObject(characterId) ?: return false
        if (character !is CharacterImpl)
            throw RuntimeException("Searchable object with given id isn't Character")

        val dirPos = pos.applyDirection(direction)
        if (!dirPos.isInBounds(width, height))
            return false
        val dirObject = this[dirPos].last()
        if (dirObject is CharacterImpl) {
            return attack(characterId, direction)
        }
        if (!dirPos.isInBounds(width, height) || dirObject is CharacterImpl || dirObject is Wall)
            return false

        if (dirObject is Inventory) {
            character.pickInventory(dirObject)
            this.pop(dirPos)
        }
        this[dirPos] = this.pop(pos)
        return true
    }
}

