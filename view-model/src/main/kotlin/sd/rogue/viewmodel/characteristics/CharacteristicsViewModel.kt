package sd.rogue.viewmodel.characteristics

import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import ru.hse.rogue.model.connection.ModelCharacterConnection
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

class CharacteristicsViewModel : KoinComponent {
    private val connection: ModelCharacterConnection = get()

    private var characteristicsMutable = fetchCharacteristics()
    val characteristics: List<Characteristic> get() = characteristicsMutable

    init {
        get<ScheduledExecutorService>().scheduleAtFixedRate(this::updateCharacteristics, 15, 15, TimeUnit.MILLISECONDS)
    }

    private fun updateCharacteristics() {
        characteristicsMutable = fetchCharacteristics()
    }

    private fun fetchCharacteristics(): MutableList<Characteristic> {
        return mutableListOf(
            Characteristic("Health Icon", connection.character.curHealth.toString()),
            Characteristic("Damage", connection.character.curDamage.toString()),
            Characteristic("Level", connection.character.curLevel.toString()),
            Characteristic("Experience", connection.character.curExperience.toString()),
        )
    }
}
