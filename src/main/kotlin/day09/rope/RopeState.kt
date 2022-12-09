package day09.rope

import kotlin.math.absoluteValue
import kotlin.math.sign

class RopeState private constructor(
    val head: Position,
    val tail: Position,
    val tails: Set<Position>
) {
    companion object {
        fun initial(): RopeState {
            val position = Vector(0, 0)
            return RopeState(
                head = position,
                tail = position,
                tails = setOf(position)
            )
        }
    }

    operator fun plus(movement: Movement): RopeState {
        var newHead = head
        var newTail = tail
        val newTails = LinkedHashSet(tails)

        repeat(movement.steps) {
            newHead += movement.direction.vector
            val difference = newHead - newTail

            var newTailX = newTail.x
            var newTailY = newTail.y
            if (difference.x.absoluteValue > 1 || difference.y.absoluteValue > 1) {
                newTailX += difference.x.sign
                newTailY += difference.y.sign
            }

            newTail = Position(newTailX, newTailY)
            newTails += newTail
        }

        return RopeState(
            head = newHead,
            tail = newTail,
            tails = newTails
        )
    }

    override fun toString(): String {
        fun v(vector: Vector) = "(${vector.x},${vector.y})"
        val tailz = tails.joinToString(
            prefix = "[",
            separator = ",",
            postfix = "]",
            transform = ::v
        )
        return "head=${v(head)}, tail=${v(tail)}, tails=$tailz"
    }
}
