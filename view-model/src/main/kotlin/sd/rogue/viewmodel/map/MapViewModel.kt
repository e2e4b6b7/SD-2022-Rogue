package sd.rogue.viewmodel.map

class MapViewModel {
    private val fieldMutable = Field(25, 16)
    val field: ImmutableField get() = fieldMutable

    private var x = 0
    private var y = 0

    private val hero = MapElement(2, "Main Hero").also {
        fieldMutable.add(0, 0, it)
    }

    init {
        repeat(25) { w ->
            repeat(16) { h ->
                fieldMutable.add(w, h, MapElement(0, "Grass"))
            }
        }
    }

    fun map() = fieldMutable

    fun move(dx: Int, dy: Int) {
        fieldMutable.remove(x, y, hero)
        x += dx
        y += dy
        fieldMutable.add(x, y, hero)
    }
}
