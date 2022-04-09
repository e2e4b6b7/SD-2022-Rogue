package sd.rogue.view.paintable.serialization

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import sd.rogue.view.image.ImageLoader
import sd.rogue.view.paintable.*
import java.awt.Color
import java.nio.file.Files
import java.nio.file.Path


@Serializable
sealed class PaintableSerialized {
    abstract fun create(): Paintable
}

@Serializable
@SerialName("Mono")
data class MonoColorPaintableSerialized(val r: Int, val g: Int, val b: Int) : PaintableSerialized() {
    override fun create() = MonoPaintable(Color(r, g, b))
}

@Serializable
@SerialName("Image")
data class ImagePaintableSerialized(val path: String, val policy: String) : PaintableSerialized() {
    override fun create() = when (policy) {
        "Crop" -> ImageCropPaintable(ImageLoader.get(Path.of(path)))
        "ScaleFull" -> ImageScaleFullPaintable(ImageLoader.get(Path.of(path)))
        "Scale" -> ImageScalePaintable(ImageLoader.get(Path.of(path)))
        else -> error("Unknown policy")
    }
}

@Serializable
@SerialName("Sprite")
data class SpritePaintableSerialized(val pathTemplate: String, val stepMillis: Long) : PaintableSerialized() {
    override fun create(): Paintable {
        val images = generateSequence(1) { it + 1 }
            .map { Path.of(pathTemplate.replace("$", it.toString())) }
            .takeWhile { Files.exists(it) }
            .map { ImageLoader.get(it) }
            .toList()
        require(images.isNotEmpty())
        return SpritePaintable(images, stepMillis)
    }
}
