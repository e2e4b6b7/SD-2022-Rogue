package sd.rogue.view.paintable

import sd.rogue.view.timers.Timer
import java.awt.Graphics
import java.awt.Image

/**
 * Cyclic sprite with fixed step. Image fitting policy is scale.
 *
 * @param stepMillis Time of single image in millis
 */
class SpritePaintable(private val images: List<Image>, stepMillis: Long) : Paintable {
    private var index = 0
    private val timer = Timer(stepMillis)

    override fun paint(g: Graphics) {
        if (timer.needStep()) {
            index++
            if (index == images.size) index = 0
        }

        g.drawImageScale(images[index])
    }
}
