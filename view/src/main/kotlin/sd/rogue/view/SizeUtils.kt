package sd.rogue.view

import java.awt.Toolkit

val ppi = Toolkit.getDefaultToolkit().screenResolution
const val defaultPPI = 96.0

/**
 * Convert from device independent pixels to current device pixels.
 */
val Int.dip: Int
    get() = ((this / defaultPPI) * ppi).toInt()
