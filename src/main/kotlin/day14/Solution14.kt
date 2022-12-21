package day14

import Input
import Presenter
import Solution
import day14.cave.Cave
import utils.Coords

fun main() = Presenter.present(Solution14)

object Solution14 : Solution {
    private fun rocks(input: Input): List<List<Coords>> = input.lines()
        .map { line ->
            line.split(" -> ")
                .map { coords ->
                    val (col, row) = coords.split(",").map { it.toInt() }
                    Coords(row, col)
                }
        }

    override fun part1(input: Input): String = Cave(
        rocks = rocks(input),
        leak = Coords(0, 500),
        bottom = false
    )
        .fill()
        .toString()

    override fun part2(input: Input): String = Cave(
        rocks = rocks(input),
        leak = Coords(0, 500),
        bottom = true
    )
        .fill()
        .toString()
}
