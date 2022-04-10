package ru.hse.rogue.model.gameobject

import java.util.UUID

interface GameObject

object FreeSpace: GameObject
object Wall: GameObject

typealias SearchId = UUID

interface Searchable: GameObject {
    val id: SearchId
}
