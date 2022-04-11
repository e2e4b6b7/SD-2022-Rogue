package ru.hse.rogue.model.utils

import ru.hse.rogue.model.map.Direction
import ru.hse.rogue.model.map.Position

fun Position.isInBounds(width: Int, height: Int) = x in 0 until width && y in 0 until height

fun Position.applyDirection(direction: Direction) = when (direction) {
    Direction.LEFT -> Position(x - 1, y)
    Direction.RIGHT -> Position(x + 1, y)
    Direction.UP -> Position(x, y - 1)
    Direction.DOWN -> Position(x, y + 1)
}
