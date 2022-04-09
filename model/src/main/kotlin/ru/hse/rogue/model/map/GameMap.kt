package ru.hse.rogue.model.map

import ru.hse.rogue.model.gameobject.*
import java.util.Stack

class MapElement : Stack<GameObject>()
data class Position(val x: Int, val y: Int) {
    fun isInBounds(width: Int, height: Int) = x in 0 until width && y in 0 until height
}

internal class GameMap(val width: Int, val height: Int) {
    private val searchableObjectsMap = mutableMapOf<SearchId, Pair<Searchable, Position>>()
    val mapElementsArray: Array<Array<MapElement>> = Array(height) {
        Array(width) {
            MapElement().apply { push(FreeSpace) }
        }
    }

    operator fun get(x: Int, y: Int): GameObject = mapElementsArray[y][x].peek()
    operator fun get(position: Position) = this[position.x, position.y]

    operator fun set(x: Int, y: Int, gameObject: GameObject) {
        if (gameObject is Searchable) {
            assert(gameObject.getId() !in searchableObjectsMap)
            searchableObjectsMap[gameObject.getId()] = Pair(gameObject, Position(x, y))
        }
        mapElementsArray[y][x].push(gameObject)
    }
    operator fun set(position: Position, gameObject: GameObject) {
        this[position.x, position.y] = gameObject
    }

    fun pop(x: Int, y: Int): GameObject {
        val gameObject = mapElementsArray[y][x].pop()
        if (gameObject is Searchable) {
            searchableObjectsMap.remove(gameObject.getId())
        }
        return gameObject
    }
    fun pop(position: Position) = this.pop(position.x, position.y)

    fun searchObject(id: SearchId): Pair<Searchable, Position>? {
        return searchableObjectsMap[id]
    }
}

