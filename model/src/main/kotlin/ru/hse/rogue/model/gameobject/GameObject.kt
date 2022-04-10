package ru.hse.rogue.model.gameobject

import java.util.UUID

/** Interface for representing game object*/
interface GameObject

/** Class for representing empty space in the position */
object FreeSpace: GameObject

/** Class for representing wall in the position. Character can not move through it */
object Wall: GameObject

typealias SearchId = UUID

/** Interface for object, which can be search by [id] */
interface Searchable: GameObject {
    val id: SearchId
}
