package utils

enum class Direction(dr: Int, dc: Int) {
    NORTH(-1, 0),
    EAST(0, +1),
    SOUTH(+1, 0),
    WEST(0, -1);

    val vector = Coords(row = dr, col = dc)

    override fun toString(): String = name.take(1)
}
