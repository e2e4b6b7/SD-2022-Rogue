package ru.hse.rogue.model.level

import ru.hse.rogue.model.gameobject.character.Character
import ru.hse.rogue.model.npc.behaviour.Behaviour
import ru.hse.rogue.model.npc.state.State

/** Class with mob components */
data class Mob(val character: Character, var behaviour: Behaviour, var state: State)
