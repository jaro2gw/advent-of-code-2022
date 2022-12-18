package day14.cave

import day14.cave.CaveElement.LEAK
import day14.cave.CaveElement.NONE
import day14.cave.CaveElement.ROCK
import day14.cave.CaveElement.SAND
import utils.Coords
import utils.MinMax
import utils.contains
import utils.get
import utils.set

class Cave(
    rocks: List<List<Coords>>,
    leak: Coords,
    bottom: Boolean,
) {
    companion object {
        private val SHIFT_LOWER = Coords(1, 0)
        private val SHIFT_LOWER_LEFT = Coords(1, -1)
        private val SHIFT_LOWER_RIGHT = Coords(1, 1)
    }

    private val elements: Array<Array<CaveElement>>
    private val leak: Coords

    init {
        var (cols, rows) = rocks.flatten()
            .fold(MinMax(leak.col) to MinMax(leak.row)) { (cols, rows), (row, col) ->
                (cols + col) to (rows + row)
            }

        val paths = rocks.toMutableList()

        if (bottom) {
            // make the cave 2 rows higher
            rows += (rows.max + 2)

            // the bottom does not actually have to be "infinite",
            // it only has to be long enough to form a "triangle" from where the leak is
            val height = rows.max - rows.min + 1
            cols = cols + (leak.col - height) + (leak.col + height)

            // this path will become the rock bottom
            paths += listOf(
                Coords(row = rows.max, col = cols.min),
                Coords(row = rows.max, col = cols.max),
            )
        }

        val width = cols.max - cols.min + 1
        val height = rows.max - rows.min + 1

        elements = Array(height) {
            Array(width) { NONE }
        }

        val offset = Coords(row = rows.min, col = cols.min)
        paths.forEach { path ->
            path.map { it - offset }
                .zipWithNext()
                .flatMap { (start, end) -> Coords.path(start, end) }
                .forEach { elements[it] = ROCK }
        }

        this.leak = leak - offset
        elements[this.leak] = LEAK
    }

    private fun fill(coords: Coords): Coords? {
        if (coords !in elements) throw AbyssReachedException()
        return if (!elements[coords].free) null
        else fill(coords + SHIFT_LOWER) // try directly lower
            ?: fill(coords + SHIFT_LOWER_LEFT) // try lower left
            ?: fill(coords + SHIFT_LOWER_RIGHT) // try lower right
            ?: coords
    }

    fun fill(): Int {
        var units = 0

        while (true) {
            try {
                val coords = fill(leak) ?: break
                elements[coords] = SAND
                units += 1
            }
            catch (e: AbyssReachedException) {
                return units
            }
        }

        return units
    }

    override fun toString(): String = elements.joinToString(separator = System.lineSeparator()) { array ->
        array.joinToString(separator = "") { it.value }
    }
}
