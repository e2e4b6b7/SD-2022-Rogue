package sd.rogue.viewmodel.map

import sd.rogue.viewmodel.PaintableID

interface ImmutableField {
    val width: Int
    val height: Int
    val elements: List<List<Iterable<MapElement>>>
}

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

inline fun ImmutableField.forEach(action: (Int, Int, PaintableID) -> Unit) {
    elements.forEachIndexed { w, column ->
        column.forEachIndexed { h, point ->
            point.forEach { el ->
                action(w, h, el.view)
            }
        }
    }
}
