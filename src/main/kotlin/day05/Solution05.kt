package day05

import Input
import Presenter
import Solution
import kotlin.math.max

fun main() = Presenter.present(Solution05)

object Solution05 : Solution {
    private val OPERATION_REGEX = Regex("move (\\d+) from (\\d+) to (\\d+)")
    private val CRATE_REGEX = Regex("\\[(.)]")

    private fun split(
        input: Input,
        supplyCrateMover: (Int) -> CrateMover
    ): Pair<CrateMover, List<CrateMoverOperation>> {
        var size = 0
        val crates = mutableListOf<String>()
        val operations = mutableListOf<CrateMoverOperation>()

        var afterBlank = false
        input.lines().forEach { line ->
            if (line.isBlank()) afterBlank = true
            else if (afterBlank) operations += operation(line)
            else {
                val estimation = (line.length + 1) / 4
                size = max(size, estimation)
                crates += line
            }
        }

        val stacks = supplyCrateMover(size)
        crates.reversed()
            .drop(1) // labels
            .forEach { line ->
                CRATE_REGEX.findAll(line)
                    .forEach { result ->
                        val index = result.range.first / 4
                        val crate = result.groupValues[1][0]
                        stacks.push(index, crate)
                    }
            }

        return stacks to operations
    }

    private fun operation(line: String): CrateMoverOperation {
        val (count, source, target) = OPERATION_REGEX.find(line)!!.groupValues.drop(1).map { it.toInt() }
        return CrateMoverOperation(count, source - 1, target - 1)
    }

    override fun part1(input: Input): String {
        val (mover, operations) = split(input, ::CrateMover9000)
        operations.forEach { mover.perform(it) }
        return mover.peek()
    }

    override fun part2(input: Input): String {
        val (mover, operations) = split(input, ::CrateMover9001)
        operations.forEach { mover.perform(it) }
        return mover.peek()
    }
}
