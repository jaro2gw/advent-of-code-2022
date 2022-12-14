package day14.cave

import day14.cave.CaveElement.LEAK
import day14.cave.CaveElement.NONE
import day14.cave.CaveElement.ROCK
import day14.cave.CaveElement.SAND
import day14.utils.MinMax
import utils.Coords
import utils.contains
import utils.get
import utils.set

class Cave(
    rocks: List<List<Coords>>,
    leak: Coords,
    bottom: Boolean = false,
) {
    private val elements: Array<Array<CaveElement>>
    private val leak: Coords

    init {
        var (cols, rows) = rocks.flatten()
            .fold(MinMax(leak.col) to MinMax(leak.row)) { (cols, rows), (row, col) ->
                (cols.extend(col)) to (rows.extend(row))
            }

        if (bottom) {
            rows = rows.extend(rows.max + 2)
            val half = rows.max - rows.min + 1
            cols = cols.extend(leak.col - half).extend(leak.col + half)
        }

        val offset = Coords(row = rows.min, col = cols.min)

        val width = cols.max - cols.min + 1
        val height = rows.max - rows.min + 1

        elements = Array(height) {
            Array(width) { NONE }
        }

        val paths = rocks.toMutableList()
        if (bottom) paths += listOf(
            Coords(row = rows.max, col = cols.min),
            Coords(row = rows.max, col = cols.max),
        )

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
        else fill(coords + Coords(1, 0))
            ?: fill(coords + Coords(1, -1))
            ?: fill(coords + Coords(1, 1))
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
