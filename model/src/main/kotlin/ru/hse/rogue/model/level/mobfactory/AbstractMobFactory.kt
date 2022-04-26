package ru.hse.rogue.model.level.mobfactory

import ru.hse.rogue.model.level.Mob
import ru.hse.rogue.model.gameobject.character.ImmutableCharacter

/** Factory of mobs with some type of behaviour */
interface AbstractMobFactory {
    fun createAggressive(playerCharacter: ImmutableCharacter): Mob
    fun createCoward(playerCharacter: ImmutableCharacter): Mob
    fun createFriendly(): Mob
}
