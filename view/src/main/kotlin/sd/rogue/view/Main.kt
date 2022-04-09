package sd.rogue.view

import org.koin.core.context.startKoin
import sd.rogue.view.assets.Assets
import sd.rogue.view.timers.SleepingTimer
import sd.rogue.viewmodel.viewModelModule
import java.awt.GraphicsEnvironment
import java.lang.System.currentTimeMillis
import java.nio.file.Path
import javax.swing.JFrame

typealias Stack<T> = ArrayList<T>
typealias LayoutFactory = () -> Layout


fun repaintCycle(frame: JFrame) {
    val timer = SleepingTimer(15)
    var p = currentTimeMillis()
    while (true) {
        timer.await()
        val c = currentTimeMillis()
        if (c - p > 16) println(c - p)
        p = c
        frame.repaint()
    }
}

fun main() {
    startKoin {
        modules(viewModelModule)
    }

    Assets.load(Path.of("assets.json"))

    val frame = JFrame("Rogue").apply {
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        GraphicsEnvironment.getLocalGraphicsEnvironment().defaultScreenDevice.fullScreenWindow = this
    }

    LayoutController(frame).apply {
        registerLayout("Main", ::MainLayout)
        initialize("Main")
    }

    frame.isVisible = true

    repaintCycle(frame)
}