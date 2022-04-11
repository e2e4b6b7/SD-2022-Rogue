package sd.rogue.view.timers

import java.lang.System.currentTimeMillis
import java.lang.Thread.sleep

class SleepingTimer(private val step: Long) {
    private var next: Long = currentTimeMillis() + step

    fun await() {
        var cur = currentTimeMillis()
        while (cur < next) {
            sleep(next - cur)
            cur = currentTimeMillis()
        }
        if (cur >= 2 * step + next) {
            // To prevent speedup after unexpected freeze
            next = cur + step
        } else {
            next += step
        }
    }
}
