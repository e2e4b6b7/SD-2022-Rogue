package ru.hse.rogue.model.level.builder

import ru.hse.rogue.model.level.Level
import ru.hse.rogue.model.level.inventoryfactory.AbstractInventoryFactory
import ru.hse.rogue.model.level.mobfactory.AbstractMobFactory
import java.nio.file.Path

/** Builder of [Level] from file */
class FileLevelBuilder(private val levelFilePath: Path) : LevelBuilder {
    private var mobFactory: AbstractMobFactory? = null
    private var inventoryFactory: AbstractInventoryFactory? = null

    override fun setMobFactory(factory: AbstractMobFactory): FileLevelBuilder {
        this.mobFactory = factory
        return this
    }

    override fun setInventoryFactory(factory: AbstractInventoryFactory): FileLevelBuilder {
        this.inventoryFactory = factory
        return this
    }

    override fun build(): Level {
        TODO("Not yet implemented")
    }
}
