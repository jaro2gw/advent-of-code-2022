package day01

import Input
import Solution
import SolutionPresenter
import java.util.LinkedList

fun main() = SolutionPresenter.present(Solution01)

object Solution01 : Solution {
    private fun Sequence<String>.split(): Sequence<List<String>> = sequence {
        var buffer = LinkedList<String>()
        forEach { line ->
            if (line.isBlank()) {
                yield(buffer)
                buffer = LinkedList()
            } else buffer.add(line)
        }
        if (buffer.isNotEmpty()) yield(buffer)
    }

    private fun calories(input: Input) = input.lines()
        .split()
        .map { it.map(String::toInt) }
        .map { it.sum() }

    override fun part1(input: Input): String = calories(input)
        .max()
        .toString()

    override fun part2(input: Input): String = calories(input)
        .sortedDescending()
        .take(3)
        .sum()
        .toString()
}
