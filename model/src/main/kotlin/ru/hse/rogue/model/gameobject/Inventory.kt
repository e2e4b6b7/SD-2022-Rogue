package ru.hse.rogue.model.gameobject

import ru.hse.rogue.model.gameobject.character.CharacteristicType
import ru.hse.rogue.model.map.MutableMapElement
import java.util.UUID

interface Inventory: Searchable {
    val name: String?
    val characteristics: MutableMap<CharacteristicType, Int>
}

class Cloth(override val name: String, armour: Int = 0) : Inventory {
    override val characteristics = mutableMapOf(Pair(CharacteristicType.ARMOUR, armour))
    override val id: SearchId = UUID.randomUUID()
}

class ExtraHealth(extraHealth: Int) : Inventory {
    override val name: String? = null
    override val characteristics = mutableMapOf(Pair(CharacteristicType.HEALTH, extraHealth))
    override val id: SearchId = UUID.randomUUID()
}

class Arm(override val name: String, harm: Int) : Inventory {
    override val characteristics = mutableMapOf(Pair(CharacteristicType.HARM, harm))
    override val id: SearchId = UUID.randomUUID()
}



