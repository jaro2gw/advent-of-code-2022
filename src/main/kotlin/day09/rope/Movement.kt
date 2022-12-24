package day09.rope

import utils.Direction
import utils.Direction.EAST
import utils.Direction.NORTH
import utils.Direction.SOUTH
import utils.Direction.WEST

data class Movement(
    val direction: Direction,
    val steps: Int,
) {
    companion object {
        private val ROPE_MOVE_REGEX = Regex("([DLRU]) (\\d+)")

        private fun direction(char: Char): Direction = when (char) {
            'D' -> SOUTH
            'L' -> WEST
            'R' -> EAST
            'U' -> NORTH
            else -> throw IllegalArgumentException("Could not convert char '$char' into a direction")
        }

        fun fromString(string: String): Movement {
            val (dir, num) = ROPE_MOVE_REGEX.find(string)!!.destructured
            val direction = direction(dir[0])
            val steps = num.toInt()
            return Movement(direction, steps)
        }
    }
}
