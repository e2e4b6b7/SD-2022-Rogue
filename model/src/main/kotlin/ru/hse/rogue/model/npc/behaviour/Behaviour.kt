package ru.hse.rogue.model.npc.behaviour

import ru.hse.rogue.model.ModelConnection
import ru.hse.rogue.model.gameobject.Character
import ru.hse.rogue.model.map.MapElement
import ru.hse.rogue.model.map.Position

interface Behaviour {
    fun doAnythingOrNotDo(modelConnection: ModelConnection, character: Character)
}
