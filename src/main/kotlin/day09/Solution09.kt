package day09

import Input
import Presenter
import Solution
import day09.rope.Movement
import day09.rope.Rope

fun main() = Presenter.present(Solution09)

object Solution09 : Solution {
    private fun solution(input: Input, knots: Int): String {
        val rope = Rope.chain(knots)
        input.lines()
            .map { Movement.fromString(it) }
            .forEach { rope.move(it) }
        return rope.tails().size.toString()
    }

    override fun part1(input: Input): String = solution(input, knots = 2)

    override fun part2(input: Input): String = solution(input, knots = 10)
}
