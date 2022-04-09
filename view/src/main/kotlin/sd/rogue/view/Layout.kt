package sd.rogue.view

import javax.swing.JPanel

abstract class Layout : JPanel() {
    abstract fun activate()
    abstract fun deactivate()
    abstract fun close()
}
