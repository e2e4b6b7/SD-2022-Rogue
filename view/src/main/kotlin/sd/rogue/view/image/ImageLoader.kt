package sd.rogue.view.image

import java.awt.Image
import java.nio.file.Path
import javax.imageio.ImageIO

object ImageLoader {
    private val cache = mutableMapOf<Path, Image>()

    fun get(path: Path) = cache.getOrPut(path) { MultiResolutionCachedImage(ImageIO.read(path.toFile())) }

//    data class CachedImage(val baseImage: Image) {
//        val scalableImage by lazy { MultiResolutionCachedImage(baseImage) }
//    }
}
