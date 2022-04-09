package sd.rogue.view

import java.awt.Toolkit

val ppi = Toolkit.getDefaultToolkit().screenResolution
const val defaultPPI = 96.0

val Int.dip: Int
    get() = ((this / defaultPPI) * ppi).toInt()
