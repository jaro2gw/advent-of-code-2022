package day24

import Input
import Presenter
import Solution
import day24.blizzard.Blizzard
import day24.blizzard.Vortex
import utils.Coords
import utils.Direction
import utils.Direction.EAST
import utils.Direction.NORTH
import utils.Direction.SOUTH
import utils.Direction.WEST

fun main() = Presenter.present(Solution24)

object Solution24 : Solution {
    private fun direction(char: Char): Direction = when (char) {
        '^' -> NORTH
        '>' -> EAST
        'v' -> SOUTH
        '<' -> WEST
        else -> throw IllegalArgumentException("Cannot convert char '$char' into a vortex")
    }

    private fun vortex(row: Int, col: Int, char: Char): Vortex {
        val coords = Coords(row, col)
        val direction = direction(char)
        return Vortex(coords, direction)
    }

    private fun convert(input: Input): Pair<Blizzard, List<Vortex>> {
        val rows = input.lines().size
        val cols = input.lines().first().length

        val vortexes = input.lines()
            .flatMapIndexed { row, line ->
                line.withIndex()
                    .filter { (_, char) -> char != '#' }
                    .filter { (_, char) -> char != '.' }
                    .map { (col, char) -> vortex(row, col, char) }
            }

        return Blizzard(rows, cols) to vortexes
    }

    override fun part1(input: Input): String {
        val (blizzard, vortexes) = convert(input)
        val start = Coords(
            row = 0,
            col = 1,
        )
        val end = Coords(
            row = blizzard.rows - 1,
            col = blizzard.cols - 2,
        )
        val (_, minutes) = blizzard.quickestPath(vortexes, start, end)
        return minutes.toString()
    }

    override fun part2(input: Input): String {
        val (blizzard, vortexes1) = convert(input)
        val start = Coords(
            row = 0,
            col = 1,
        )
        val end = Coords(
            row = blizzard.rows - 1,
            col = blizzard.cols - 2,
        )
        val (vortexes2, trip1) = blizzard.quickestPath(vortexes1, start, end)
        val (vortexes3, trip2) = blizzard.quickestPath(vortexes2, end, start)
        val (_, trip3) = blizzard.quickestPath(vortexes3, start, end)

        val total = trip1 + trip2 + trip3
        return total.toString()
    }
}
