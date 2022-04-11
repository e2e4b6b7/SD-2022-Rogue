package ru.hse.rogue.model.gameobject.character

/** Type of characteristics */
enum class CharacteristicType {
    HEALTH,
    HARM,
    ARMOUR
}

/** Class for representing characteristic with type [characteristic] and value [startValue]*/
data class Characteristic(val characteristic: CharacteristicType, private val startValue: Int) {
    var value: Int = startValue
        internal set
}
