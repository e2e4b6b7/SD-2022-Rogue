package sd.rogue.view

import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import sd.rogue.view.assets.Assets
import sd.rogue.viewmodel.inventory.InventoryViewModel
import java.awt.*
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import javax.swing.JComponent

/**
 * View displaying component with player's inventory.
 */
class InventoryView : JComponent(), KoinComponent {
    private val model: InventoryViewModel = get()
    private val background = Assets.get("Inventory Background")

    private var itemsRects = emptyList<Rectangle>()

    init {
        addMouseListener(object : MouseListener {
            override fun mouseClicked(e: MouseEvent) {
                val p = e.point
                val idx = itemsRects.indexOfFirst { it.contains(p) }
                if (idx == -1) return
                model.interact(idx)
            }

            override fun mousePressed(e: MouseEvent) {}
            override fun mouseReleased(e: MouseEvent) {}
            override fun mouseEntered(e: MouseEvent) {}
            override fun mouseExited(e: MouseEvent) {}
        })
    }

    override fun paintComponent(g: Graphics) {
        background.paint(g)

        val newItemRects = mutableListOf<Rectangle>()

        var y = padding
        for (item in model.items) {
            Assets.get(item.icon).paint(g.create(padding, y, lineHeight, lineHeight))

            g.color = if (item.enabled) enabledTextColor else disabledTextColor
            g.font = textFont
            g.drawString(item.description, padding + textXOffset, y + textYOffset)

            newItemRects.add(Rectangle(padding, y, g.clipBounds.width - 2 * padding, lineHeight))

            y += lineHeight + interLine
        }

        itemsRects = newItemRects
    }

    private companion object {
        val padding = 10.dip
        val lineHeight = 25.dip
        val interLine = 10.dip
        val textXOffset = 30.dip
        val textYOffset = 23.dip
        val disabledTextColor: Color = Color.WHITE
        val enabledTextColor: Color = Color.RED
        val textFont = Font(null, Font.PLAIN, 25)
    }
}
