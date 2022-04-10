package ru.hse.rogue.model.gameobject.character

import ru.hse.rogue.model.gameobject.*
import java.util.*



class Character(val maxHealth: UInt) : Searchable {
    var curHealth: UInt = maxHealth
    private set
    get() = field + _usingInventory.sumOf { it.characteristics.getOrDefault(CharacteristicType.HEALTH, 0) }.toUInt()

    val armour: UInt get() = TODO("Add using armour in attacks")

    private val _inventory = mutableListOf<Inventory>(ExtraHealth(this.maxHealth.toInt()))
    val inventory: List<Inventory> get() = _inventory
    private val _usingInventory = mutableListOf<Inventory>()

    val usingInventory: List<Inventory> get() = _usingInventory

    private fun healthDecrease(harm: UInt) {
        var mutableHarm = harm.toInt()
        for (invent in _usingInventory) {
            if (CharacteristicType.HEALTH in invent.characteristics) {
                // now only ExtraHealth contains HEALTH
                val harmForInvent = minOf(harm.toInt(), invent.characteristics[CharacteristicType.HEALTH]!!)
                if (harmForInvent > 0) {
                    invent.characteristics[CharacteristicType.HEALTH] =
                        invent.characteristics[CharacteristicType.HEALTH]!! - harmForInvent
                    mutableHarm -= harmForInvent
                    if (mutableHarm == 0)
                        return
                }
            }
        }
        curHealth = maxOf(0u, curHealth - mutableHarm.toUInt())
    }

    fun isAlive() = curHealth > 0u

    fun pickInventory(item: Inventory) {
        _inventory.add(item)
    }

    fun useInventory(item: Inventory) {
        _usingInventory.add(item)
    }

    fun disableInventory(clothId: SearchId) {
        _usingInventory.removeIf{ it.id == clothId }
    }

    fun attack(other: Character) {
        other.healthDecrease(_usingInventory.sumOf {
            it.characteristics.getOrDefault(CharacteristicType.HARM, 0)
        }.toUInt())
    }

    override val id: SearchId = UUID.randomUUID()
}
