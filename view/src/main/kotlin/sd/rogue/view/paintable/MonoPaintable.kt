package sd.rogue.view.paintable

import java.awt.Color
import java.awt.Graphics

/**
 * Paint rectangle fully in single color.
 */
class MonoPaintable(private val color: Color) : Paintable {
    override fun paint(g: Graphics) = with(g.clipBounds) {
        g.color = color
        g.fillRect(x, y, width, height)
    }
}



