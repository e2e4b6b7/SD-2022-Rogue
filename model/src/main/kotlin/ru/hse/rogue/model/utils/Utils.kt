package ru.hse.rogue.model.utils

import ru.hse.rogue.model.map.Position

fun Position.isInBounds(width: Int, height: Int) = x in 0 until width && y in 0 until height
