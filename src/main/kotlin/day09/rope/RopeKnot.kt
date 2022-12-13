package day09.rope

import kotlin.math.abs
import kotlin.math.sign

class RopeKnot(
    position: Position = Vector(0, 0),
    private val next: RopeKnot? = null
) {
    var position: Position = position
        private set

    override fun toString(): String = buildString {
        append("(${position.x},${position.y})")
        if (next != null) {
            append("->")
            append(next)
        }
    }

    fun move(vector: Vector) {
        position += vector
        if (next != null) {
            val diff = position - next.position
            if (abs(diff.x) > 1 || abs(diff.y) > 1) {
                val step = Vector(diff.x.sign, diff.y.sign)
                next.move(step)
            }
        }
    }
}
