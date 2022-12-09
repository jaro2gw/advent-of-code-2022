package day09.rope

class Rope private constructor(
    private val head: RopeKnot,
    private val tail: RopeKnot,
) {
    companion object {
        fun chain(knots: Int): Rope {
            val tail = RopeKnot()
            var head = tail
            repeat(knots - 1) {
                head = RopeKnot(head.position, head)
            }
            return Rope(head, tail)
        }
    }

    private val tails: MutableSet<Position> = mutableSetOf(tail.position)

    fun tails(): Set<Position> = tails.toSet()

    fun move(movement: Movement) = repeat(movement.steps) {
        head.move(movement.direction.vector)
        tails += tail.position
    }
}
