package day09.rope

enum class Direction(
    val vector: Vector
) {
    LEFT(vector = Vector(x = -1, y = 0)),
    RIGHT(vector = Vector(x = 1, y = 0)),
    UP(vector = Vector(x = 0, y = 1)),
    DOWN(vector = Vector(x = 0, y = -1));

    companion object {
        fun fromChar(char: Char) = when (char) {
            'L' -> LEFT
            'R' -> RIGHT
            'U' -> UP
            'D' -> DOWN
            else -> throw IllegalArgumentException("Could not convert char '$char' into a direction")
        }
    }
}
