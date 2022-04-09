package sd.rogue.viewmodel.characteristics

class CharacteristicsViewModel {
    private val characteristicsMutable = mutableListOf<Characteristic>()

    val characteristics: List<Characteristic> get() = characteristicsMutable

    init {
        characteristicsMutable.add(Characteristic("Health Icon", "120"))
        characteristicsMutable.add(Characteristic("Mana Icon", "25"))
    }
}
