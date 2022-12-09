package day09.rope

import kotlin.math.absoluteValue
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
            val step = Vector(diff.x.sign, diff.y.sign)
            if (diff.x.absoluteValue > 1 || diff.y.absoluteValue > 1) {
                next.move(step)
            }
        }
    }
}
