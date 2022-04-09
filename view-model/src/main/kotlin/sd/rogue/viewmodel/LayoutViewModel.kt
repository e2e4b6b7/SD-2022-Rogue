package sd.rogue.viewmodel

class LayoutViewModel {
    private val layoutChangeManager = EventManager<LayoutChange>()

    fun onLayoutChange(callback: (LayoutChange) -> Unit): Disposable {
        return layoutChangeManager.subscribe(callback)
    }

    sealed interface LayoutChange {
        data class PushLayout(val layout: LayoutID) : LayoutChange
        data class ReplaceLayout(val layout: LayoutID) : LayoutChange
        object PopLayout : LayoutChange
    }
}
