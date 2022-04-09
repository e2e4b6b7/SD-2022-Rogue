package sd.rogue.viewmodel

class EventManager<T> {
    private val subscribers = mutableListOf<(T) -> Unit>()

    fun trigger(data: T) {
        subscribers.forEach { it(data) }
    }

    fun subscribe(subscriber: (T) -> Unit): Disposable {
        subscribers.add(subscriber)
        return Disposable { subscribers.remove(subscriber) }
    }
}
