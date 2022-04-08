package sd.rogue.view.timers

class Timer(private val step: Long) {
    private var next: Long = System.currentTimeMillis() + step

    fun needStep(): Boolean {
        val cur = System.currentTimeMillis()
        if (cur >= next) {
            if (cur >= 2 * step + next) {
                // To prevent speedup after unexpected freeze
                next = cur + step
            } else {
                next += step
            }
            return true
        }
        return false
    }
}
