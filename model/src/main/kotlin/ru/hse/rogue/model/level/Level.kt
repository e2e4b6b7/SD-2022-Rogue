package ru.hse.rogue.model.level

import ru.hse.rogue.model.gameobject.character.Character
import ru.hse.rogue.model.map.GameMap
import ru.hse.rogue.model.npc.behaviour.Behaviour

/** Class for level representation including [map], [player] and [mobs]*/
data class Level(
    val map: GameMap,
    val player: Character,
    val mobs: MutableList<Mob> = mutableListOf()
)
