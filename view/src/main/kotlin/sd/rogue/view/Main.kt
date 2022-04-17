package sd.rogue.view

import org.koin.core.context.startKoin
import sd.rogue.view.assets.Assets
import sd.rogue.view.timers.SleepingTimer
import sd.rogue.viewmodel.GameConfig
import sd.rogue.viewmodel.init
import java.awt.GraphicsEnvironment
import java.awt.Toolkit
import java.nio.file.Path
import javax.swing.JFrame


typealias Stack<T> = ArrayList<T>
typealias LayoutFactory = () -> Layout


fun repaintCycle(frame: JFrame) {
    val timer = SleepingTimer(15)
    while (true) {
        timer.await()
        frame.repaint()
    }
}

fun main() {
    val config = GameConfig(5, 63, 40)

    startKoin {
        modules(init(config))
    }

    Assets.load(Path.of("assets.json"))

    val frame = JFrame("Rogue").apply {
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
//        GraphicsEnvironment.getLocalGraphicsEnvironment().defaultScreenDevice.fullScreenWindow = this
        size = Toolkit.getDefaultToolkit().screenSize
    }

    LayoutController(frame).apply {
        registerLayout("Main", ::MainLayout)
        initialize("Main")
    }

    frame.isVisible = true

    repaintCycle(frame)
}
