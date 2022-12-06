package day06

import Input
import Presenter
import Solution

fun main() = Presenter.present(Solution06)

object Solution06 : Solution {
    private fun startIndex(signal: String, distinct: Int): Int = signal.windowed(distinct) { it.toSet() }
        .indexOfFirst { it.size == distinct }
        .plus(distinct)

    private fun packetStartIndex(signal: String): Int = startIndex(signal, 4)

    private fun messageStartIndex(signal: String): Int = startIndex(signal, 14)

    override fun part1(input: Input): String = input.lines()
        .map { packetStartIndex(it) }
        .joinToString(separator = ",")

    override fun part2(input: Input): String = input.lines()
        .map { messageStartIndex(it) }
        .joinToString(separator = ",")
}
