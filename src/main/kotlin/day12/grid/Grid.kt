package day12.grid

class Grid(
    val elevation: Array<IntArray>,
    val start: Coords,
    val end: Coords,
) {
    companion object {
        private val alphabet = ('a'..'z').toList().toCharArray()
    }

    init {
        check(elevation[start] == 0)
        check(elevation[end] == 26)
    }

    override fun toString(): String = elevation
        .mapIndexed { row, ints ->
            ints
                .mapIndexed { col, i ->
                    when (Coords(row, col)) {
                        start -> 'S'
                        end -> 'E'
                        else -> alphabet[i]
                    }
                }
                .joinToString(separator = "")
        }
        .joinToString(separator = System.lineSeparator())
}
