package ru.hse.rogue.model.level

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement
import ru.hse.rogue.model.gameobject.PresentationId
import java.nio.file.Path
import kotlin.io.path.Path
import kotlin.io.path.readText

/**
 * Object responsible for mapping name of GameObject presentation assets to identifier of presentation
 */
object AssetsId {
    private val assets = mutableMapOf<String, PresentationId>()

    operator fun get(name: String) = assets[name] ?: throw IllegalArgumentException("Asset not found")
    init {
        load(Path("assets_id.json"))
    }
    private fun load(path: Path) {
        val json = Json.parseToJsonElement(path.readText())
        val assetsSerial = Json.decodeFromJsonElement<Map<String, PresentationId>>(json)
        assetsSerial.forEach { (k, v) ->
            assets[k] = v
        }
    }
}
