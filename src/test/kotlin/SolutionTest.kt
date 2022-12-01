import input.SolutionInput
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

abstract class SolutionTest {
    private val input = SolutionInput(this::class.java)
    abstract val solution: Solution

    abstract val expected1: String
    abstract val expected2: String

    @Test
    fun part1() {
        val actual1 = solution.part1(input)
        assertEquals(expected1, actual1)
    }

    @Test
    fun part2() {
        val actual2 = solution.part2(input)
        assertEquals(expected2, actual2)
    }
}
