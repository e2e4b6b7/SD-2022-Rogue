package ru.hse.rogue.model.gameobject

import ru.hse.rogue.model.gameobject.character.CharacteristicType
import java.util.*

/** Interface for representing game object from inventory */
interface Inventory : Searchable {
    /** Name of item */
    val name: String?

    /** Characteristics of item */
    val characteristics: MutableMap<CharacteristicType, Int>
}

/** Class for representing cloth item with armour */
class Cloth(override val name: String, health: Int = 0, override val presentationId: PresentationId) : Inventory {
    override val characteristics = mutableMapOf(Pair(CharacteristicType.HEALTH, health))
    override val id: SearchId = UUID.randomUUID()
}

/** Class for representing items which give character extra health*/
class ExtraHealth(extraHealth: Int, override val presentationId: PresentationId = "Extra health") : Inventory {
    override val name: String = "Extra health"
    override val characteristics = mutableMapOf(Pair(CharacteristicType.HEALTH, extraHealth))
    override val id: SearchId = UUID.randomUUID()
}

/** Class for representing items which give character new value of harm */
class Arm(
    override val name: String,
    harm: Int,
    override val presentationId: PresentationId,
    stunChancePercent: Int = 0
) : Inventory {
    override val characteristics = mutableMapOf(
        Pair(CharacteristicType.HARM, harm),
        Pair(CharacteristicType.STUN, stunChancePercent)
    )
    override val id: SearchId = UUID.randomUUID()
}



