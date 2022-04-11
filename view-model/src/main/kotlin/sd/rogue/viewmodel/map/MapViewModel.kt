package sd.rogue.viewmodel.map

import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import ru.hse.rogue.model.connection.ModelCharacterConnection
import ru.hse.rogue.model.map.Direction
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

class MapViewModel : KoinComponent {
    private val connection: ModelCharacterConnection = get()

    private var fieldMutable = fetchField()
    val field: ImmutableField get() = fieldMutable

    init {
        get<ScheduledExecutorService>().scheduleAtFixedRate(this::updateField, 15, 15, TimeUnit.MILLISECONDS)
    }

    fun move(direction: Direction) {
        connection.move(direction)
    }

    private fun updateField() {
        fieldMutable = fetchField()
    }

    private fun fetchField(): Field {
        val newField = Field(connection.map.size, connection.map[0].size)
        connection.map.forEachIndexed { w, col ->
            col.forEachIndexed { h, cell ->
                cell.forEach { el ->
                    newField.add(w, h, MapElement(el.presentationId))
                }
            }
        }
        return newField
    }
}
