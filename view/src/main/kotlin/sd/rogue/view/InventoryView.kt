package sd.rogue.view

import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import sd.rogue.view.assets.Assets
import sd.rogue.viewmodel.inventory.InventoryViewModel
import java.awt.*
import javax.swing.JComponent


class InventoryView : JComponent(), KoinComponent {
    private val model: InventoryViewModel = get()
    private val background = Assets.get("Inventory Background")

    override fun paintComponent(g: Graphics) {
        background.paint(g)

        var y = padding
        for (ch in model.items) {
            Assets.get(ch.icon).paint(g.create(padding, y, lineHeight, lineHeight))

            g.color = textColor
            g.font = textFont
            g.drawString(ch.description, padding + textXOffset, y + textYOffset)

            y += lineHeight + interLine
        }
    }

    private companion object {
        val padding = 10.dip
        val lineHeight = 25.dip
        val interLine = 10.dip
        val textXOffset = 30.dip
        val textYOffset = 23.dip
        val textColor: Color = Color.WHITE
        val textFont = Font(null, Font.PLAIN, 25)
    }
}
