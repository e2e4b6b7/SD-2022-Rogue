package ru.hse.rogue.model.level.mobfactory

import ru.hse.rogue.model.level.Mob
import ru.hse.rogue.model.gameobject.character.ImmutableCharacter

/** Factory of mobs with some type of behaviour */
interface AbstractMobFactory {
    fun createAggressive(playerCharacter: ImmutableCharacter): Mob
    fun createCoward(playerCharacter: ImmutableCharacter): Mob
    fun createFriendly(): Mob

    fun createRandomMob(playerCharacter: ImmutableCharacter) = when ((0..2).random()) {
        0 -> createAggressive(playerCharacter)
        1 -> createCoward(playerCharacter)
        2 -> createFriendly()
        else -> throw RuntimeException()
    }
}
