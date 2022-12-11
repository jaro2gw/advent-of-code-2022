package day01

import Input
import Presenter
import Solution
import utils.split

fun main() = Presenter.present(Solution01)

object Solution01 : Solution {
    private fun calories(input: Input) = split(input)
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
