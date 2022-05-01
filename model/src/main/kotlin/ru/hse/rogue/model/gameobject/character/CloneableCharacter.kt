package ru.hse.rogue.model.gameobject.character

import ru.hse.rogue.model.gameobject.PresentationId
import kotlin.random.Random

class CloneableCharacter(
    startHealth: UInt,
    private val probability: Double = 0.2,
    override val presentationId: PresentationId = "Slime"
):
    CharacterImpl(startHealth, PresentationId()),
    Cloneable {

    fun needToClone(): Boolean {
        return Random.nextDouble() < probability
    }

    public override fun clone(): CloneableCharacter {
        return super.clone() as CloneableCharacter
    }
}


