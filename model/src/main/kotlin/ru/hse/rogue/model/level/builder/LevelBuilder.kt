package ru.hse.rogue.model.level.builder

import ru.hse.rogue.model.level.Level
import ru.hse.rogue.model.level.inventoryfactory.AbstractInventoryFactory
import ru.hse.rogue.model.level.mobfactory.AbstractMobFactory

/** [Level] builder interface */
interface LevelBuilder {
    fun setMobFactory(factory: AbstractMobFactory): LevelBuilder
    fun setInventoryFactory(factory: AbstractInventoryFactory): LevelBuilder

    fun build(): Level
}
