import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

@OptIn(ExperimentalTime::class)
object Presenter {
    private val newline = System.lineSeparator()

    private fun present(part: Int, code: () -> String?) {
        val (answer, duration) = measureTimedValue(code)
        if (answer != null) {
            println("Part $part:")
            val prefix = if (answer.contains(newline)) newline else ""
            val string = prefix + answer
            println("\tSolution: $string")
            println("\tDuration: $duration")
        }
    }

    fun present(solution: Solution) {
        val input = Input(solution::class.java, file = "input.txt")
        present(solution, input)
    }

    fun present(solution: Solution, input: Input) {
        present(part = 1) { solution.part1(input) }
        present(part = 2) { solution.part2(input) }
    }
}
