package sd.rogue.viewmodel.map

/**
 * Interface of field for view.
 */
interface ImmutableField {
    val width: Int
    val height: Int
    val elements: List<List<Iterable<MapElement>>>
}
