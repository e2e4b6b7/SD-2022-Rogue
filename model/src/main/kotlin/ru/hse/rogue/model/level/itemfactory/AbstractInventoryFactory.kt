package ru.hse.rogue.model.level.itemfactory

import ru.hse.rogue.model.gameobject.*

interface AbstractInventoryFactory {
    fun createCloth(): Cloth
    fun createArm(): Arm
    fun createExtraHealth(): ExtraHealth
}
