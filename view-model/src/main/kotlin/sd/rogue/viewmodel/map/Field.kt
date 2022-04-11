package sd.rogue.viewmodel.map

/**
 * Map state with all objects on it.
 */
class Field(w: Int, h: Int) : ImmutableField {
    private val elementsMutable = Array(w) { Array(h) { MapCell() }.asList() }.asList()

    override val width get() = elementsMutable.size
    override val height get() = elementsMutable[0].size
    override val elements get() = elementsMutable

    fun add(w: Int, h: Int, element: MapElement) {
        elementsMutable[w][h].add(element)
    }

    fun remove(w: Int, h: Int, element: MapElement) {
        elementsMutable[w][h].remove(element)
    }
}
