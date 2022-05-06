package ru.hse.rogue.model.level.mobfactory

import ru.hse.rogue.model.gameobject.Arm
import ru.hse.rogue.model.gameobject.character.*
import ru.hse.rogue.model.level.Mob
import ru.hse.rogue.model.npc.behaviour.*
import ru.hse.rogue.model.npc.state.NormalState
import ru.hse.rogue.model.npc.state.State

object ManLikeMobFactory : AbstractMobFactory {
    private val RANGE_HEALTH = (10u..40u)

    override fun createAggressive(playerCharacter: ImmutableCharacter): Mob {
        val behaviour = AggressiveStupidHunter(playerCharacter, (5..8).random())
        val character = CharacterImpl(RANGE_HEALTH.random(), "Zombie")
        val weapon = Arm("Zombie Arm", 2, "Zombie Arm", 10)
        character.pickInventory(weapon)
        character.useInventory(weapon.id)
        return Mob(character, behaviour, NormalState(State.DEFAULT_HEALTH_THRESH_HOLD))
    }

    override fun createCoward(playerCharacter: ImmutableCharacter): Mob {
        val behaviour = CowardWalker(playerCharacter, (5..8).random())
        val character = CharacterImpl(RANGE_HEALTH.random(), "Robot")
        return Mob(character, behaviour, NormalState(State.DEFAULT_HEALTH_THRESH_HOLD))
    }

    override fun createFriendly() = Mob(
        CharacterImpl(RANGE_HEALTH.random(), "Villager"),
        FriendlyStander,
        NormalState(State.DEFAULT_HEALTH_THRESH_HOLD)
    )

    override fun createAggressiveCloneable(playerCharacter: ImmutableCharacter): Mob {
        val behaviour = AggressiveStupidHunter(playerCharacter, (5..8).random())
        val character = CloneableCharacter(RANGE_HEALTH.random(), presentationId = "Slime")
        val weapon = Arm("Slime toxin", 2, "Slime toxin")
        character.pickInventory(weapon)
        character.useInventory(weapon.id)
        return Mob(character, behaviour, NormalState(State.DEFAULT_HEALTH_THRESH_HOLD))
    }
}
