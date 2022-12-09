package day09.rope

data class Movement(
    val direction: Direction,
    val steps: Int,
) {
    companion object {
        private val ROPE_MOVE_REGEX = Regex("([DLRU]) (\\d+)")

        fun fromString(string: String): Movement {
            val (dir, num) = ROPE_MOVE_REGEX.find(string)!!.destructured
            val direction = Direction.fromChar(dir[0])
            val steps = num.toInt()
            return Movement(direction, steps)
        }
    }
}
