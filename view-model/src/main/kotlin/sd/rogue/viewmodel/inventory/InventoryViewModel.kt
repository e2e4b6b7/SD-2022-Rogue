package sd.rogue.viewmodel.inventory

class InventoryViewModel {
    private val itemsMutable = mutableListOf<InventoryItem>()

    val items: List<InventoryItem> get() = itemsMutable

    init {
        itemsMutable.add(InventoryItem("Sword", "Sword"))
        itemsMutable.add(InventoryItem("Boots", "Boots"))
    }
}
