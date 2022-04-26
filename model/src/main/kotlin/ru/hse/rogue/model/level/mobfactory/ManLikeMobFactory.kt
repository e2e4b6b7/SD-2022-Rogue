package ru.hse.rogue.model.level.mobfactory

import ru.hse.rogue.model.gameobject.Arm
import ru.hse.rogue.model.gameobject.character.CharacterImpl
import ru.hse.rogue.model.gameobject.character.ImmutableCharacter
import ru.hse.rogue.model.level.Mob
import ru.hse.rogue.model.npc.behaviour.*

class ManLikeMobFactory : AbstractMobFactory {
    companion object {
        private val RANGE_HEALTH = (10u..40u)
    }

    override fun createAggressive(playerCharacter: ImmutableCharacter): Mob {
        val behaviour = AggressiveStupidHunter(playerCharacter, (5..8).random())
        val character = CharacterImpl(RANGE_HEALTH.random(), "Zombie")
        val weapon = Arm("Zombie Arm", 2, "Zombie Arm", 10)
        character.pickInventory(weapon)
        character.useInventory(weapon.id)
        return Mob(character, behaviour)
    }

    override fun createCoward(playerCharacter: ImmutableCharacter): Mob {
        val behaviour = CowardWalker(playerCharacter, (5..8).random())
        val character = CharacterImpl(RANGE_HEALTH.random(), "Robot")
        return Mob(character, behaviour)
    }

    override fun createFriendly() = Mob(CharacterImpl(RANGE_HEALTH.random(), "Villager"), FriendlyStander)
}
