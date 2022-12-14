package utils

import kotlin.math.sign

data class Coords(
    val row: Int,
    val col: Int,
) {
    companion object {
        /** [end] inclusive */
        fun path(start: Coords, end: Coords) = sequence {
            val r = (end.row - start.row).sign
            val c = (end.col - start.col).sign

            var row = start.row
            var col = start.col

            var dr: Boolean
            var dc: Boolean

            do {
                val coords = Coords(row, col)
                yield(coords)
                dr = row != end.row
                dc = col != end.col
                if (dr) row += r
                if (dc) col += c
            }
            while (dr || dc)
        }
    }

    operator fun plus(coords: Coords) = Coords(
        row = this.row + coords.row,
        col = this.col + coords.col,
    )

    operator fun minus(coords: Coords) = Coords(
        row = this.row - coords.row,
        col = this.col - coords.col,
    )
}

operator fun Array<IntArray>.get(coords: Coords): Int = this[coords.row][coords.col]

operator fun <T> Array<Array<T>>.get(coords: Coords): T = this[coords.row][coords.col]

operator fun Array<IntArray>.set(coords: Coords, value: Int) {
    this[coords.row][coords.col] = value
}

operator fun <T> Array<Array<T>>.set(coords: Coords, value: T) {
    this[coords.row][coords.col] = value
}

operator fun Array<IntArray>.contains(coords: Coords): Boolean {
    return coords.row in this.indices && coords.col in this[coords.row].indices
}

operator fun <T> Array<Array<T>>.contains(coords: Coords): Boolean {
    return coords.row in this.indices && coords.col in this[coords.row].indices
}
