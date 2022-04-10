package ru.hse.rogue.model.level

import ru.hse.rogue.model.map.GameMap
import ru.hse.rogue.model.gameobject.character.Character

data class Level(
    val map: GameMap,
    val player : Character,
    val NPCCharacters: MutableList<Character> = mutableListOf()
)
