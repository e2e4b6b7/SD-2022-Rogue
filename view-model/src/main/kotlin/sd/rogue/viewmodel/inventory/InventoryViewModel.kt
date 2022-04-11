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

    private fun updateItems() {
        itemsMutable = fetchItems()
    }

    private fun fetchItems(): MutableList<InventoryItem> {
        return connection.character.inventory.mapTo(mutableListOf()) { InventoryItem(it.presentationId, it.name ?: "") }
    }
}
