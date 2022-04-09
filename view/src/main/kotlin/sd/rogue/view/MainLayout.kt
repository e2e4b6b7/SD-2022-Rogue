package sd.rogue.view

import java.awt.BorderLayout
import java.awt.Dimension
import javax.swing.JPanel


class MainLayout : Layout() {
    init {
        val characteristicsView = CharacteristicsView()
        val inventoryView = InventoryView().apply {
            preferredSize = Dimension(0, Int.MAX_VALUE)
        }
        val leftPanel = JPanel().apply {
            layout = BorderLayout()
            preferredSize = Dimension(200.dip, Int.MAX_VALUE)
            add(characteristicsView, BorderLayout.NORTH)
            add(inventoryView, BorderLayout.CENTER)
        }
        val mapView = MapView().apply {
            preferredSize = Dimension(Int.MAX_VALUE, Int.MAX_VALUE)
        }
        layout = BorderLayout()
        add(mapView, BorderLayout.CENTER)
        add(leftPanel, BorderLayout.WEST)
    }

    override fun activate() {}

    override fun deactivate() {}

    override fun close() {}
}
