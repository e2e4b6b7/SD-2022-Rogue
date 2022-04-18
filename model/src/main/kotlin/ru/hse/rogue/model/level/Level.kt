package ru.hse.rogue.model.level

import ru.hse.rogue.model.gameobject.character.Character
import ru.hse.rogue.model.map.GameMap
import ru.hse.rogue.model.npc.behaviour.Behaviour

/** Class for level representation including [map], [player] and [NPCCharacters]*/
data class Level(
    val map: GameMap,
    val player: Character,
    /** List of NPC spawned on this level */
    val NPCCharacters: MutableList<Pair<Character, Behaviour>> = mutableListOf()
)
