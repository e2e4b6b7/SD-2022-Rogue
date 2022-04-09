package sd.rogue.viewmodel

import org.koin.dsl.module
import sd.rogue.viewmodel.characteristics.CharacteristicsViewModel
import sd.rogue.viewmodel.inventory.InventoryViewModel
import sd.rogue.viewmodel.map.MapViewModel

typealias PaintableID = String
typealias LayoutID = String

val viewModelModule = module {
    single { LayoutViewModel() }
    single { MapViewModel() }
    single { InventoryViewModel() }
    single { CharacteristicsViewModel() }
}
