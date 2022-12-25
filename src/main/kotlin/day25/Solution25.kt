package day25

import Input
import Presenter
import Solution
import day25.snafu.SnafuNumber

fun main() = Presenter.present(Solution25)

object Solution25 : Solution {
    override fun part1(input: Input): String {
        val sum = input.lines().sumOf { SnafuNumber.toLong(it) }
        return SnafuNumber.fromLong(sum)
    }

    override fun part2(input: Input): String = ":)"
}
