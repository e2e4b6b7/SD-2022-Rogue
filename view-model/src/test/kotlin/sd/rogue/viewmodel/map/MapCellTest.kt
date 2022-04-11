package sd.rogue.viewmodel.map

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class MapCellTest {

    @Test
    fun addRemoveTest() {
        val a = MapElement(0, "Hehe")
        val b = MapElement(3, "Haha")
        val c = MapElement(7, "Hihi")
        val cell = MapCell()
        cell.add(a)
        cell.add(b)
        cell.remove(b)
        cell.add(b)
        cell.add(c)
    }

    @Test
    fun iteratorTest() {
        val a = MapElement(0, "Hehe")
        val b = MapElement(3, "Haha")
        val c = MapElement(7, "Hihi")
        val cell = MapCell()
        cell.add(a)
        cell.add(b)
        cell.remove(b)
        cell.add(c)
        cell.add(b)

        val correct = listOf(a, b, c)
        var idx = 0
        for (el in cell) {
            assertEquals(el, correct[idx])
            idx += 1
        }
    }
}
