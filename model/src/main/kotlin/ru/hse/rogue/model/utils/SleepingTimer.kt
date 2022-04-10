package ru.hse.rogue.model.utils

class SleepingTimer(private val step: Long) {
    private var next: Long = System.currentTimeMillis() + step

    fun await() {
        var cur = System.currentTimeMillis()
        while (cur < next) {
            Thread.sleep(next - cur)
            cur = System.currentTimeMillis()
        }
        if (cur >= 2 * step + next) {
            // To prevent speedup after unexpected freeze
            next = cur + step
        } else {
            next += step
        }
    }
}
