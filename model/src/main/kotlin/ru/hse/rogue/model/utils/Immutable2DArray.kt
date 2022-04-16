package ru.hse.rogue.model.utils

open class Immutable2DArray<T>(
    val outerSize: Int,
    val innerSize: Int,
    generate: (outerIndex: Int, innerIndex: Int) -> T
) {
    private var _array2D: List<List<T>>

    init {
        _array2D = MutableList(outerSize) { outerIndex ->
            MutableList(innerSize) { innerIndex ->
                generate(outerIndex, innerIndex)
            }
        }
    }

    operator fun get(outerIndex: Int, innerIndex: Int): T = _array2D[outerIndex][innerIndex]
}

inline fun <T> Immutable2DArray<T>.forEachIndexed(
    action: (outerIndex: Int, innerIndex: Int, T) -> Unit
) {
    for (outer in 0 until outerSize)
        for (inner in 0 until innerSize)
            action(outer, inner, this[outer, inner])
}
