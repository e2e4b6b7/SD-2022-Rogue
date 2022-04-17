package sd.rogue.viewmodel.inventory

import ru.hse.rogue.model.gameobject.SearchId
import sd.rogue.viewmodel.PaintableID

interface InventoryItem {
    val icon: PaintableID
    val description: String
    val enabled: Boolean
}

data class InventoryItemInternal(
    override val icon: PaintableID,
    override val description: String,
    override val enabled: Boolean,
    val searchId: SearchId
) : InventoryItem
