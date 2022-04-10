package ru.hse.rogue.model.gameobject

import java.util.UUID

interface Pickable: Searchable

class Arm(val harm: UInt, val name: String): Pickable {
    override val id: SearchId = UUID.randomUUID()
}

class Cloth(val name: String): Pickable {
    override val id: SearchId = UUID.randomUUID()
}

class ExtraHealth(val health: UInt): Pickable {
    override val id: SearchId = UUID.randomUUID()
}
