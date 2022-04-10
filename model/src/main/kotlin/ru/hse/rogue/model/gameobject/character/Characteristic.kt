package ru.hse.rogue.model.gameobject.character

enum class CharacteristicType {
    HEALTH,
    HARM,
    ARMOUR
}

data class Characteristic(val characteristic: CharacteristicType, private val startValue: Int) {
    var value: Int = startValue
    internal set
}
