package ru.hse.rogue.model.gameobject.character

import org.junit.jupiter.api.Test
import ru.hse.rogue.model.gameobject.*
import kotlin.test.*

internal class CharacterImplTest {
    private lateinit var char: Character
    private lateinit var victimChar: Character
    private val startHealth = 10u
    private val extraHealth = 10

    @BeforeTest
    fun charInit() {
        char = CharacterImpl(startHealth)
        victimChar = CharacterImpl(startHealth)
    }

    @Test
    fun `pick inventory`() {
        val extraHealthInventory = ExtraHealth(extraHealth)
        char.pickInventory(extraHealthInventory)
        assertEquals(1, char.inventory.size)
        assertEquals(0, char.usingInventory.size)
        assertEquals(extraHealthInventory.id, char.inventory[0].id)
    }

    @Test
    fun `use inventory that character has`() {
        val extraHealthInventory = ExtraHealth(extraHealth)
        char.pickInventory(extraHealthInventory)
        assertTrue(char.useInventory(extraHealthInventory.id))
        assertEquals(1, char.usingInventory.size)
        assertEquals(extraHealthInventory.id, char.usingInventory[0].id)
    }

    @Test
    fun `use inventory that character doesn't have`() {
        val extraHealthInventory = ExtraHealth(extraHealth)
        char.pickInventory(extraHealthInventory)
        assertFalse(char.useInventory(SearchId.randomUUID()))
        assertEquals(0, char.usingInventory.size)
    }

    @Test
    fun `disable inventory that character is using`() {
        val extraHealthInventory = ExtraHealth(extraHealth)
        char.pickInventory(extraHealthInventory)
        char.useInventory(extraHealthInventory.id)
        assertTrue(char.disableInventory(extraHealthInventory.id))
        assertEquals(0, char.usingInventory.size)
    }

    @Test
    fun `disable inventory that character isn't using`() {
        val extraHealthInventory = ExtraHealth(extraHealth)
        char.pickInventory(extraHealthInventory)
        char.useInventory(extraHealthInventory.id)
        assertFalse(char.disableInventory(SearchId.randomUUID()))
        assertEquals(1, char.usingInventory.size)
    }

    @Test
    fun `decrease health of character without extra health on harm less than current health`() {
        char.healthDecrease(1u)
        assertEquals(startHealth - 1u, char.curHealth)
    }

    @Test
    fun `decrease health of character without extra health on harm more than current health`() {
        char.healthDecrease(startHealth + 1u)
        assertEquals(0u, char.curHealth)
    }

    @Test
    fun `decrease health of character with 1 extra health on harm less than this extra health`() {
        val extraHealthInventory = ExtraHealth(extraHealth)
        char.pickInventory(extraHealthInventory)
        char.useInventory(extraHealthInventory.id)
        char.healthDecrease(1u)
        assertEquals(extraHealth - 1, char.inventory[0].characteristics[CharacteristicType.HEALTH])
        assertEquals(startHealth + extraHealth.toUInt() - 1u, char.curHealth)
    }

    @Test
    fun `decrease health of character with 1 extra health on harm more than this extra health`() {
        val extraHealthInventory = ExtraHealth(extraHealth)
        char.pickInventory(extraHealthInventory)
        char.useInventory(extraHealthInventory.id)
        char.healthDecrease((extraHealth + 1).toUInt())
        assertEquals(0, extraHealthInventory.characteristics[CharacteristicType.HEALTH])
        assertEquals(startHealth - 1u, char.curHealth)
    }

    @Test
    fun `decrease health of character with 2 extra healths on harm less than 1st extra health`() {
        val extraHealthInventory1 = ExtraHealth(extraHealth)
        val extraHealthInventory2 = ExtraHealth(extraHealth)
        char.pickInventory(extraHealthInventory1)
        char.pickInventory(extraHealthInventory2)
        char.useInventory(extraHealthInventory1.id)
        char.useInventory(extraHealthInventory2.id)
        char.healthDecrease(1u)
        assertEquals(extraHealth - 1, extraHealthInventory1.characteristics[CharacteristicType.HEALTH])
        assertEquals(extraHealth, extraHealthInventory2.characteristics[CharacteristicType.HEALTH])
        assertEquals(startHealth + 2u * extraHealth.toUInt() - 1u, char.curHealth)
    }

    @Test
    fun `decrease health of character with 2 extra healths on harm more than 1st extra health but less than their sum`() {
        val extraHealthInventory1 = ExtraHealth(extraHealth)
        val extraHealthInventory2 = ExtraHealth(extraHealth)
        char.pickInventory(extraHealthInventory1)
        char.pickInventory(extraHealthInventory2)
        char.useInventory(extraHealthInventory1.id)
        char.useInventory(extraHealthInventory2.id)
        char.healthDecrease(extraHealth.toUInt() + 1u)
        assertEquals(0, extraHealthInventory1.characteristics[CharacteristicType.HEALTH])
        assertEquals(extraHealth - 1, extraHealthInventory2.characteristics[CharacteristicType.HEALTH])
        assertEquals(startHealth + extraHealth.toUInt() - 1u, char.curHealth)
    }

    @Test
    fun `decrease health of character with 2 extra healths on harm more than their sum`() {
        val extraHealthInventory1 = ExtraHealth(extraHealth)
        val extraHealthInventory2 = ExtraHealth(extraHealth)
        char.pickInventory(extraHealthInventory1)
        char.pickInventory(extraHealthInventory2)
        char.useInventory(extraHealthInventory1.id)
        char.useInventory(extraHealthInventory2.id)
        char.healthDecrease(2u * extraHealth.toUInt() + 1u)
        assertEquals(0, extraHealthInventory1.characteristics[CharacteristicType.HEALTH])
        assertEquals(0, extraHealthInventory2.characteristics[CharacteristicType.HEALTH])
        assertEquals(startHealth - 1u, char.curHealth)
    }


    @Test
    fun `decrease health of character with 2 extra healths on harm more than their sum, but only 1 was used`() {
        val extraHealthInventory1 = ExtraHealth(extraHealth)
        val extraHealthInventory2 = ExtraHealth(extraHealth)
        char.pickInventory(extraHealthInventory1)
        char.pickInventory(extraHealthInventory2)
        char.useInventory(extraHealthInventory1.id)
        char.healthDecrease(extraHealth.toUInt() + 1u)
        assertEquals(0, extraHealthInventory1.characteristics[CharacteristicType.HEALTH])
        assertEquals(extraHealth, extraHealthInventory2.characteristics[CharacteristicType.HEALTH])
        assertEquals(startHealth - 1u, char.curHealth)
    }

    @Test
    fun `attack other character without arm`() {
        char.attack(victimChar)
        assertEquals(startHealth, victimChar.curHealth)
    }

    @Test
    fun `attack other character with arm and not kill`() {
        val arm = Arm("stick", 1, "")
        char.pickInventory(arm)
        char.useInventory(arm.id)
        char.attack(victimChar)
        assertTrue(victimChar.isAlive())
        assertEquals(startHealth - 1u, victimChar.curHealth)
    }

    @Test
    fun `attack other character with arm and kill`() {
        val arm = Arm("stick", startHealth.toInt() + 1, "")
        char.pickInventory(arm)
        char.useInventory(arm.id)
        char.attack(victimChar)
        assertFalse(victimChar.isAlive())
        assertEquals(0u, victimChar.curHealth)
    }
}
