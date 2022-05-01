package ru.hse.rogue.model.gameobject.character

import ru.hse.rogue.model.gameobject.PresentationId
import kotlin.random.Random

/** Character which is able to clone */
class CloneableCharacter(
    startHealth: UInt,
    private val probability: Double = 0.03,
    override val presentationId: PresentationId = "Slime"
):
    CharacterImpl(startHealth, PresentationId()),
    Cloneable {

    /** Does character need to clone */
    fun needToClone(): Boolean {
        return Random.nextDouble() < probability
    }

    public override fun clone(): CloneableCharacter {
        val cloneCharacter = CloneableCharacter(baseHealth, probability, presentationId)
        for (item in inventory) {
            cloneCharacter.pickInventory(item)
        }
        for (item in usingInventory) {
            cloneCharacter.useInventory(item.id)
        }
        return cloneCharacter
    }
}


