package ru.hse.rogue.model.level.inventoryfactory

import ru.hse.rogue.model.gameobject.*

/** Factory of inventory */
interface AbstractInventoryFactory {
    fun createCloth(): Cloth
    fun createArm(): Arm
    fun createExtraHealth(): ExtraHealth
}
