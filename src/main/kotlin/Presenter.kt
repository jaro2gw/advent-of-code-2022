import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

@OptIn(ExperimentalTime::class)
object Presenter {
    fun present(solution: Solution) {
        val input = Input(solution::class.java)

        val part1 = measureTimedValue { solution.part1(input) }
        println("Part 1:")
        if (part1.value == null) println("\tTODO")
        else {
            println("\tSolution: ${part1.value}")
            println("\tDuration: ${part1.duration}")
        }

        val part2 = measureTimedValue { solution.part2(input) }
        println("Part 2:")
        if (part2.value == null) println("\tTODO")
        else {
            println("\tSolution: ${part2.value}")
            println("\tDuration: ${part2.duration}")
        }
    }
}
