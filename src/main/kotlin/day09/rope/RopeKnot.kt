package day09.rope

import utils.Coords
import kotlin.math.abs
import kotlin.math.sign

class RopeKnot(
    position: Coords = Coords(row = 0, col = 0),
    private val next: RopeKnot? = null
) {
    var position: Coords = position
        private set

    override fun toString(): String = buildString {
        append("(x=${position.col},y=${position.row})")
        if (next != null) {
            append("->")
            append(next)
        }
    }

    fun move(coords: Coords) {
        position += coords
        if (next != null) {
            val diff = position - next.position
            if (abs(diff.row) > 1 || abs(diff.col) > 1) {
                val step = Coords(
                    row = diff.row.sign,
                    col = diff.col.sign
                )
                next.move(step)
            }
        }
    }
}
