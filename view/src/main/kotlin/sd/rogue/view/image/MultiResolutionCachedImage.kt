package sd.rogue.view.image

import java.awt.Image
import java.awt.image.AbstractMultiResolutionImage

class MultiResolutionCachedImage(private val baseImg: Image) : AbstractMultiResolutionImage() {
    private val cache = mutableMapOf<Resolution, Image>()

    override fun getResolutionVariant(destImageWidth: Double, destImageHeight: Double): Image {
        val resolution = Resolution(destImageWidth.toInt(), destImageHeight.toInt())
        return cache.getOrPut(resolution) {
            baseImg.getScaledInstance(resolution.width, resolution.height, SCALE_DEFAULT)
        }
    }

    override fun getResolutionVariants() = listOf(baseImg)

    override fun getBaseImage() = baseImg

    data class Resolution(val width: Int, val height: Int)
}
