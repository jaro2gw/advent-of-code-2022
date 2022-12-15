package day15.sensor

import java.util.TreeSet
import kotlin.math.max
import kotlin.math.min

class Ranges {
    private val ranges = TreeSet<Range>()

    private fun joinable(r1: Range, r2: Range): Boolean =
        if (r1.lower <= r2.lower) r2.lower <= r1.upper + 1
        else r1.lower <= r2.upper + 1

    operator fun plusAssign(range: Range) {
        ranges += ranges.filter { joinable(it, range) }
            .onEach { ranges -= it }
            .fold(range) { r1, r2 ->
                val lower = min(r1.lower, r2.lower)
                val upper = max(r1.upper, r2.upper)

                Range(lower, upper)
            }
    }

    fun continuous(lower: Int, upper: Int): Boolean = ranges.maxBy { it.lower <= lower }.upper >= upper
    fun missingValue(): Int {
        check(ranges.size == 2)
        val (r1, r2) = ranges.toList()
        val missing = (r1.upper + r2.lower) / 2
        check(missing == r1.upper + 1)
        check(missing == r2.lower - 1)
        return missing
    }

    fun size(): Int = ranges.sumOf { it.size() }

    operator fun contains(value: Int): Boolean = ranges.any { value in it }

    override fun toString(): String = ranges.joinToString(separator = ",")
}
