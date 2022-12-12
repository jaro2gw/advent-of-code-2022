package day12.grid

data class Coords(
    val row: Int,
    val col: Int,
) {
    companion object {
        private val NEIGHBOURS = listOf(
            Coords(row = 0, col = -1), // left
            Coords(row = 0, col = +1), // right
            Coords(row = -1, col = 0), // up
            Coords(row = +1, col = 0), // down
        )
    }

    operator fun plus(coords: Coords) = Coords(
        row = row + coords.row,
        col = col + coords.col,
    )

    fun neighbours() = NEIGHBOURS.map { this + it }
}
