package ru.hse.rogue.model.level

import ru.hse.rogue.model.gameobject.character.Character
import ru.hse.rogue.model.npc.behaviour.Behaviour

/** Class with mob components */
data class Mob(val character: Character, val behaviour: Behaviour)
