import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

abstract class SolutionTest {
    private val input = Input(this::class.java, file = "example.txt")
    abstract val solution: Solution

    protected open val expected1: String? = null
    protected open val expected2: String? = null

    @Test
    open fun part1() {
        assertNotNull(expected1)

        val actual1 = solution.part1(input)
        assertEquals(expected1, actual1)
    }

    @Test
    open fun part2() {
        assertNotNull(expected2)

        val actual2 = solution.part2(input)
        assertEquals(expected2, actual2)
    }
}
