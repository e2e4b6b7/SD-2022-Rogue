package ru.hse.rogue.model.npc.behaviour

import ru.hse.rogue.model.connection.ModelCharacterConnection
import ru.hse.rogue.model.gameobject.character.Character
import ru.hse.rogue.model.gameobject.SearchId
import ru.hse.rogue.model.map.MapElement
import ru.hse.rogue.model.map.Position
import ru.hse.rogue.model.utils.isInBounds

/** Interface that is responsible for what actions character will do*/
interface Behaviour {
    /** Make an action based on modelConnection */
    fun doAnything(modelConnection: ModelCharacterConnection)
}
