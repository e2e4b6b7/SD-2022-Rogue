package ru.hse.rogue.model.map

import ru.hse.rogue.model.gameobject.*
import java.util.Stack

class GameMap(val width: Int, val height: Int) {
    private class MapElement : Stack<GameObject>()
    private val searchableObjectsMap = mutableMapOf<SearchId, Searchable>()
    private val gameMap: Array<Array<MapElement>> = Array(height) {
        Array(width) {
            MapElement().apply { push(FreeSpace) }
        }
    }

    operator fun get(y: Int, x: Int): GameObject = gameMap[y][x].peek()
    operator fun set(y: Int, x: Int, gameObject: GameObject) {
        if (gameObject is Searchable) {
            assert(gameObject.getId() !in searchableObjectsMap)
            searchableObjectsMap[gameObject.getId()] = gameObject
        }
        gameMap[y][x].push(gameObject)
    }
    fun pop(y: Int, x: Int): GameObject {
        val gameObject = gameMap[y][x].pop()
        if (gameObject is Searchable) {
            searchableObjectsMap.remove(gameObject.getId())
        }
        return gameObject
    }

    fun searchObject(id: SearchId): Searchable? {
        return searchableObjectsMap[id]
    }
}

