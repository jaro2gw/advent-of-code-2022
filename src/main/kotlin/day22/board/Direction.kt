package day22.board

import day22.board.Turn.LEFT
import day22.board.Turn.RIGHT
import utils.Coords

enum class Direction(dr: Int, dc: Int) {
    EAST(0, +1),
    SOUTH(+1, 0),
    WEST(0, -1),
    NORTH(-1, 0),
    ;

    val vector = Coords(row = dr, col = dc)

    fun turn(turn: Turn): Direction {
        val diff = when (turn) {
            LEFT -> -1
            RIGHT -> +1
        }
        val index = (ordinal + diff + 4) % 4
        return values()[index]
    }
}
