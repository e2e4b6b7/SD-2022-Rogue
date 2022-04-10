package sd.rogue.view.assets

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement
import sd.rogue.view.paintable.Paintable
import sd.rogue.view.paintable.TransparentPaintable
import sd.rogue.view.paintable.serialization.PaintableSerialized
import java.nio.file.Path
import kotlin.io.path.readText

/**
 * Object responsible for uploading and managing of assets from config.
 */
object Assets {
    private val assets = mutableMapOf<String, Paintable>()

    fun get(id: String) = assets.getOrDefault(id, TransparentPaintable)

    fun load(path: Path) {
        val json = Json.parseToJsonElement(path.readText())
        val assetsSerial = Json.decodeFromJsonElement<Map<String, PaintableSerialized>>(json)
        assetsSerial.forEach { (k, v) ->
            assets[k] = v.create()
        }
    }
}
