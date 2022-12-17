package day17.rock

import utils.Coords
import utils.contains
import utils.get
import utils.set

class RockChamber(
    private val width: Int,
    shapes: List<RockShape>,
    moves: List<RockMove>
) {
    private val shapes: Iterator<IndexedValue<RockShape>> = infinite(shapes).iterator()
    private val moves: Iterator<IndexedValue<RockMove>> = infinite(moves).iterator()

    private fun <T> infinite(elements: List<T>): Sequence<IndexedValue<T>> = sequence {
        while (true) {
            yieldAll(elements = elements.withIndex())
        }
    }

    private fun height(stack: List<BooleanArray>) = stack.indexOfLast { cells -> cells.any { it } } + 1

    private fun shatter(shape: RockShape, shift: Coords): List<Coords> = shape.shatter().map { it + shift }

    private fun valid(stack: List<BooleanArray>, shape: RockShape, shift: Coords): Boolean = shatter(shape, shift)
        .all { coords ->
            // make sure the coords are in the stack and that the space is not already occupied
            coords in stack && !stack[coords]
        }

    private fun ensureHeight(stack: MutableList<BooleanArray>, height: Int) {
        while (stack.size < height) {
            stack += BooleanArray(width)
        }
    }

    private fun put(stack: List<BooleanArray>, shape: RockShape, shift: Coords) = shatter(shape, shift)
        .forEach { coords -> stack[coords] = true }

    fun height(turns: Int): Int {
        val stack = ArrayList<BooleanArray>()

        repeat(turns) {
            var coords = Coords(
                row = height(stack) + 3,
                col = 2,
            )

            // ensure the stack is high enough (add empty rows if needed)
            ensureHeight(stack, coords.row + 4)

            val (_, shape) = shapes.next()
            while (true) {
                val (_, move) = moves.next()

                // try to move the shape left/right
                val colShift = coords.copy(col = move.adjust(coords.col))
                if (valid(stack, shape, colShift)) coords = colShift // update the coords

                // try to move the shape down
                val rowShift = coords.copy(row = coords.row - 1)
                if (valid(stack, shape, rowShift)) coords = rowShift // update the coords
                else break // the shape cannot move further down
            }

            // put the shape into the stack at the specified coords
            put(stack, shape, coords)
        }

        return height(stack)
    }

    private fun sum(nums: String): Int = nums.sumOf { it - '0' }

    fun height(turns: Long): Long {
        val (prefix, pattern) = recordPattern(10_000)
        val diff = turns - prefix.length
        val cycles = diff / pattern.length
        val pl = diff.rem(pattern.length).toInt()
        val postfix = pattern.take(pl)

        return sum(prefix) + cycles * sum(pattern) + sum(postfix)
    }

    fun recordPattern(turns: Int): Pair<String, String> {
        val stack = ArrayList<BooleanArray>()
        var height = 0
        val diffs = StringBuilder()

        repeat(turns) {
            var coords = Coords(
                row = height(stack) + 3,
                col = 2,
            )

            // ensure the stack is high enough (add empty rows if needed)
            ensureHeight(stack, coords.row + 4)

            val (_, shape) = shapes.next()
            while (true) {
                val (_, move) = moves.next()

                // try to move the shape left/right
                val colShift = coords.copy(col = move.adjust(coords.col))
                if (valid(stack, shape, colShift)) coords = colShift // update the coords

                // try to move the shape down
                val rowShift = coords.copy(row = coords.row - 1)
                if (valid(stack, shape, rowShift)) coords = rowShift // update the coords
                else break
            }

            put(stack, shape, coords)
            val h = height(stack)
            diffs.append(h - height)
            height = h
        }

        val regex = Regex("(\\d{10,3000})\\1+")
        val result = regex.findAll(diffs).maxBy { it.range.count() }
        val range = result.range

        val prefix = diffs.substring(0, range.first)
        val pattern = result.groupValues[1]

        return prefix to pattern
    }
}
