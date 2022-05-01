package ru.hse.rogue.model.gameobject.character

import ru.hse.rogue.model.gameobject.*
import java.lang.Double.min
import java.util.*
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.random.Random.Default.nextDouble


/** [Character] implementation class. May be player or NPC with start health */
open class CharacterImpl(startHealth: UInt, override val presentationId: PresentationId = "Hero") : Character {
    private var _curHealth: UInt = startHealth

    final override var curExperience: UInt = 0u
        private set

    final override var curLevel: UInt = 1u
        private set

    /** Current health of the character*/
    override val curHealth: UInt
        get() = _curHealth + _usingInventory.sumOf {
            it.characteristics.getOrDefault(
                CharacteristicType.HEALTH,
                0
            )
        }.toUInt()

    override val curDamage: UInt
        get() = _usingInventory.sumOf {
            it.characteristics.getOrDefault(
                CharacteristicType.HARM,
                0
            )
        }.toUInt()

    private val _inventory = mutableListOf<Inventory>()

    /** Which inventory items has been picked by character*/
    override val inventory: List<Inventory> get() = _inventory

    private val _usingInventory = mutableListOf<Inventory>()

    /** Which inventory items is currently using by a character*/
    override val usingInventory: List<Inventory> get() = _usingInventory

    /** Decrease health of character on [harm]*/
    override fun healthDecrease(harm: UInt) {
        var mutableHarm = harm.toInt()
        for (invent in _usingInventory) {
            if (CharacteristicType.HEALTH in invent.characteristics) {
                val harmForInvent = minOf(mutableHarm, invent.characteristics[CharacteristicType.HEALTH]!!)
                if (harmForInvent > 0) {
                    invent.characteristics[CharacteristicType.HEALTH] =
                        invent.characteristics[CharacteristicType.HEALTH]!! - harmForInvent
                    mutableHarm -= harmForInvent
                    if (mutableHarm == 0)
                        return
                }
            }
        }
        _curHealth = maxOf(0, _curHealth.toInt() - mutableHarm).toUInt()
    }

    override fun experienceIncrease(experienceIncome: UInt) {
        curExperience += experienceIncome
        if (curExperience >= EXP_IN_LEVEL) {
            _curHealth += HEALTH_LEVEL_REWARD
        }
        curLevel += (curExperience / EXP_IN_LEVEL)
        curExperience %= EXP_IN_LEVEL
    }

    /** Pick [item] to the inventory */
    override fun pickInventory(item: Inventory) {
        _inventory.add(item)
    }

    /** Add inventory to the currently used, if character has inventory with this [inventory]
     *  Returns true if character has this inventory and inventory is used now, false otherwise*/
    override fun useInventory(inventoryId: SearchId): Boolean {
        val inventory = _inventory.firstOrNull { it.id == inventoryId } ?: return false
        _usingInventory.add(inventory)
        return true
    }

    /** Attack another character */
    override fun disableInventory(inventoryId: SearchId): Boolean {
        return _usingInventory.removeIf { it.id == inventoryId }
    }

    /** Attack other character. It decreases health by value of this character harm and can stun him */
    override fun attack(other: Character) {
        other.healthDecrease(_usingInventory.sumOf {
            it.characteristics.getOrDefault(CharacteristicType.HARM, 0)
        }.toUInt())
        val prob = min(1.0,
            _usingInventory.sumOf {
                it.characteristics.getOrDefault(CharacteristicType.STUN, 0) / 100.0
            }
        )
        if (!other.isStunned.get()) {
            other.isStunned.set(prob > nextDouble())
        }
    }

    override val isStunned: AtomicBoolean = AtomicBoolean(false)

    override val id: SearchId = UUID.randomUUID()

    companion object {
        private const val EXP_IN_LEVEL = 10u
        const val EXP_PER_KILL = 1u
        private const val HEALTH_LEVEL_REWARD = 10u
    }
}
