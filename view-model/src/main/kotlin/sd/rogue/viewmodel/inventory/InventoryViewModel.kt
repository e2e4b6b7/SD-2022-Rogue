package sd.rogue.viewmodel.inventory

import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import ru.hse.rogue.model.connection.ModelCharacterConnection
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

class InventoryViewModel : KoinComponent {
    private val connection: ModelCharacterConnection = get()

    private var itemsMutable = fetchItems()
    val items: List<InventoryItem> get() = itemsMutable

    init {
        get<ScheduledExecutorService>().scheduleAtFixedRate(this::updateItems, 15, 15, TimeUnit.MILLISECONDS)
    }

    fun interact(idx: Int) {
        require(itemsMutable.indices.contains(idx))
        if (itemsMutable[idx].enabled) {
            connection.disableInventory(itemsMutable[idx].searchId)
        } else {
            connection.useInventory(itemsMutable[idx].searchId)
        }
    }

    private fun updateItems() {
        itemsMutable = fetchItems()
    }

    private fun fetchItems(): MutableList<InventoryItemInternal> {
        val newItems = mutableListOf<InventoryItemInternal>()
        connection.character.usingInventory
            .mapTo(newItems) {
                InventoryItemInternal(it.presentationId, it.name ?: "", true, it.id)
            }
        val used = connection.character.usingInventory.asSequence().map { it.id }.toSet()
        connection.character.inventory
            .filter { !used.contains(it.id) }
            .mapTo(newItems) {
                InventoryItemInternal(it.presentationId, it.name ?: "", false, it.id)
            }
        return newItems
    }
}
