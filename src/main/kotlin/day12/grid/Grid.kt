package day12.grid

import utils.Coords
import utils.contains
import utils.get

class Grid(
    private val elevation: Array<IntArray>,
    val start: Coords,
    val end: Coords,
) {
    companion object {
        private val alphabet = ('a'..'z').toList().toCharArray()
    }

    val rows: Int = elevation.size
    val cols: Int = elevation[0].size

    init {
        require(elevation[start] == 0)
        require(elevation[end] == 26)

        for (row in elevation) {
            require(row.size == cols)
        }
    }

    operator fun get(coords: Coords): Int = elevation[coords]

    operator fun contains(coords: Coords): Boolean = coords in elevation

    override fun toString(): String = elevation
        .mapIndexed { row, ints ->
            ints
                .mapIndexed { col, i ->
                    when (Coords(row, col)) {
                        start -> 'S'
                        end   -> 'E'
                        else  -> alphabet[i]
                    }
                }
                .joinToString(separator = "")
        }
        .joinToString(separator = System.lineSeparator())
}
