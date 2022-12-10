import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

@OptIn(ExperimentalTime::class)
object Presenter {
    private val separator = System.lineSeparator()

    private fun present(part: Int, code: () -> String?) {
        println("Part $part:")
        val (answer, duration) = measureTimedValue(code)
        if (answer == null) println("\tTODO")
        else {
            val prefix = if (answer.contains(separator)) separator else ""
            val string = prefix + answer
            println("\tSolution: $string")
            println("\tDuration: $duration")
        }
    }

    fun present(solution: Solution) {
        val input = Input(solution::class.java, file = "input.txt")

        present(part = 1) { solution.part1(input) }
        present(part = 2) { solution.part2(input) }
    }
}
