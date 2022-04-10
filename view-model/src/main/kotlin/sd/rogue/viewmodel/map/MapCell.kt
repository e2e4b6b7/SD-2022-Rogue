package sd.rogue.viewmodel.map

/**
 * Elements on single map state sorted in priority order.
 */
class MapCell : Iterable<MapElement> {
    private val elements = mutableListOf<MapElement>()

    fun add(element: MapElement) {
        val idx = elements.indexOfFirst { it.priority > element.priority }
        val pos = if (idx == -1) elements.size else idx
        elements.add(pos, element)
    }

    fun remove(element: MapElement) {
        elements.remove(element)
    }

    override fun iterator() = elements.iterator()
}
