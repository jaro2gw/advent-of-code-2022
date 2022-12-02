import input.SolutionInput
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

abstract class SolutionTest {
    private val input = SolutionInput(this::class.java)
    abstract val solution: Solution

    protected open val expected1: String = "TODO"
    protected open val expected2: String = "TODO"

    @Test
    fun part1() {
        assertNotEquals("TODO", expected1)

        val actual1 = solution.part1(input)
        assertEquals(expected1, actual1)
    }

    @Test
    fun part2() {
        assertNotEquals("TODO", expected2)

        val actual2 = solution.part2(input)
        assertEquals(expected2, actual2)
    }
}
