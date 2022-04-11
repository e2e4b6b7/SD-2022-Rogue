package sd.rogue.view

import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import sd.rogue.viewmodel.LayoutID
import sd.rogue.viewmodel.LayoutViewModel
import javax.swing.JFrame


/**
 * Controller of current layouts stack and tranfer of control between them.
 */
class LayoutController(private val frame: JFrame) : KoinComponent {
    private val layoutStack = Stack<Layout>()
    private val factories = HashMap<LayoutID, LayoutFactory>()

    init {
        get<LayoutViewModel>().onLayoutChange { layoutChange ->
            when (layoutChange) {
                is LayoutViewModel.LayoutChange.PopLayout -> popLayout()
                is LayoutViewModel.LayoutChange.PushLayout -> pushLayout(layoutChange.layout)
                is LayoutViewModel.LayoutChange.ReplaceLayout -> replaceLayout(layoutChange.layout)
            }
        }
    }

    /**
     * Register layout with specified id.
     */
    fun registerLayout(id: LayoutID, factory: LayoutFactory) {
        factories[id] = factory
    }

    /**
     * Set first layout before displaying.
     */
    fun initialize(initialLayoutID: LayoutID) {
        addLayout(initialLayoutID)
        activateLayout()
    }

    private fun pushLayout(id: LayoutID) {
        deactivateLayout()
        addLayout(id)
        activateLayout()
    }

    private fun popLayout() {
        deactivateLayout()
        deleteLayout()
        activateLayout()
    }

    private fun replaceLayout(id: LayoutID) {
        deactivateLayout()
        deleteLayout()
        addLayout(id)
        activateLayout()
    }

    private fun deactivateLayout() {
        layoutStack.last().deactivate()
        frame.remove(layoutStack.last())
    }

    private fun activateLayout() {
        layoutStack.last().activate()
        frame.add(layoutStack.last())
    }

    private fun deleteLayout() {
        layoutStack.last().close()
        layoutStack.removeLast()
    }

    private fun addLayout(id: LayoutID) {
        val factory = factories[id] ?: error("No such layout")
        layoutStack.add(factory())
    }
}
