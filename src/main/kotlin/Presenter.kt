import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

@OptIn(ExperimentalTime::class)
object Presenter {
    fun present(solution: Solution) {
        val input = Input(solution::class.java)

        val part1 = measureTimedValue { solution.part1(input) }
        println("Part 1:")
        println("\tSolution: ${part1.value}")
        if (part1.value != "TODO") println("\tDuration: ${part1.duration}")

        val part2 = measureTimedValue { solution.part2(input) }
        println("Part 2:")
        println("\tSolution: ${part2.value}")
        if (part2.value != "TODO") println("\tDuration: ${part2.duration}")
    }
}
