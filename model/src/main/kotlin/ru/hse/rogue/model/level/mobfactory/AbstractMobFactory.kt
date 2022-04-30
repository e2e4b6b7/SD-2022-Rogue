package ru.hse.rogue.model.level.mobfactory

import ru.hse.rogue.model.level.Mob
import ru.hse.rogue.model.gameobject.character.ImmutableCharacter

/** Factory of mobs with some type of behaviour */
interface AbstractMobFactory {
    /** Create [Mob] with aggressive behaviour */
    fun createAggressive(playerCharacter: ImmutableCharacter): Mob
    /** Create [Mob] with coward behaviour */
    fun createCoward(playerCharacter: ImmutableCharacter): Mob
    /** Create [Mob] with friendly behaviour */
    fun createFriendly(): Mob

    /** Create [Mob] with random behaviour */
    fun createRandomMob(playerCharacter: ImmutableCharacter) = when ((0..2).random()) {
        0 -> createAggressive(playerCharacter)
        1 -> createCoward(playerCharacter)
        2 -> createFriendly()
        else -> throw RuntimeException()
    }
}