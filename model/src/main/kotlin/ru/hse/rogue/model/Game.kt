package ru.hse.rogue.model

import ru.hse.rogue.model.connection.*
import ru.hse.rogue.model.level.Level
import ru.hse.rogue.model.npc.NPC
import ru.hse.rogue.model.npc.behaviour.AggressiveRandomWalker
import kotlin.concurrent.thread


class Game(private val level: Level) {
    val modelViewConnection: ModelViewConnection = ModelViewConnectionImpl(level.map)
    val playerCharacterModelConnection: ModelCharacterConnection =
        ModelCharacterConnectionImpl(level.map, level.player)

    init {
        level.NPCCharacters.forEach {
            // FIX BEHAVIOUR
            thread(start = true, isDaemon = true){
                NPC(ModelCharacterConnectionImpl(level.map, it), AggressiveRandomWalker)
            }
        }
    }
}
