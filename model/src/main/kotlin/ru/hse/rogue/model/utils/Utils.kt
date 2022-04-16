package ru.hse.rogue.model.utils

import ru.hse.rogue.model.map.Direction
import ru.hse.rogue.model.map.Position
import kotlin.math.absoluteValue

fun Position.isInBounds(width: Int, height: Int) = x in 0 until width && y in 0 until height

fun Position.applyDirection(direction: Direction) = when (direction) {
    Direction.LEFT -> Position(x - 1, y)
    Direction.RIGHT -> Position(x + 1, y)
    Direction.UP -> Position(x, y - 1)
    Direction.DOWN -> Position(x, y + 1)
}

fun Position.manhattanDistanceTo(otherPos: Position): Int {
    return (x - otherPos.x).absoluteValue + (y - otherPos.y).absoluteValue
}

fun Position.directionsTo(otherPos: Position): List<Direction> {
    val res = mutableListOf<Direction>()
    if (otherPos.x > x) {
        res.add(Direction.RIGHT)
    } else if (otherPos.x < x) {
        res.add(Direction.LEFT)
    }
    if (otherPos.y > y) {
        res.add(Direction.DOWN)
    } else if (otherPos.y < y) {
        res.add(Direction.UP)
    }
    return res
}
