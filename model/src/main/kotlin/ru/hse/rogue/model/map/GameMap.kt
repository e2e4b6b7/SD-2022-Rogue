package ru.hse.rogue.model.map

import ru.hse.rogue.model.gameobject.*


typealias MapElement = List<GameObject>
typealias MutableMapElement = MutableList<GameObject>

data class Position(val x: Int, val y: Int)

class GameMap(val width: Int, val height: Int) {
    private val searchableObjectsMap = mutableMapOf<SearchId, Pair<Searchable, Position>>()
    val mapElementsArray: Array<Array<MutableMapElement>> = Array(height) {
        Array(width) {
            mutableListOf<GameObject>().apply { add(FreeSpace) }
        }
    }

    operator fun get(x: Int, y: Int): MapElement = mapElementsArray[y][x]
    operator fun get(position: Position) = this[position.x, position.y]

    operator fun set(x: Int, y: Int, gameObject: GameObject) {
        if (gameObject is Searchable) {
            assert(gameObject.id !in searchableObjectsMap)
            searchableObjectsMap[gameObject.id] = Pair(gameObject, Position(x, y))
        }
        mapElementsArray[y][x].add(gameObject)
    }
    operator fun set(position: Position, gameObject: GameObject) {
        this[position.x, position.y] = gameObject
    }

    fun pop(x: Int, y: Int): GameObject {
        val gameObject = mapElementsArray[y][x].removeLast()
        if (gameObject is Searchable) {
            searchableObjectsMap.remove(gameObject.id)
        }
        return gameObject
    }
    fun pop(position: Position) = this.pop(position.x, position.y)

    fun searchObject(id: SearchId): Pair<Searchable, Position>? {
        return searchableObjectsMap[id]
    }
}

