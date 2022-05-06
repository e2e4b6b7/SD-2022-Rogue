package ru.hse.rogue.model.level.mobfactory

import ru.hse.rogue.model.gameobject.Arm
import ru.hse.rogue.model.gameobject.character.*
import ru.hse.rogue.model.level.Mob
import ru.hse.rogue.model.npc.behaviour.*
import ru.hse.rogue.model.npc.state.NormalState
import ru.hse.rogue.model.npc.state.State

object AnimalFactory : AbstractMobFactory {
    private val RANGE_HEALTH = (10u..40u)

    override fun createAggressive(playerCharacter: ImmutableCharacter): Mob {
        val behaviour = AggressiveStupidHunter(playerCharacter, (5..8).random())
        val character = CharacterImpl(RANGE_HEALTH.random(), "Wolf")
        val weapon = Arm("Wolf fangs", 2, "Wolf fangs", 10)
        character.pickInventory(weapon)
        character.useInventory(weapon.id)
        return Mob(character, behaviour, NormalState(State.DEFAULT_HEALTH_THRESH_HOLD))
    }

    override fun createCoward(playerCharacter: ImmutableCharacter): Mob {
        val behaviour = CowardWalker(playerCharacter, (5..8).random())
        val character = CharacterImpl(RANGE_HEALTH.random(), "Squirrel")
        return Mob(character, behaviour, NormalState(State.DEFAULT_HEALTH_THRESH_HOLD))
    }

    override fun createFriendly() = Mob(
        CharacterImpl(RANGE_HEALTH.random(), "Cat"),
        FriendlyStander,
        NormalState(State.DEFAULT_HEALTH_THRESH_HOLD)
    )

    override fun createAggressiveCloneable(playerCharacter: ImmutableCharacter): Mob {
        val behaviour = AggressiveStupidHunter(playerCharacter, (5..8).random())
        val character = CloneableCharacter(RANGE_HEALTH.random(), presentationId = "Bees")
        val weapon = Arm("Bee sting", 2, "Bee sting")
        character.pickInventory(weapon)
        character.useInventory(weapon.id)
        return Mob(character, behaviour, NormalState(State.DEFAULT_HEALTH_THRESH_HOLD))
    }
}
