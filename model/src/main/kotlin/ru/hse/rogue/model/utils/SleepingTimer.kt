package ru.hse.rogue.model.utils

class SleepingTimer(private val stepMillis: Long) {
    private var next: Long = System.currentTimeMillis() + stepMillis

    fun await() {
        var cur = System.currentTimeMillis()
        while (cur < next) {
            Thread.sleep(next - cur)
            cur = System.currentTimeMillis()
        }
        if (cur >= 2 * stepMillis + next) {
            // To prevent speedup after unexpected freeze
            next = cur + stepMillis
        } else {
            next += stepMillis
        }
    }
}
