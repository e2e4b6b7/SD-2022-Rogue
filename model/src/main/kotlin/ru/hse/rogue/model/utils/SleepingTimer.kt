package ru.hse.rogue.model.utils

class SleepingTimer(private val step: Long) {
    private var next: Long = System.currentTimeMillis() + step

    fun await() {
        val cur = System.currentTimeMillis()
        if (cur >= next) {
            if (cur >= 2 * step + next) {
                // To prevent speedup after unexpected freeze
                next = cur + step
            } else {
                next += step
            }
        } else {
            Thread.sleep(next - cur)
        }
    }
}
