package ru.hse.rogue.model.gameobject

import java.util.UUID

sealed interface Pickable: Searchable

class Arm(val harm: UInt, val name: String): Pickable {
    private val id = UUID.randomUUID()
    override fun getId(): SearchId = id
}

class Cloth(val name: String): Pickable {
    private val id = UUID.randomUUID()
    override fun getId(): SearchId = id
}

class ExtraHealth(val health: UInt): Pickable {
    private val id = UUID.randomUUID()
    override fun getId(): SearchId = id
}
