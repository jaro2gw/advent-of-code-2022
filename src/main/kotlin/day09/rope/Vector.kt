package day09.rope

data class Vector(
    val x: Int,
    val y: Int,
) {
    operator fun plus(vector: Vector) = Vector(
        x = x + vector.x,
        y = y + vector.y,
    )

    operator fun minus(vector: Vector) = Vector(
        x = x - vector.x,
        y = y - vector.y,
    )
}

typealias Position = Vector
