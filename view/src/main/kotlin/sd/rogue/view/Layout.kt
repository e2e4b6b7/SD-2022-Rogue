package sd.rogue.view

import javax.swing.JPanel

/**
 * Layout is the manager for all components displayed on the screen in specified moment.
 */
abstract class Layout : JPanel() {
    /**
     * Called after adding layout on the main frame.
     */
    abstract fun activate()

    /**
     * Called before removing layout from the main frame.
     */
    abstract fun deactivate()

    /**
     * Called before removing layout from the layout stack.
     */
    abstract fun close()
}
