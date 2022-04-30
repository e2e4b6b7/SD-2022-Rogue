package ru.hse.rogue.model.level.builder

import ru.hse.rogue.model.level.Level
import ru.hse.rogue.model.level.itemfactory.AbstractInventoryFactory
import ru.hse.rogue.model.level.mobfactory.AbstractMobFactory
import java.nio.file.Path

class FileLevelBuilder(private val levelFilePath: Path) : LevelBuilder {
    private var mobFactory: AbstractMobFactory? = null
    private var inventoryFactory: AbstractInventoryFactory? = null

    override fun setMobFactory(factory: AbstractMobFactory): LevelBuilder {
        this.mobFactory = factory
        return this
    }

    override fun setInventoryFactory(factory: AbstractInventoryFactory): LevelBuilder {
        this.inventoryFactory = factory
        return this
    }

    override fun build(): Level {
        TODO("Not yet implemented")
    }
}
