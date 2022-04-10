package ru.hse.rogue.model.gameobject

import ru.hse.rogue.model.level.AssetsId
import java.util.UUID

typealias PresentationId = Int
/** Interface for representing game object*/
interface GameObject {
    val presentationId: PresentationId
}

/** Class for representing empty space in the position */
object FreeSpace: GameObject {
    override val presentationId: PresentationId
        get() = AssetsId.get("Grass")
}

/** Class for representing wall in the position. Character can not move through it */
object Wall: GameObject {
    override val presentationId: PresentationId
        get() = AssetsId.get("Wall")
}

typealias SearchId = UUID

/** Interface for object, which can be search by [id] */
interface Searchable: GameObject {
    val id: SearchId
}
