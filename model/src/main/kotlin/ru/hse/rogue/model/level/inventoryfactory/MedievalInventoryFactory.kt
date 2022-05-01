package ru.hse.rogue.model.level.inventoryfactory

import ru.hse.rogue.model.gameobject.*

object MedievalInventoryFactory: AbstractInventoryFactory {
    override fun createCloth(): Cloth {
        return Cloth("Chain Mail", 50, "Chain Mail")
    }

    override fun createArm(): Arm {
        return listOf(
            Arm("Axe", 5, "Axe", 30),
            Arm("Sword", 7, "Sword", 20),
            Arm("Dagger", 3, "Dagger", 10)
        ).random()
    }

    override fun createExtraHealth(): ExtraHealth {
        return ExtraHealth((5..10).random())
    }
}
