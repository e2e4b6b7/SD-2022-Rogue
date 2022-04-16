package ru.hse.rogue.model.gameobject.character

import ru.hse.rogue.model.gameobject.*

/** Interface for watching character game object */
interface ImmutableCharacter : Searchable {
    /** Current health of the character*/
    val curHealth: UInt

    /** Which inventory items has been picked by character*/
    val inventory: List<Inventory>

    /** Which inventory items is currently using by a character*/
    val usingInventory: List<Inventory>

    /** Return true if character is alive, false otherwise */
    fun isAlive(): Boolean = curHealth > 0u
}

/** Character game object interface, which can be controlled by player on NPC */
interface Character : ImmutableCharacter {
    /** Decrease health of character on [harm]*/
    fun healthDecrease(harm: UInt)

    /** Pick [item] to the inventory */
    fun pickInventory(item: Inventory)

    /** Add inventory to the currently used, if character has inventory with this [inventory]
     *  Returns true if character has this inventory and inventory is used now, false otherwise*/
    fun useInventory(inventoryId: SearchId): Boolean

    /** Disable inventory, if character is using inventory with this [inventoryId] now.
     *  Returns true if character is using this inventory, false otherwise*/
    fun disableInventory(inventoryId: SearchId): Boolean

    /** Attack another character */
    fun attack(other: Character)
}
