package ru.hse.rogue.model.gameobject.character

import ru.hse.rogue.model.gameobject.*
import java.util.*


class Character(private var maxHealth: UInt): Searchable {
    var curHealth = maxHealth
    private set

    private val _arms = mutableListOf<Arm>()
    val arms: List<Arm> = _arms
    var curArm: Arm? = null
    private set

    private val _clothes = mutableListOf<Cloth>()
    val clothes: List<Cloth> = _clothes
    private val _curClothes = mutableListOf<Cloth>()
    val curClothes: List<Cloth> = _curClothes

    private fun healthDecrease(harm: UInt) {
        curHealth = maxOf(0u, curHealth - harm)
    }

    private fun healthIncrease(healthAddition: UInt) {
        check(curHealth <= maxHealth)
        curHealth = minOf(maxHealth, curHealth + healthAddition)
    }

    fun isAlive() = curHealth > 0u

    fun pick(item: Pickable): Boolean {
        return when (item) {
            is Arm -> {
                _arms.add(item)
                true
            }
            is Cloth -> {
                _clothes.add(item)
                true
            }
            is ExtraHealth -> healthIncrease(item.health)
        }
    }

    fun wear(clothListIndex: Int) {
        _curClothes.add(clothes[clothListIndex])
    }

    fun takeOff(curClothListIndex: Int) {
        _curClothes.removeAt(curClothListIndex)
    }

    fun useArm(armListIndex: Int) {
        curArm = arms[armListIndex]
    }

    fun attack(other: Character) {
        other.healthDecrease(curArm?.harm ?: 0u)
    }

    override val id: SearchId = UUID.randomUUID()
}
