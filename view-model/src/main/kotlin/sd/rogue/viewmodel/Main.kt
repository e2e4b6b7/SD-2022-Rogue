package sd.rogue.viewmodel

import org.koin.core.module.Module
import org.koin.dsl.module
import ru.hse.rogue.model.Game
import ru.hse.rogue.model.level.builder.RandomLevelBuilder
import ru.hse.rogue.model.level.itemfactory.MedievalInventoryFactory
import ru.hse.rogue.model.level.mobfactory.ManLikeMobFactory
import sd.rogue.viewmodel.characteristics.CharacteristicsViewModel
import sd.rogue.viewmodel.inventory.InventoryViewModel
import sd.rogue.viewmodel.map.MapViewModel
import java.util.concurrent.Executors

typealias PaintableID = String
typealias LayoutID = String

data class GameConfig(val enemiesCount: Int, val healthBonusCount: Int, val width: Int, val height: Int)

fun init(config: GameConfig): Module {
    val game = Game(
        RandomLevelBuilder()
            .setNumOfEnemies(config.enemiesCount)
            .setNumOfExtraHealth(config.healthBonusCount)
            .setWidth(config.width)
            .setHeight(config.height)
            .setMobFactory(ManLikeMobFactory)
            .setInventoryFactory(MedievalInventoryFactory)
            .build()
    )
    return module {
        single { Executors.newScheduledThreadPool(7) }
        single { game.playerCharacterModelConnection }

        single { LayoutViewModel() }
        single { MapViewModel() }
        single { InventoryViewModel() }
        single { CharacteristicsViewModel() }
    }
}
