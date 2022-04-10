package sd.rogue.view.paintable

import java.awt.Graphics
import java.awt.Image

/**
 * Paint static image with crop policy.
 */
class ImageCropPaintable(private val image: Image) : Paintable {
    override fun paint(g: Graphics) = g.drawImageCrop(image)
}
