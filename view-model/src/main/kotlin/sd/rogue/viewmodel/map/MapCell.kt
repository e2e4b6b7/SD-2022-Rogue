package sd.rogue.viewmodel.map

/**
 * Elements on single map state sorted in priority order.
 */
class MapCell : Iterable<MapElement> {
    private val elements = mutableListOf<MapElement>()

    fun add(element: MapElement) {
        elements.add(element)
    }

    fun remove(element: MapElement) {
        elements.remove(element)
    }

    override fun iterator() = elements.iterator()
}
