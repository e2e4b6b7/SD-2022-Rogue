package sd.rogue.view.paintable

import java.awt.Graphics

/**
 * Any object that can be painted in the frame.
 */
interface Paintable {
    fun paint(g: Graphics)
}
