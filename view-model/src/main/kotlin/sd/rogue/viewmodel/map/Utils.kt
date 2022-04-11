package sd.rogue.viewmodel.map

import sd.rogue.viewmodel.PaintableID

/**
 * Iterate through all elements on map.
 * For each single cell iterate from back to front elements.
 */
inline fun ImmutableField.forEach(action: (Int, Int, PaintableID) -> Unit) {
    elements.forEachIndexed { w, column ->
        column.forEachIndexed { h, point ->
            point.forEach { el ->
                action(w, h, el.view)
            }
        }
    }
}
