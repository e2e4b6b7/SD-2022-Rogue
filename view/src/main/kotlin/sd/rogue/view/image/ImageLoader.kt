package sd.rogue.view.image

import java.awt.Image
import java.nio.file.Path
import javax.imageio.ImageIO

/**
 * Object responsible for uploading images by path and caching them.
 */
object ImageLoader {
    private val cache = mutableMapOf<Path, Image>()

    fun get(path: Path) = cache.getOrPut(path) { MultiResolutionCachedImage(ImageIO.read(path.toFile())) }
}
