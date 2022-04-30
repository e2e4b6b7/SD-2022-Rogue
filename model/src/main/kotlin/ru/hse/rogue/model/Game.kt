package ru.hse.rogue.model

import ru.hse.rogue.model.connection.ModelCharacterConnection
import ru.hse.rogue.model.connection.ModelCharacterConnectionImpl
import ru.hse.rogue.model.level.Level
import ru.hse.rogue.model.npc.NPCController
import kotlin.concurrent.thread


class Game(private val level: Level) {
    val playerCharacterModelConnection: ModelCharacterConnection =
        ModelCharacterConnectionImpl(level.map, level.player)

    init {
        level.mobs.forEach {
            // FIX BEHAVIOUR
            thread(start = true, isDaemon = true) {
                NPCController(ModelCharacterConnectionImpl(level.map, it.character), it.behaviour).run()
            }
        }
    }
}
