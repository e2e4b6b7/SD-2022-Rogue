package sd.rogue.view

import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import sd.rogue.view.assets.Assets
import sd.rogue.viewmodel.characteristics.CharacteristicsViewModel
import java.awt.*
import javax.swing.JComponent


class CharacteristicsView : JComponent(), KoinComponent {
    private val model: CharacteristicsViewModel = get()
    private val background = Assets.get("Characteristics Background")

    init {
        minimumSize = calcMinimumSize()
        preferredSize = minimumSize
    }

    private fun calcMinimumSize(): Dimension {
        return Dimension(0, padding * 2 + (lineHeight + interLine) * model.characteristics.size - interLine)
    }

    override fun paintComponent(g: Graphics) {
        background.paint(g)

        var y = padding
        for (ch in model.characteristics) {
            Assets.get(ch.icon).paint(g.create(padding, y, lineHeight, lineHeight))

            g.color = textColor
            g.font = textFont
            g.drawString(ch.value, padding + textXOffset, y + textYOffset)

            y += lineHeight + interLine
        }
    }

    private companion object {
        val padding = 20.dip
        val lineHeight = 25.dip
        val interLine = 10.dip
        val textXOffset = 30.dip
        val textYOffset = 23.dip
        val textColor: Color = Color.WHITE
        val textFont = Font(null, Font.PLAIN, 25)
    }
}
