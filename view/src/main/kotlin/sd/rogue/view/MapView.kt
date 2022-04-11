package sd.rogue.view

import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import ru.hse.rogue.model.map.Direction
import sd.rogue.view.assets.Assets
import sd.rogue.viewmodel.map.MapViewModel
import sd.rogue.viewmodel.map.forEach
import java.awt.Graphics
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import javax.swing.JPanel

/**
 * View displaying component with current map.
 */
class MapView : JPanel(), KoinComponent {
    private val model: MapViewModel = get()
    private val background = Assets.get("Map Background")

    init {
        isFocusable = true
        addKeyListener(object : KeyListener {
            override fun keyTyped(e: KeyEvent) {}

            override fun keyPressed(e: KeyEvent) {
                when (e.keyCode) {
                    KeyEvent.VK_LEFT -> model.move(Direction.LEFT)
                    KeyEvent.VK_RIGHT -> model.move(Direction.RIGHT)
                    KeyEvent.VK_UP -> model.move(Direction.UP)
                    KeyEvent.VK_DOWN -> model.move(Direction.DOWN)
                }
            }

            override fun keyReleased(e: KeyEvent) {}
        })
    }

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        background.paint(g)
        val edgeSize = minOf(g.clipBounds.height / model.field.height, g.clipBounds.width / model.field.width)
        val paddingH = (g.clipBounds.height - edgeSize * model.field.height) / 2
        val paddingW = (g.clipBounds.width - edgeSize * model.field.width) / 2
        model.field.forEach { w, h, paintable ->
            Assets.get(paintable).paint(g.create(paddingW + w * edgeSize, paddingH + h * edgeSize, edgeSize, edgeSize))
        }
    }
}
