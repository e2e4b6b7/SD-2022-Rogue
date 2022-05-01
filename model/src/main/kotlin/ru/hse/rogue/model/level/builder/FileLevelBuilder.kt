package ru.hse.rogue.model.level.builder

import com.opencsv.CSVReader
import ru.hse.rogue.model.gameobject.FreeSpace
import ru.hse.rogue.model.gameobject.Wall
import ru.hse.rogue.model.gameobject.character.CharacterImpl
import ru.hse.rogue.model.level.Level
import ru.hse.rogue.model.level.builder.FileLevelBuilder.AbstractMapElement.*
import ru.hse.rogue.model.level.inventoryfactory.AbstractInventoryFactory
import ru.hse.rogue.model.level.mobfactory.AbstractMobFactory
import ru.hse.rogue.model.map.GameMap
import java.nio.file.Path
import kotlin.io.path.bufferedReader

/** Builder of [Level] from file */
class FileLevelBuilder(private val levelFilePath: Path) : LevelBuilder {
    private lateinit var mobFactory: AbstractMobFactory
    private lateinit var inventoryFactory: AbstractInventoryFactory

    override fun setMobFactory(factory: AbstractMobFactory): FileLevelBuilder {
        this.mobFactory = factory
        return this
    }

    override fun setInventoryFactory(factory: AbstractInventoryFactory): FileLevelBuilder {
        this.inventoryFactory = factory
        return this
    }

    override fun build(): Level {
        require(this::mobFactory.isInitialized) { "mob factory must be set" }
        require(this::inventoryFactory.isInitialized) { "inventory factory must be set" }

        val player = CharacterImpl(PLAYER_HEALTH)

        val map = CSVReader(levelFilePath.bufferedReader()).use { reader ->
            reader.readAll().map { it.map { it.split(' ').map { valueOf(it) } } }
        }
        val level = Level(GameMap(map[0].size, map.size), player)
        map.forEachIndexed { i, line ->
            line.forEachIndexed { j, point ->
                point.forEach {
                    level.map[j, i] = when (it) {
                        grass -> FreeSpace
                        wall -> Wall
                        hero -> player
                        health -> inventoryFactory.createExtraHealth()
                        arm -> inventoryFactory.createArm()
                        cloth -> inventoryFactory.createCloth()
                        botAgr -> {
                            level.mobs.add(mobFactory.createAggressive(player))
                            level.mobs.last().character
                        }
                        botCow -> {
                            level.mobs.add(mobFactory.createCoward(player))
                            level.mobs.last().character
                        }
                        botFri -> {
                            level.mobs.add(mobFactory.createFriendly())
                            level.mobs.last().character
                        }
                    }
                }
            }
        }
        return level
    }

    enum class AbstractMapElement { hero, health, arm, cloth, botAgr, botCow, botFri, grass, wall }

    companion object {
        private const val PLAYER_HEALTH = 100u
    }
}
