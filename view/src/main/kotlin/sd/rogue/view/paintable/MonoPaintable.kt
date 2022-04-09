package sd.rogue.view.paintable

import java.awt.Color
import java.awt.Graphics

class MonoPaintable(private val color: Color) : Paintable {
    override fun paint(g: Graphics) {
        g.color = color
        with(g.clipBounds) {
            g.fillRect(x, y, width, height)
        }
    }
}


