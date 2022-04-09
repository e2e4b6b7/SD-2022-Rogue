package sd.rogue.view.paintable

import java.awt.Graphics
import java.awt.Image

class ImageScalePaintable(private val image: Image) : Paintable {
    override fun paint(g: Graphics) = g.drawImageScale(image)
}
